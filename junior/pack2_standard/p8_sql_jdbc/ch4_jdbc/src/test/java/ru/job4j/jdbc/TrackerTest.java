package ru.job4j.jdbc;

import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * Class TrackerTest тестирует методы класса Tracker.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 7
 * @since 2017-04-18
 */
public class TrackerTest {
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
        } catch (SQLException ex) {
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
            String id = expected.getId();
            tracker.add(expected);
            Item found = new Item();
            found = tracker.findById(id);
            found.setDesc("Новое описание Заявки2");
            tracker.update(found);
            Item result = new Item();
            result = tracker.findById(id);
            assertEquals(expected, result);
        } catch (SQLException ex) {
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
            Item expected = new Item("Имя3", "Описание3");
            String id = expected.getId();
            tracker.add(expected);
            Item result = tracker.findById(id);
            assertEquals(expected, result);
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
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
            Item[] tasks = new Item[6];
            for (int a = 0; a < tasks.length; a++) {
                tasks[a] = new Item("Имя" + a, "Описание" + a);
                tracker.add(tasks[a]);
            }
            String id = tasks[5].getId();
            tracker.delete(id);
            Item result = tracker.findById(id);
            assertTrue(result.isEmpty());
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
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
            int expected = 2;
            tracker.add(new Item("Имя1", "Описание1"));
            tracker.add(new Item("Имя2", "Описание2"));
            assertEquals(expected, tracker.getQuantity());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
