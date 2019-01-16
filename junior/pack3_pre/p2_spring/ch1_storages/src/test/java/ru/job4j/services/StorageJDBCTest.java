package ru.job4j.services;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.User;
import static org.junit.Assert.assertEquals;
/**
 * Класс StorageJDBCTest тестирует класс Storage с хранением данных в БД.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-14
 * @since 2018-07-19
 */
public class StorageJDBCTest {
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
        URL url = this.getClass().getClassLoader().getResource("hibernate.cfg.xml");
        System.err.println("----- url: " + url);
        //this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p2ch1task2", "postgres", "postgresrootpass");
        this.driver = new DBDriver(url.toString());
        String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
        this.path = path.replaceFirst("^/(.:/)", "$1");
        this.path = String.format("%s../../src/test/resources/junior.pack3.p2.ch1.task2.%s.sql", this.path, "PostgreSQL");
        this.driver.executeSqlScript(this.path);
    }
    /**
     * Тестирует public void add(User user).
     * Явный вызов конструктора.
     * @throws Exception исключение.
     */
    @Test
    public void testAddWithExplisitCallOfStorageDBMSConstructor() throws Exception {
        String cfgLocalFileName = "hibernate.cfg.xml";
        Storage storage = new Storage(cfgLocalFileName);
        User expected = new User(0, "DBMSUser1");
        storage.add(expected);
        String query = String.format("select user.id as \"id\", user.name as \"name\" from users where id = %d", expected.getId());
        List<HashMap<String, String>> result = this.driver.select(query);
        User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
        storage.close();
    }
    /**
     * Тестирует public void add(User user).
     * Получение бина из контекста.
     * @throws Exception исключение.
     */
    @Ignore@Test
    public void testAdd() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        Storage storage = (Storage) ctx.getBean("storageDBMS");
        User expected = new User(0, "DBMSUser2");
        storage.add(expected);
        String query = String.format("select user.id as \"id\", user.name as \"name\" from users where id = %d", expected.getId());
        List<HashMap<String, String>> result = this.driver.select(query);
        User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
        storage.close();
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