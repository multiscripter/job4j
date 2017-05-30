package ru.job4j.list;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleLimkedPriorityQueueTest тестирует класс SimpleLimkedPriorityQueue<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-30
 */
public class SimpleLimkedPriorityQueueTest {
    /**
     * Объект связного списка.
     */
    private SimpleLimkedPriorityQueue<String> slpq;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.slpq = new SimpleLimkedPriorityQueue<>();
        for (int a = 0; a < 9; a++) {
            this.slpq.add("Foo" + a * 2);
        }
    }
    /**
     * Тестирует add(E e). Вставка в начало очереди.
     */
    @Test
    public void testAddBeforeHead() {
        String expected = "Foo";
        this.slpq.add("Foo");
        Iterator<String> iter = this.slpq.iterator();
        String result = iter.next();
        assertEquals(expected, result);
    }
    /**
     * Тестирует add(E e). Вставка в середину очереди.
     */
    @Test
    public void testAddInsertMiddle() {
        String[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo9", "Foo10", "Foo12", "Foo14", "Foo16"};
        this.slpq.add("Foo9");
        String[] result = new String[10];
        Iterator<String> iter = this.slpq.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует add(E e). Вставка в конец очереди.
     */
    @Test
    public void testAddToTail() {
        String expected = "Foo22";
        this.slpq.add("Foo22");
        String result = null;
        Iterator<String> iter = this.slpq.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result = iter.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует clear().
     */
    @Test
    public void testClear() {
        this.slpq.clear();
        assertEquals(0, this.slpq.size());
    }
    /**
     * Тестирует contains(). Указанный в параметре объект есть в очереди.
     */
    @Test
    public void testContainsTrue() {
        assertTrue(this.slpq.contains("Foo8"));
    }
    /**
     * Тестирует contains(). Очередь не содержит указанный в параметре объект.
     */
    @Test
    public void testContainsFalse() {
        assertFalse(this.slpq.contains("Labut"));
    }
    /**
     * Тестирует element().
     */
    @Test
    public void testElement() {
        String expected = "Foo0";
        String result = this.slpq.element();
        assertEquals(expected, result);
    }
    /**
     * Тестирует isEmpty().
     */
    @Test
    public void testIsEmpty() {
        this.slpq.clear();
        assertTrue(this.slpq.isEmpty());
    }
    /**
     * Тестирует offer(E e).
     */
    @Test
    public void testOffer() {
        String expected = "AAA";
        this.slpq.add("AAA");
        Iterator<String> iter = this.slpq.iterator();
        String result = iter.next();
        assertEquals(expected, result);
    }
    /**
     * Тестирует peek().
     */
    @Test
    public void testPeek() {
        String expected = "Foo0";
        Iterator<String> iter = this.slpq.iterator();
        String result = iter.next();
        assertEquals(expected, result);
    }
    /**
     * Тестирует poll().
     */
    @Test
    public void testPoll() {
        String expected = "Foo2";
        this.slpq.poll();
        Iterator<String> iter = this.slpq.iterator();
        String result = iter.next();
        assertEquals(expected, result);
    }
    /**
     * Тестирует remove().
     */
    @Test
    public void testRemove() {
        String expected = "Foo4";
        this.slpq.remove();
        this.slpq.remove();
        Iterator<String> iter = this.slpq.iterator();
        String result = iter.next();
        assertEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        assertEquals(9, this.slpq.size());
    }
    /**
     * Тестирует toArray().
     */
    @Test
    public void testToObjectArray() {
        Object[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        Object[] result = this.slpq.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует toArray(E[] a).
     */
    @Test
    public void testToEArray() {
        String[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        String[] result = this.slpq.toArray(new String[9]);
        assertArrayEquals(expected, result);
    }
}