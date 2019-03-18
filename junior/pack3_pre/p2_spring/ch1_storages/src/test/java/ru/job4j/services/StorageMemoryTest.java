package ru.job4j.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.User;
import ru.job4j.utils.HibernateSessionFactory;
import static org.junit.Assert.assertEquals;
/**
 * Класс StorageMemoryTest тестирует класс Storage с хранением данных в RAM.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-18
 * @since 2018-07-19
 */
public class StorageMemoryTest {
    /**
     * Название субд.
     */
    private static String dbmsName;
    /**
     * Драйвер бд.
     */
    private static DBDriver driver;
    /**
     * Локальное ямя sql-скрипта.
     */
    private static String sqlScriptName;
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        dbmsName = "HyperSQL";
        /**
         * Получает абсолютный путь до папки с ресурсами.
         * В случае с Maven это: target/test-classes/
         */
        String path = StorageMemoryTest.class.getClassLoader().getResource(".").getPath();
        path = path.replaceFirst("^/(.:/)", "$1");
        driver = new DBDriver(path + dbmsName);
        sqlScriptName = String.format("%sjunior.pack3.p2.ch1.task2.%s.sql", path, dbmsName);
    }
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @Before
    public void beforeEachTest() throws Exception {
        /**
         * По-умолчанию HSQLDB в запросах использует имёна столбцов из схемы
         * таблицы, игнорируя алиасы !!!
         * То есть запрос "select col_name as col_alias from . . . "
         * вернёт в результирующем наборе col_name=значение,
         * а не col_alias=значение. Выключается это совершенно дибильное
         * поведение опцией get_column_name=false
         */
        //driver = new DBDriver("jdbc:hsqldb:mem:jpack3p2ch1task2;get_column_name=false", "SA", "");
        driver.executeSqlScript(sqlScriptName);
    }
    /**
     * Тестирует public void add(User user).
     * Явный вызов конструктора.
     * @throws Exception исключение.
     */
    @Test
    public void testAddWithExplisitCallOfStorageMemoryConstructor() throws Exception {
        Storage storage = new Storage(String.format("hibernate.%s.cfg.xml", dbmsName));
        User expected = new User(0, "MemoryUser1");
        storage.add(expected);
        String query = String.format("select users.id as id, users.name as name from users where id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void close().
     * @throws Exception исключение.
     */
    @Test
    public void testClose() throws Exception {
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
        ApplicationContext ctx = new ClassPathXmlApplicationContext(String.format("spring-context.%s.xml", dbmsName));
        Storage storage = (Storage) ctx.getBean("storageMemory");
        User expected = new User(0, "MemoryUser2");
        storage.add(expected);
        String query = String.format("select users.id as id, users.name as name from users where id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<User> read(User obj) throws Exception.
     * Явный вызов конструктора.
     * @throws Exception исключение.
     */
    @Test
    public void testRead() throws Exception {
        Storage storage = new Storage(String.format("hibernate.%s.cfg.xml", dbmsName));
        List<User> expected = storage.read(new User());
        String query = "select * from users";
        List<HashMap<String, String>> result = driver.select(query);
        List<User> actual = new ArrayList<>();
        for (HashMap<String, String> item : result) {
            actual.add(new User(Integer.parseInt(item.get("id")), item.get("name")));
        }
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<User> read(User obj) throws Exception.
     * Явный вызов конструктора.
     * @throws Exception исключение.
     */
    @Test(expected = Exception.class)
    public void testReadPassNullThrowsException() throws Exception {
        Storage storage = new Storage(String.format("hibernate.%s.cfg.xml", dbmsName));
        storage.read(null);
    }
    /**
     * Действия после теста.
     * @throws Exception исключение.
     */
    @After
    public void afterEachTest() throws Exception {
        driver.executeSqlScript(sqlScriptName);
    }
    /**
     * Действия после всех тестов.
     * @throws Exception исключение.
     */
    @AfterClass
    public static void afterAllTests() throws Exception {
        driver.close();
        String cfgLocalFileName = "hibernate.cfg.xml";
        if (!dbmsName.equals("PostgreSQL")) {
            cfgLocalFileName = String.format("hibernate.%s.cfg.xml", dbmsName);
        }
        HibernateSessionFactory.get(cfgLocalFileName); //.close();
    }
}