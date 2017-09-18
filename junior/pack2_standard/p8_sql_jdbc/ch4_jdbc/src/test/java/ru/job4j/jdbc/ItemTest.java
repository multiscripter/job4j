package ru.job4j.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class ItemTest тестирует методы класса Item.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 4
 * @since 2017-04-18
 */
public class ItemTest {
    /**
     * Драйвер бд.
     */
    private PgSQLJDBCDriver dbDriver;
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
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует пустой конструктор и метод boolean isEmpty().
     */
    @Test
    public void testEmpty() {
        Item item = new Item();
        boolean result = item.isEmpty();
        assertTrue(result);
    }
    /**
     * Тестирует String getId().
     */
    @Test
    public void testGetId() {
        String expected = "expected";
        String result = "result";
        try (Connection con = this.dbDriver.getConnection()) {
            Item item = new Item("ItemTest testGetId имя", "ItemTest testGetId Описание");
            result = item.getId();
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("select max(id) from orders");
            resultSet.next();
            expected = Integer.toString(resultSet.getInt(1));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, result);
    }
}