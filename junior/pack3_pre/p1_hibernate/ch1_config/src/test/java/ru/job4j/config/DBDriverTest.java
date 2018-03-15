package ru.job4j.config;

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
import org.junit.Before;
import org.junit.Test;
/**
 * Класс DBDriverTest тестирует класс DBDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-09
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
            this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch1task1", "postgres", "postgresrootpass");
            this.path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            this.path = path.replaceFirst("^/(.:/)", "$1");
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     */
    @Test
    public void testDelete() {
        try {
            this.driver.executeSqlScript(this.path + "junior.pack3.p1.ch1.task1.sql");
            int affected = this.driver.delete("delete from users");
            assertEquals(3, affected);
        } catch (Exception ex) {
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
            int affected = this.driver.delete("delete from users");
            assertEquals(0, affected);
        } catch (Exception ex) {
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
            this.driver.executeSqlScript(this.path + "junior.pack3.p1.ch1.task1.sql");
            this.driver.close();
            int affected = this.driver.delete("delete from users");
            assertEquals(3, affected);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     * Выброс SQLException.
     */
    @Test
    public void testDeleteThrowsSQLException() {
        SQLException e = null;
        try {
            this.driver.delete("delete from zzzz");
        } catch (SQLException ex) {
            e = ex;
        } finally {
            assertEquals(e.getClass(), SQLException.class);
        }
    }
    /**
     * Тестирует public void executeSql(String query) throws SQLException.
     */
    @Test
    public void testExecuteSql() {
        try {
            this.driver.executeSql("select * from users");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void executeSql(String query) throws SQLException.
     */
    @Test
    public void testExecuteSqlThrowsSQLException() {
        SQLException e = null;
        try {
            this.driver.executeSql("select * from test_table");
        } catch (SQLException ex) {
            e = ex;
        } finally {
            assertEquals(e.getClass(), SQLException.class);
        }
    }
    /**
     * Тестирует public void executeSqlScript(String name) throws IOException, SQLException.
     * Выброс SQLException.
     */
    @Test
    public void testExecuteSqlScriptThrowsSQLException() {
        try {
            SQLException e = null;
            try {
                this.driver.executeSqlScript(this.path + "junior.pack3.p1.ch1.task1.test.sql");
            } catch (SQLException ex) {
                e = ex;
            } finally {
                assertEquals(e.getClass(), SQLException.class);
            }
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
            this.driver.setPass("postgresrootpass");
            this.driver.setUrl("jdbc:postgresql://localhost:5432/jpack3p1ch1task1");
            this.driver.setUser("postgres");
            this.driver.setConnection();
            assertTrue(this.driver.isValid());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<HashMap<String, String>> select(String query) throws SQLException.
     */
    @Test
    public void testSelect() {
        try {
            this.driver.executeSqlScript(this.path + "junior.pack3.p1.ch1.task1.sql");
            LinkedList<HashMap<String, String>> result = this.driver.select("select * from users");
            assertTrue(result.size() > 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<HashMap<String, String>> select(String query) throws SQLException.
     * Выброс SQLException.
     */
    @Test
    public void testSelectThrowsSQLException() {
        SQLException e = null;
        try {
            this.driver.select("select * from test_table");
        } catch (SQLException ex) {
            e = ex;
        } finally {
            assertEquals(e.getClass(), SQLException.class);
        }
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
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int update(String query) throws SQLException.
     */
    @Test
    public void testUpdate() {
        try {
            this.driver.executeSqlScript(this.path + "junior.pack3.p1.ch1.task1.sql");
            int affected = this.driver.update("update users set login = 'Zorro' where id = 1");
            assertEquals(1, affected);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}