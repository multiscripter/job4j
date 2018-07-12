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
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
/**
 * Класс DBDriverTest тестирует класс DBDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-11
 * @since 2018-03-09
 */
public class DBDriverTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Путь до sql-файла.
     */
    private String path;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.logger = LogManager.getLogger(this.getClass().getName());
        try {
            this.driver = new DBDriver("jdbc:h2:mem:DBDriverTest;DB_CLOSE_DELAY=-1", "sa", "");
            this.path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            this.path = path.replaceFirst("^/(.:/)", "$1");
            this.driver.executeSqlScript(this.path + "../../src/test/resources/junior.pack3.p1.ch1.task0.H2.sql");
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     */
    @Test
    public void testDelete() {
        try {
            int affected = this.driver.delete("delete from \"users\"");
            assertEquals(3, affected);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
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
            this.driver.setConnection();
            int affected = this.driver.delete("delete from \"users\"");
            assertEquals(3, affected);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
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
            this.driver.close();
            int affected = this.driver.delete("delete from \"users\"");
            assertEquals(3, affected);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
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
        this.driver.delete("delete from zzzz");
    }
    /**
     * Тестирует public void executeSql(String query) throws SQLException.
     */
    @Test
    public void testExecuteSql() {
        try {
            this.driver.executeSql("select * from \"users\"");
        } catch (SQLException ex) {
            this.logger.error("ERROR", ex);
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
        this.driver.executeSql("select * from test_table");
    }
    /**
     * Тестирует public void executeSqlScript(String name) throws IOException, SQLException.
     * Выброс SQLException.
     * @throws java.sql.SQLException исключение SQL.
     */
    @Test(expected = SQLException.class)
    public void testExecuteSqlScriptThrowsSQLException() throws SQLException {
        try {
            this.driver.executeSqlScript(this.path + "../../src/test/resources/junior.pack3.p1.ch1.task0.test.sql");
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
            this.driver.setPass("");
            this.driver.setUrl("jdbc:h2:mem:jpack3p1ch1task0");
            this.driver.setUser("sa");
            this.driver.setConnection();
            assertTrue(this.driver.isValid());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<HashMap<String, String>> select(String query) throws SQLException.
     */
    @Test
    public void testSelect() {
        try {
            LinkedList<HashMap<String, String>> result = this.driver.select("select * from \"users\"");
            assertTrue(result.size() > 0);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
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
        this.driver.select("select * from test_table");
    }
    /**
     * Тестирует public void setConnection() throws SQLException.
     */
    @Test
    public void testSetConnection() {
        try {
            this.driver.close();
            this.driver.setConnection();
            assertTrue(this.driver.isValid());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int update(String query) throws SQLException.
     */
    @Test
    public void testUpdate() {
        try {
            int affected = this.driver.update("update \"users\" set login = 'Zorro' where id = 1");
            assertEquals(1, affected);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.driver.close();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}