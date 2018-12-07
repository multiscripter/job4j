package ru.job4j.control;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ru.job4j.control.persistence.DBDriver;
/**
 * Класс DBDriverTest тестирует класс DBDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2018-01-09
 */
public class DBDriverTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.driver.executeSql("delete from users where id > 4");
            this.driver.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.driver = DBDriver.getInstance();
            if (!this.driver.isDBDriverSet()) {
                this.driver.setDbDriver();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void close() throws SQLException.
     */
    @Test
    public void testClose() {
        try {
            this.driver.close();
            assertTrue(this.driver.isClosed());
            this.driver.setDbDriver();
            this.driver.setConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     */
    @Test
    public void testDelete() {
        try {
            this.driver.insert("insert into users (login, pass, role_id, addr_id) values ('login_a', 'pass_a', 2, 1)");
            int actual = this.driver.delete("delete from users where login = 'login_a'");
            assertEquals(1, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public int delete(String query) throws SQLException.
     */
    @Test
    public void testDeleteThrowsSQLException() {
        try {
            this.driver.delete("delete from users where login = 'nonexistent_login'");
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void executeSql(String query) throws SQLException.
     */
    @Test
    public void testExecuteSql() {
        try {
            this.driver.executeSql("select count(*) from users");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public void executeSql(String query) throws SQLException.
     */
    @Test
    public void testExecuteSqlThrowsSQLException() {
        try {
            this.driver.executeSql("select count(*) from nonexistent_table");
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public static DBDriver getInstance().
     */
    @Test
    public void testGetInstance() {
        try {
            assertTrue(DBDriver.getInstance() instanceof DBDriver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int getNumActive().
     */
    @Test
    public void testGetNumActive() {
        try {
            assertEquals(0, this.driver.getNumActive());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public HashMap<String, String> insert(String query) throws SQLException.
     */
    @Test
    public void testInsert() {
        try {
            HashMap<String, String> result = this.driver.insert("insert into users (login, pass, role_id, addr_id) values ('login_b', 'pass_b', 2, 2)");
            assertTrue(Integer.parseInt(result.get("id")) > 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public HashMap<String, String> insert(String query) throws SQLException.
     */
    @Test
    public void testInsertThrowsSQLException() {
        try {
            this.driver.insert("insert into users (login, pass, role_id, addr_id) values (no values)");
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public boolean isDBDriverSet().
     */
    @Test
    public void testIsDBDriverSet() {
        try {
            assertTrue(this.driver.isDBDriverSet());
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
            LinkedList<String> expected = new LinkedList<>(Arrays.asList("administrator", "moderator", "user"));
            LinkedList<String> actual = new LinkedList<>();
            LinkedList<HashMap<String, String>> result = this.driver.select("select name from roles order by name");
            for (HashMap<String, String> entry : result) {
                actual.add(entry.get("name"));
            }
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public LinkedList<HashMap<String, String>> select(String query) throws SQLException.
     */
    @Test
    public void testSelectThrowsSQLException() {
        try {
            this.driver.select("select name from roles order by %%%");
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void setConnection() throws SQLException.
     */
    @Test
    public void testSetConnection() {
        try {
            this.driver.close();
            this.driver.setDbDriver();
            assertEquals(0, this.driver.getNumActive());
            this.driver.setConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void setDbDriver().
     */
    @Test
    public void testSetDbDriver() {
        try {
            this.driver.close();
            this.driver.setDbDriver();
            assertTrue(this.driver.isDBDriverSet());
            this.driver.setConnection();
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
            HashMap<String, String> result = this.driver.insert("insert into users (login, pass, role_id, addr_id) values ('login_c', 'pass_c', 2, 3)");
            int id = Integer.parseInt(result.get("id"));
            String query = String.format("update users set login = 'login_c_updated' where id = %d", id);
            assertEquals(1, this.driver.update(query));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public int update(String query) throws SQLException.
     */
    @Test
    public void testUpdateThrowsSQLException() {
        try {
            String query = "update users set login = 'login_c_updated' where id = string";
            this.driver.update(query);
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}