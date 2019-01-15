package ru.job4j.checking;

import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
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
import ru.job4j.utils.PropertyLoader;

/**
 * Класс DBDriverTest тестирует класс DBDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-15
 * @since 2018-03-09
 */
public class DBDriverTest {
    /**
     * Имя СУБД.
     */
    private static String db;
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
            // H2 | HyperSQL | PostgreSQL
            db = new PropertyLoader("activeDBMS.properties").getPropValue("name");
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
            }/* else if (db.equals("PostgreSQL")) {
                url = "jdbc:postgresql://localhost:5432/jpack3p1ch1task0";
                user = "postgres";
                pass = "postgresrootpass";
            }*/
            driver = new DBDriver();
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
            driver.executeSqlScript("junior.pack3.p2.ch1.task2.PostgreSQL.sql");
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
            assertEquals(2, affected);
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
            assertEquals(2, affected);
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
            assertEquals(2, affected);
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
            boolean result = driver.executeSql("select * from users");
            assertTrue(result);
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
     * Тестирует public int[] executeSqlScript(String name) throws IOException, NullPointerException, SQLException, URISyntaxException.
     * Выброс SQLException.
     */
    @Test(expected = NullPointerException.class)
    public void testExecuteSqlScriptThrowsSQLException() {
        try {
            driver.executeSqlScript("junior.pack3.p1.ch1.task0.test.sql");
        } catch (IOException | SQLException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public boolean isValid() throws SQLException.
     */
    @Test
    public void testIsValid() {
        try {
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
            int affected = driver.update("update users set name = 'Zorro' where id = 1");
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