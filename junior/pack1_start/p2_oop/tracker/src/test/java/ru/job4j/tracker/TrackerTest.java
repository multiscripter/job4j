package ru.job4j.tracker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * Class TrackerTest тестирует методы класса Tracker.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-18
 */
public class TrackerTest {
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
        item.setDesc("Новое описание Заявки2");
        tracker.update(item);
        Item expected = item;
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
     * Тестирует Item findByName(String id).
     */
    @Test
    public void testFindByName() {
        Tracker tracker = new Tracker();
        String name = "Имя3";
        for (int a = 1; a < 6; a++) {
            tracker.add(new Item("Заявка" + a, "Имя" + a, "Описание" + a, new String[0]));
        }
        tracker.add(new Item("Заявка10", name, "Описание10", new String[0]));
        Item[] found = tracker.findByName(name);
        assertEquals(2, found.length);
    }
    /**
     * Тестирует void delete(String id).
     */
    @Test
    public void testDelete() {
        Tracker tracker = new Tracker();
        String id = "Заявка3";
        for (int a = 1; a < 6; a++) {
            tracker.add(new Item("Заявка" + a, "Имя" + a, "Описание" + a, new String[0]));
        }
        tracker.delete(id);
        Item result = tracker.findById(id);
        assertTrue(result.isEmpty());
    }
    /**
     * Тестирует Item[] getAll().
     */
    @Test
    public void testGetAll() {
        Tracker tracker = new Tracker();
        Item[] items = new Item[15];
        for (int a = 0; a < 15; a++) {
            Item item = new Item("Заявка" + a, "Имя" + a, "Описание" + a, new String[0]);
            tracker.add(item);
            items[a] = item;
        }
        Item[] all = tracker.getAll();
        assertArrayEquals(items, all);
    }
    /**
     * Тестирует int getLength().
     */
    @Test
    public void testGetLength() {
        Tracker tracker = new Tracker();
        int expected = 10;
        tracker.add(new Item("Заявка1", "Имя1", "Описание1", new String[0]));
        tracker.add(new Item("Заявка2", "Имя2", "Описание2", new String[0]));
        assertEquals(expected, tracker.getLength());
    }
    /**
     * Тестирует Item[] increaseCapacity(Item[] items).
     */
    @Test
    public void testIncreaseCapacity() {
        Tracker tracker = new Tracker();
        int expected = 20;
        Item[] items = new Item[expected];
        for (int a = 0; a < 15; a++) {
            Item item = new Item("Заявка" + a, "Имя" + a, "Описание" + a, new String[0]);
            tracker.add(item);
        }
        assertEquals(expected, tracker.getLength());
    }
}