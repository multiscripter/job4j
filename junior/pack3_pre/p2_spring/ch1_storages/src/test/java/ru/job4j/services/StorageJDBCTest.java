package ru.job4j.services;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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
 * @version 2018-07-19
 * @since 2018-07-19
 */
public class StorageJDBCTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Путь файла sql.
     */
    private String path;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p2ch1task2", "postgres", "postgresrootpass");
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            this.path = path.replaceFirst("^/(.:/)", "$1");
            this.path = String.format("%s../../src/test/resources/junior.pack3.p2.ch1.task2.%s.sql", this.path, "PostgreSQL");
            this.driver.executeSqlScript(this.path);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void add(User user).
     * Явный вызов конструктора.
     */
    @Ignore@Test
    public void testAddWithExplisitCallOfStorageDBMSConstructor() {
        try {
            String cfgLocalFileName = "hibernate.cfg.xml";
            Storage storage = new Storage(cfgLocalFileName);
            User expected = new User(0, "DBMSUser1");
            storage.add(expected);
            String query = String.format("select user.id as \"id\", user.name as \"name\" from users where id = %d", expected.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
            assertEquals(expected, actual);
            storage.close();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Тестирует public void add(User user).
     * Получение бина из контекста.
     */
    @Ignore@Test
    public void testAdd() {
        try {
            ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
            Storage storage = (Storage) ctx.getBean("storageDBMS");
            User expected = new User(0, "DBMSUser2");
            storage.add(expected);
            String query = String.format("select user.id as \"id\", user.name as \"name\" from users where id = %d", expected.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
            assertEquals(expected, actual);
            storage.close();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.driver.executeSqlScript(this.path);
            this.driver.close();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}