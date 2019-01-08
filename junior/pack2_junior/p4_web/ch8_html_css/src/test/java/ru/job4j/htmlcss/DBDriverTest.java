package ru.job4j.htmlcss;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс DBDriverTest тестирует класс DBDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
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
            this.driver.executeSql("delete from users where id > 4");
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
            this.driver.insert("insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('name_a', 'login_a', 'login@a', '1910-01-01', 'pass_a', 2, 1)");
            int actual = this.driver.delete("delete from users where login = 'login_a'");
            assertEquals(1, actual);
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
     * Тестирует public static DBDriver getInstance().
     */
    @Test
    public void testGetInstance() {
        try {
            assertTrue(DBDriver.getInstance() != null);
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
            HashMap<String, String> result = this.driver.insert("insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('name_b', 'login_b', 'email_b', '1920-02-02', 'pass_b', 2, 2)");
            assertTrue(Integer.parseInt(result.get("id")) > 0);
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
            LinkedList<String> expected = new LinkedList<>(Arrays.asList("administrator", "user"));
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
     * Тестирует public int update(String query) throws SQLException.
     */
    @Test
    public void testUpdate() {
        try {
            HashMap<String, String> result = this.driver.insert("insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('name_c', 'login_c', 'email_c', '1930-03-03', 'pass_c', 2, 8)");
            int id = Integer.parseInt(result.get("id"));
            String query = String.format("update users set name = 'cccUpdated' where id = %d", id);
            assertEquals(1, this.driver.update(query));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}