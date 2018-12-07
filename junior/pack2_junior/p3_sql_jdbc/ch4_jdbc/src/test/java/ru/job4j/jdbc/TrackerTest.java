package ru.job4j.jdbc;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс TrackerTest тестирует методы класса Tracker.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-04-18
 */
public class TrackerTest {
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            Prepare pre = new Prepare();
            pre.loadProperties("tracker.properties");
            pre.setDbDriver(new PgSQLJDBCDriver());
            pre.executeSql("junior.pack2.p8.ch4.task2.sql");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует Item add(Item item).
     */
    @Test
    public void testAdd() {
        try {
            Tracker tracker = new Tracker();
            Item expected = new Item("Имя1", "Описание1");
            Item result = tracker.add(expected);
            assertEquals(expected, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует void update(Item uItem).
     */
    @Test
    public void testUpdate() {
        try {
            Tracker tracker = new Tracker();
            Item expected = new Item("Имя2", "Описание2");
            tracker.add(expected);
            Item found = new Item();
            found = tracker.findById(expected.getId());
            found.setDesc("Новое описание Заявки2");
            assertTrue(tracker.update(found));
            Item result = new Item();
            result = tracker.findById(expected.getId());
            assertEquals(expected, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует Item findById(String id).
     */
    @Test
    public void testFindById() {
        try {
            Tracker tracker = new Tracker();
            String id = "Заявка3";
            Item expected = new Item("Имя3", "Описание3");
            tracker.add(expected);
            Item result = tracker.findById(expected.getId());
            assertEquals(expected, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует Item findByName(String id).
     */
    @Test
    public void testFindByName() {
        try {
            Tracker tracker = new Tracker();
            String name = "Имя3";
            for (int a = 1; a < 6; a++) {
                tracker.add(new Item("Имя" + a, "Описание" + a));
            }
            tracker.add(new Item(name, "Описание10"));
            Item[] found = tracker.findByName(name);
            assertEquals(2, found.length);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует void delete(String id).
     */
    @Test
    public void testDelete() {
        try {
            Tracker tracker = new Tracker();
            String name = "Заявка";
            for (int a = 1; a < 6; a++) {
                tracker.add(new Item(name + a, "Описание" + a));
            }
            Item[] found = tracker.findByName(name + 3);
            assertTrue(tracker.delete(found[0].getId()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует Item[] getAll().
     */
    @Test
    public void testGetAll() {
        try {
            Tracker tracker = new Tracker();
            Item[] items = new Item[15];
            for (int a = 0; a < 15; a++) {
                Item item = new Item("Имя" + a, "Описание" + a);
                tracker.add(item);
                items[a] = item;
            }
            Item[] all = tracker.getAll();
            assertArrayEquals(items, all);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует int getQuantity().
     */
    @Test
    public void testGetQuantity() {
        try {
            Tracker tracker = new Tracker();
            tracker.add(new Item("Имя1", "Описание1"));
            tracker.add(new Item("Имя2", "Описание2"));
            assertEquals(2, tracker.getQuantity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
