package ru.job4j.config;

import java.io.IOException;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
/**
 * Класс DBDriverTest тестирует класс DBDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-22
 * @since 2018-03-09
 */
public class DBDriverTest {
    /**
     * Имя СУБД.
     */
    private static String db = "H2"; // H2 | HyperSQL | PostgreSQL
    /**
     * Драйвер бд.
     */
    private static DBDriver driver;
    /**
     * Логгер.
     */
    private static Logger logger = LogManager.getLogger("TrackerRepositoryUserTest");
    /**
     * Путь к файлу.
     */
    private static String path;
    /**
     * URL подключения к БД.
     */
    private static String url;
    /**
     * Пользователь БД.
     */
    private static String user;
    /**
     * Пароль пользователя БД.
     */
    private static String pass;
    /**
     * Действия перед тестом.
     */
    @BeforeClass
    public static void beforeAllTests() {
        try {
            if (db.equals("H2")) {
                // http://www.h2database.com/html/features.html#in_memory_databases
                // В H2 алиасы по умолчанию могут быть выкючены.
                // http://www.h2database.com/html/faq.html#column_names_incorrect
                url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
                user = "sa";
                pass = "";
            } else if (db.equals("HyperSQL")) {
                url = "jdbc:hsqldb:mem:jpack3p1ch1task0;get_column_name=false;ifexists=true";
                user = "SA";
                pass = "";
            } else if (db.equals("PostgreSQL")) {
                url = "jdbc:postgresql://localhost:5432/jpack3p1ch1task0";
                user = "postgres";
                pass = "postgresrootpass";
            }
            driver = new DBDriver(url, user, pass);
            path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeEachTest() {
        try {
            driver.executeSqlScript(String.format("%s../../src/test/resources/junior.pack3.p1.ch1.task0.%s.sql", path, db));
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     */
    @Test
    public void testDelete() {
        try {
            int affected = driver.delete("delete from users");
            assertEquals(3, affected);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     * Явная установка соединения.
     */
    @Test
    public void testDeleteConnectionEstablished() {
        try {
            driver.setConnection();
            int affected = driver.delete("delete from users");
            assertEquals(3, affected);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     * Соединение закрыто.
     */
    @Test
    public void testDeleteConnectionClosed() {
        try {
            driver.close();
            int affected = driver.delete("delete from users");
            assertEquals(3, affected);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     * Выброс SQLException.
     * @throws java.sql.SQLException исключение SQL.
     */
    @Test(expected = SQLException.class)
    public void testDeleteThrowsSQLException() throws SQLException {
        driver.delete("delete from zzzz");
    }
    /**
     * Тестирует public void executeSql(String query) throws SQLException.
     */
    @Test
    public void testExecuteSql() {
        try {
            driver.executeSql("select * from users");
        } catch (SQLException ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void executeSql(String query) throws SQLException.
     * Выброс SQLException.
     * @throws java.sql.SQLException исключение SQL.
     */
    @Test(expected = SQLException.class)
    public void testExecuteSqlThrowsSQLException() throws SQLException {
        driver.executeSql("select * from test_table");
    }
    /**
     * Тестирует public void executeSqlScript(String name) throws IOException, SQLException.
     * Выброс SQLException.
     * @throws java.sql.SQLException исключение SQL.
     */
    @Test(expected = SQLException.class)
    public void testExecuteSqlScriptThrowsSQLException() throws SQLException {
        try {
            driver.executeSqlScript(path + "../../src/test/resources/junior.pack3.p1.ch1.task0.test.sql");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public boolean isValid() throws SQLException.
     */
    @Test
    public void testIsValid() {
        try {
            driver.setPass(pass);
            driver.setUrl(url);
            driver.setUser(user);
            driver.setConnection();
            assertTrue(driver.isValid());
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<HashMap<String, String>> select(String query) throws SQLException.
     */
    @Test
    public void testSelect() {
        try {
            LinkedList<HashMap<String, String>> result = driver.select("select * from users");
            assertTrue(result.size() > 0);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<HashMap<String, String>> select(String query) throws SQLException.
     * Выброс SQLException.
     * @throws java.sql.SQLException исключение SQL.
     */
    @Test(expected = SQLException.class)
    public void testSelectThrowsSQLException() throws SQLException {
        driver.select("select * from test_table");
    }
    /**
     * Тестирует public void setConnection() throws SQLException.
     */
    @Test
    public void testSetConnection() {
        try {
            driver.close();
            driver.setConnection();
            assertTrue(driver.isValid());
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int update(String query) throws SQLException.
     */
    @Test
    public void testUpdate() {
        try {
            int affected = driver.update("update users set login = 'Zorro' where id = 1");
            assertEquals(1, affected);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Действия после всех тестов.
     */
    @AfterClass
    public static void afterAllTest() {
        try {
            driver.close();
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}