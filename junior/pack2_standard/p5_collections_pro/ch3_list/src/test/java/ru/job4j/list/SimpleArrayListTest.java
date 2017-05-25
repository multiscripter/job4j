package ru.job4j.list;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleArrayListTest тестирует класс SimpleArrayList<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-25
 */
public class SimpleArrayListTest {
    /**
     * Тестирует add(E e).
     */
    @Test
    public void testAdd() {
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        boolean result = sar.add("Foo");
        assertTrue(result);
    }
    /**
     * Тестирует add(int index, E e).
     */
    @Test
    public void testAddByIndex() {
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        sar.add("Foo");
        sar.add(0, "newFoo");
    }
    /**
     * Тестирует increaseCapacity().
     */
    @Test
    public void testIncreaseCapacity() {
        SimpleArrayList<String> sar = new SimpleArrayList<>(2);
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        assertEquals(4, sar.capacity());
    }
    /**
     * Тестирует get(int index).
     */
    @Test
    public void testGet() {
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        String expected = new String("Bar");
        String result = sar.get(1);
        assertEquals(expected, result);
    }
    /**
     * Тестирует isEmpty().
     */
    @Test
    public void testIsEmpty() {
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        sar.add("Foo");
        sar.remove("Foo");
        assertTrue(sar.isEmpty());
    }
    /**
     * Тестирует iterator().
     */
    @Test
    public void testIterator() {
        String[] expected = {"Foo", "Bar", "Baz"};
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        Iterator<String> sarIter = sar.iterator();
        String[] result = new String[3];
        int index = 0;
        while (sarIter.hasNext()) {
            result[index++] = sarIter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует remove(int index).
     */
    @Test
    public void testRemove() {
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        String expected = new String("Baz");
        sar.add("Foo");
        sar.add("Bar");
        sar.add(expected);
        sar.remove(1);
        String result = sar.get(1);
        assertEquals(expected, result);
    }
    /**
     * Тестирует set(int index).
     */
    @Test
    public void testSet() {
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        String expected = new String("Bar");
        sar.add("Foo");
        sar.add(expected);
        sar.add("Baz");
        String result = sar.set(1, new String("Jossa"));
        assertEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        assertEquals(3, sar.size());
    }
    /**
     * Тестирует toArray().
     */
    @Test
    public void testToArray() {
        SimpleArrayList<String> sar = new SimpleArrayList<>();
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        Object[] expected = {"Foo", "Bar", "Baz"};
        assertArrayEquals(expected, sar.toArray());
    }
}
