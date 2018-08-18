package ru.job4j.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.User;
import static org.junit.Assert.assertEquals;
/**
 * Класс StorageMemoryTest тестирует класс Storage с хранением данных в RAM.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-14
 * @since 2018-07-19
 */
public class StorageMemoryTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Путь файла sql.
     */
    private String path;
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
        this.driver = new DBDriver("jdbc:hsqldb:mem:jpack3p2ch1task2;get_column_name=false", "SA", "");
        String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
        this.path = path.replaceFirst("^/(.:/)", "$1");
        this.path = String.format("%s../../src/test/resources/junior.pack3.p2.ch1.task2.%s.sql", this.path, "HyperSQL");
        this.driver.executeSqlScript(this.path);
    }
    /**
     * Тестирует public void add(User user).
     * Явный вызов конструктора.
     * @throws Exception исключение.
     */
    @Test
    public void testAddWithExplisitCallOfStorageMemoryConstructor() throws Exception {
        Storage storage = new Storage();
        User expected = new User(0, "MemoryUser1");
        storage.add(expected);
        String query = String.format("select users.id as id, users.name as name from users where id = %d", expected.getId());
        List<HashMap<String, String>> result = this.driver.select(query);
        User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void close().
     */
    @Test
    public void testClose() {
        Storage storage = new Storage("hibernate.Derby.cfg.xml");
        storage.close();
    }
    /**
     * Тестирует public void add(User user).
     * Получение бина из контекста.
     * @throws Exception исключение.
     */
    @Test
    public void testAdd() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        Storage storage = (Storage) ctx.getBean("storageMemory");
        User expected = new User(0, "MemoryUser2");
        storage.add(expected);
        String query = String.format("select users.id as id, users.name as name from users where id = %d", expected.getId());
        List<HashMap<String, String>> result = this.driver.select(query);
        User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<User> read(User obj) throws Exception.
     * @throws Exception исключение.
     */
    @Test
    public void testRead() throws Exception {
        Storage storage = new Storage();
        List<User> expected = storage.read(new User());
        String query = "select * from users";
        List<HashMap<String, String>> result = this.driver.select(query);
        List<User> actual = new ArrayList<>();
        for (HashMap<String, String> item : result) {
            actual.add(new User(Integer.parseInt(item.get("id")), item.get("name")));
        }
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<User> read(User obj) throws Exception.
     * @throws Exception исключение.
     */
    @Test(expected = Exception.class)
    public void testReadPassNullThrowsException() throws Exception {
        Storage storage = new Storage();
        storage.read(null);
    }
    /**
     * Действия после теста.
     * @throws Exception исключение.
     */
    @After
    public void afterTest() throws Exception {
        this.driver.executeSqlScript(this.path);
        this.driver.close();
    }
}