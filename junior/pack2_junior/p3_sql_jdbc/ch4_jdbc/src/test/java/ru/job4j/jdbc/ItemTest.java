package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс ItemTest тестирует методы класса Item.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-04-18
 */
public class ItemTest {
    /**
     * Драйвер бд.
     */
    private PgSQLJDBCDriver dbDriver;
    /**
     * Объект трэкера заявок.
     */
    private Tracker tracker;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            Prepare pre = new Prepare();
            pre.loadProperties("tracker.properties");
            pre.setDbDriver(new PgSQLJDBCDriver());
            this.dbDriver = pre.getDbDriver();
            pre.executeSql("junior.pack2.p8.ch4.task2.sql");
            this.tracker = new Tracker();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует пустой конструктор и метод boolean isEmpty().
     */
    @Test
    public void testEmpty() {
        Item item = new Item();
        assertTrue(item.isEmpty());
    }
    /**
     * Тестирует String getId().
     */
    @Test
    public void testGetId() {
        try (Connection con = this.dbDriver.getConnection()) {
            Item item = new Item("ItemTest testGetId имя", "ItemTest testGetId Описание");
            this.tracker.add(item);
            String result = item.getId();
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("select max(id) from orders");
            resultSet.next();
            String expected = Integer.toString(resultSet.getInt(1));
            resultSet.close();
            stmt.close();
            assertEquals(expected, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует String getDesc().
     */
    @Test
    public void testGetDesc() {
        try (Connection con = this.dbDriver.getConnection()) {
            Item item = new Item("ItemTest testGetId имя", "ItemTest testGetId Описание");
            this.tracker.add(item);
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format("select descr from orders where id = %d", Integer.parseInt(item.getId())));
            resultSet.next();
            String expected = resultSet.getString(1);
            resultSet.close();
            stmt.close();
            String result = item.getDesc();
            assertEquals(expected, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}