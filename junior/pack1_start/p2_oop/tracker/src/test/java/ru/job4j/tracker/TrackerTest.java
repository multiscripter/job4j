package ru.job4j.tracker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Class TrackerTest тестирует методы класса Tracker.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-18
 */
public class TrackerTest {
    /**
     * Трэкер заявок.
     */
    private Tracker tracker;
    /**
     * Тестирует Item add(Item item).
     */
    @Test
    public void testAdd() {
        Tracker tracker = new Tracker();
        Item expected = new Item("Заявка1", "Имя1", "Описание1", new String[0]);
        Item result = tracker.add(expected);
        assertEquals(expected, result);
    }
    /**
     * Тестирует void update(Item uItem).
     */
    @Test
    public void testUpdate() {
        Tracker tracker = new Tracker();
        String id = "Заявка2";
        Item item = new Item(id, "Имя2", "Описание2", new String[0]);
        tracker.add(item);
        Item expected = item;
        tracker.update(expected);
        Item result = tracker.findById(id);
        assertEquals(expected, result);
    }
    /**
     * Тестирует Item findById(String id).
     */
    @Test
    public void testFindById() {
        Tracker tracker = new Tracker();
        String id = "Заявка3";
        Item expected = new Item(id, "Имя3", "Описание3", new String[0]);
        tracker.add(expected);
        Item result = tracker.findById(id);
        assertEquals(expected, result);
    }
    /**
     * Тестирует void delete(String id).
     */
    @Test
    public void testDelete() {
        Tracker tracker = new Tracker();
        int expected = tracker.getLength();
        String id = "Заявка4";
        Item item = new Item(id, "Имя4", "Описание4", new String[0]);
        tracker.add(item);
        tracker.delete(id);
        int result = tracker.getLength();
        assertEquals(expected, result);
    }
    /**
     * Тестирует Item[] getAll().
     */
    @Test
    public void testGetAll() {
        Tracker tracker = new Tracker();
        Item[] items = new Item[2];
        items[0] = new Item("Заявка1", "Имя1", "Описание1", new String[0]);
        items[1] = new Item("Заявка2", "Имя2", "Описание2", new String[0]);
        for (Item item : items) {
            tracker.add(item);
        }
        Item[] all = tracker.getAll();
        for (int a = 0; a < all.length; a++) {
            assertEquals(items[a], all[a]);
        }
    }
    /**
     * Тестирует int getLength().
     */
    @Test
    public void testGetLength() {
        Tracker tracker = new Tracker();
        int expected = 2;
        tracker.add(new Item("Заявка1", "Имя1", "Описание1", new String[0]));
        tracker.add(new Item("Заявка2", "Имя2", "Описание2", new String[0]));
        assertEquals(expected, tracker.getLength());
    }
}