package ru.job4j.services;

import java.io.File;
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
import ru.job4j.utils.PropertyLoader;
import static org.junit.Assert.assertEquals;
/**
 * Класс StorageJDBCTest тестирует класс Storage с хранением данных в БД.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-18
 * @since 2018-07-19
 */
public class StorageJDBCTest {
    /**
     * Локальное имя файла конфигурации Hibernate.
     */
    private static String cfgLocalFileName;
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
        path = StorageJDBCTest.class.getClassLoader().getResource(".").getPath();
        path = path.replaceFirst("^/(.:/)", "$1");
        dbmsName = new PropertyLoader(String.format("%s%s", path, "activeDBMS.properties")).getPropValue("name");
        cfgLocalFileName = "hibernate.cfg.xml";
        if (!dbmsName.equals("PostgreSQL")) {
            cfgLocalFileName = String.format("hibernate.%s.cfg.xml", dbmsName);
        }
        driver = new DBDriver(path + dbmsName);
        path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
        path = path.replaceFirst("^/(.:/)", "$1");
        path = String.format("%sjunior.pack3.p2.ch1.task2.%s.sql", path, dbmsName);
    }
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @Before
    public void beforeEachTest() throws Exception {
        driver.executeSqlScript(path);
    }
    /**
     * Тестирует public void add(User user).
     * Явный вызов конструктора.
     * @throws Exception исключение.
     */
    @Test
    public void testAddWithExplisitCallOfStorageDBMSConstructor() throws Exception {
        Storage storage = new Storage(cfgLocalFileName);
        User expected = new User(0, "DBMSUser1");
        storage.add(expected);
        String query = String.format("select users.id as \"id\", users.name as \"name\" from users where id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void add(User user).
     * Получение бина из контекста.
     * @throws Exception исключение.
     */
    @Test
    public void testAdd() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        if (!dbmsName.equals("PostgreSQL")) {
            ctx = new ClassPathXmlApplicationContext(String.format("spring-context.%s.xml", dbmsName));
        }
        Storage storage = (Storage) ctx.getBean("storageDBMS");
        User expected = new User(0, "DBMSUser2");
        storage.add(expected);
        String query = String.format("select users.id as \"id\", users.name as \"name\" from users where id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
    }
    /**
     * Действия после теста.
     * @throws Exception исключение.
     */
    @After
    public void afterTest() throws Exception {
        driver.executeSqlScript(path);
    }
    /**
     * Действия после всех тестов.
     * @throws Exception исключение.
     */
    @AfterClass
    public static void beforeAllTest() throws Exception {
        driver.close();
        HibernateSessionFactory.get(cfgLocalFileName); //.close();
    }
}