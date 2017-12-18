package ru.job4j.testing;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс DBDriverTest тестирует класс DBDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-18
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
            this.driver.executeSql("delete from users where id > 14");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.driver = DBDriver.getInstance();
    }
    /**
     * Тестирует public int delete(String query) throws SQLException.
     */
    @Test
    public void testDelete() {
        try {
            this.driver.insert("insert into users (name, login, email, createDate, pass, role_id) values ('aaa', 'aaa', 'aaa', '1910-01-01', 'aaa', 2)");
            int actual = this.driver.delete("delete from users where login = 'aaa'");
            assertEquals(1, actual);
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public static DBDriver getInstance().
     */
    @Test
    public void testGetInstance() {
        assertTrue(DBDriver.getInstance() instanceof DBDriver);
    }
    /**
     * Тестирует public HashMap<String, String> insert(String query) throws SQLException.
     */
    @Test
    public void testInsert() {
        try {
            HashMap<String, String> result = this.driver.insert("insert into users (name, login, email, createDate, pass, role_id) values ('bbb', 'bbb', 'bbb', '1920-02-02', 'bbb', 2)");
            assertTrue(Integer.parseInt(result.get("id")) > 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<HashMap<String, String>> select(String query) throws SQLException.
     */
    @Test
    public void testSelect() {
        try {
            LinkedList<String> expected = new LinkedList<>(Arrays.asList("administrator", "user"));
            LinkedList<String> actual = new LinkedList<>();
            LinkedList<HashMap<String, String>> result = this.driver.select("select name from roles order by name");
            for (HashMap<String, String> entry : result) {
                actual.add(entry.get("name"));
            }
            assertEquals(expected, actual);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int update(String query) throws SQLException.
     */
    @Test
    public void testUpdate() {
        try {
            HashMap<String, String> result = this.driver.insert("insert into users (name, login, email, createDate, pass, role_id) values ('ccc', 'ccc', 'ccc', '1930-03-03', 'aaa', 2)");
            int id = Integer.parseInt(result.get("id"));
            String query = String.format("update users set name = 'cccUpdated' where id = %d", id);
            assertEquals(1, this.driver.update(query));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}