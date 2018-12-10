package ru.job4j.tracker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс TrackerTest тестирует методы класса Tracker.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-10
 * @since 2017-04-18
 */
public class TrackerTest {
    /**
     * Тестирует Item add(Item item).
     */
    @Test
    public void testAdd() {
        Tracker tracker = new Tracker();
        Item expected = new Item("Заявка1", "Имя1", "Описание1");
        Item actual = tracker.add(expected);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует void update(Item uItem).
     */
    @Test
    public void testUpdate() {
        Tracker tracker = new Tracker();
        String id = "Заявка2";
        Item expected = new Item(id, "Имя2", "Описание2");
        tracker.add(expected);
        Item found = tracker.findById(id);
        found.setDesc("Новое описание Заявки2");
        tracker.update(found);
        Item actual = tracker.findById(id);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует Item findById(String id).
     */
    @Test
    public void testFindById() {
        Tracker tracker = new Tracker();
        String id = "Заявка3";
        Item expected = new Item(id, "Имя3", "Описание3");
        tracker.add(expected);
        Item actual = tracker.findById(id);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует Item findByName(String id).
     */
    @Test
    public void testFindByName() {
        Tracker tracker = new Tracker();
        String name = "Имя3";
        for (int a = 1; a < 6; a++) {
            tracker.add(new Item("Заявка" + a, "Имя" + a, "Описание" + a));
        }
        tracker.add(new Item("Заявка10", name, "Описание10"));
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
            tracker.add(new Item("Заявка" + a, "Имя" + a, "Описание" + a));
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
            Item item = new Item("Заявка" + a, "Имя" + a, "Описание" + a);
            tracker.add(item);
            items[a] = item;
        }
        Item[] all = tracker.getAll();
        assertArrayEquals(items, all);
    }
    /**
     * Тестирует int getQuantity().
     */
    @Test
    public void testGetQuantity() {
        Tracker tracker = new Tracker();
        int expected = 2;
        tracker.add(new Item("Заявка1", "Имя1", "Описание1"));
        tracker.add(new Item("Заявка2", "Имя2", "Описание2"));
        assertEquals(expected, tracker.getQuantity());
    }
}
