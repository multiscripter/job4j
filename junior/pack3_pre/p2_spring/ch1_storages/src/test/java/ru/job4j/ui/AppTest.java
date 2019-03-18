package ru.job4j.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.User;
import ru.job4j.utils.PropertyLoader;
import static org.junit.Assert.assertTrue;
/**
 * Класс AppTest тестирует приложение, эмулируя ввод в консоль.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-18
 * @since 2018-08-04
 */
public class AppTest {
    /**
     * Локальное имя файла контекста.
     */
    private static String ctxName;
    /**
     * Название текущей СУБД.
     */
    private static String dbmsName;
    /**
     * Драйвер бд.
     */
    private static DBDriver driver;
    /**
     * Путь файла sql.
     */
    private static String path;
    /**
     * Действия перед всеми тестами.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        path = AppTest.class.getClassLoader().getResource(".").getPath();
        path = path.replaceFirst("^/(.:/)", "$1");
        dbmsName = new PropertyLoader(String.format("%s%s", path, "activeDBMS.properties")).getPropValue("name");
        ctxName = "spring-context.xml";
        if (!dbmsName.equals("PostgreSQL")) {
            ctxName = String.format("spring-context.%s.xml", dbmsName);
        }
        driver = new DBDriver(path + dbmsName);
        path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
        path = path.replaceFirst("^/(.:/)", "$1");
        path = String.format("%s../../src/test/resources/junior.pack3.p2.ch1.task2.%s.sql", path, dbmsName);
    }
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @Before
    public void beforeTest() throws Exception {
        /**
         * По-умолчанию HSQLDB в запросах использует имёна столбцов из схемы таблицы, игнорируя алиасы !!!
         * То есть запрос "select col_name as col_alias from . . . " вернёт в результирующем наборе
         * col_name=значение, а не col_alias=значение. Выключается это совершенно дибильное поведение
         * опцией get_column_name=false
         */
        //this.driver = new DBDriver("jdbc:hsqldb:mem:jpack3p2ch1task2;get_column_name=false", "SA", "");
        driver.executeSqlScript(path);
    }
    /**
     * Тестирует добавление пользоватеелй.
     * @throws Exception исключение.
     */
    @Test
    public void checkAddUser() throws Exception {
        String expected = "checkAddUser";
        IIO io = new StubIO(new String[]{"0", expected, "y"});
        new ImportUser(io, ctxName, "storageDBMS").init();
        String query = String.format("select * from users where name = '%s'", expected);
        List<HashMap<String, String>> result = driver.select(query);
        assertTrue(expected.equals(result.get(0).get("name")));
    }
    /**
     * Тестирует приложение.
     * @throws Exception исключение.
     */
    @Test
    public void checkShowUsers() throws Exception {
        PrintStream original = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IIO io = new StubIO(new String[]{"1", "y"});
        new ImportUser(io, ctxName, "storageDBMS").init();
        String expected = out.toString();
        System.setOut(original);
        String query = "select * from users";
        List<HashMap<String, String>> result = driver.select(query);
        for (HashMap<String, String> item : result) {
            User user = new User(Integer.parseInt(item.get("id")), item.get("name"));
            assertTrue(expected.contains(user.toString()));
        }
    }
    /**
     * Действия после теста.
     * @throws Exception исключение.
     */
    @After
    public void afterTest() throws Exception {
        driver.executeSqlScript(path);
        driver.close();
    }
}