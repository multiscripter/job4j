package ru.job4j.list;

import org.junit.Before;
import java.util.ListIterator;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleLinkedStackTest тестирует класс SimpleLinkedStack<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-31
 */
public class SimpleLinkedStackTest {
    /**
     * Объект связного списка.
     */
    private SimpleLinkedStack<String> sls;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.sls = new SimpleLinkedStack<>();
        for (int a = 0; a < 9; a++) {
            this.sls.add("Foo" + a * 2);
        }
    }
    /**
     * Тестирует add(E e).
     */
    @Test
    public void testAdd() {
        String[] expected = {"Bar", "Foo16", "Foo14"};
        String[] result = new String[3];
        this.sls.add("Bar");
        ListIterator<String> iter = this.sls.listIterator();
        result[0] = iter.previous();
        result[1] = iter.previous();
        result[2] = iter.previous();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует add(int index, E e).
     */
    @Test
    public void testAddByIndex() {
        String[] expected = {"Bar0", "Foo16", "Foo14", "Foo12", "Foo10", "Bar5", "Foo8", "Foo6", "Foo4", "Foo2", "Bar11", "Foo0"};
        this.sls.add(0, "Bar0");
        this.sls.add(5, "Bar5");
        this.sls.add(10, "Bar11");
        String[] result = this.sls.toArray(new String[12]);
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует clear().
     */
    @Test
    public void testClear() {
        this.sls.clear();
        assertEquals(0, this.sls.size());
    }
    /**
     * Тестирует contains(). Указанный в параметре объект есть в стэке.
     */
    @Test
    public void testContainsTrue() {
        assertTrue(this.sls.contains("Foo8"));
    }
    /**
     * Тестирует contains(). Стэк не содержит указанный в параметре объект.
     */
    @Test
    public void testContainsFalse() {
        assertFalse(this.sls.contains("Labut"));
    }
    /**
     * Тестирует empty().
     */
    @Test
    public void testEmpty() {
        this.sls.clear();
        assertTrue(this.sls.empty());
    }
    /**
     * Тестирует get().
     */
    @Test
    public void testGet() {
        assertEquals("Foo8", this.sls.get(4));
    }
    /**
     * Тестирует listIterator() от основания стэка к вершине.
     */
    @Test
    public void testListIteratorFromBottomToTop() {
        String[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        String[] result = new String[9];
        ListIterator<String> iter = this.sls.listIterator(this.sls.size() - 1);
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует listIterator() с вершины к основанию стэка.
     */
    @Test
    public void testListIteratorFromTopToBottom() {
        String[] expected = {"Foo16", "Foo14", "Foo12", "Foo10", "Foo8", "Foo6", "Foo4", "Foo2", "Foo0"};
        String[] result = new String[9];
        ListIterator<String> iter = this.sls.listIterator();
        for (int a = 0; iter.hasPrevious(); a++) {
            result[a] = iter.previous();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует peek().
     */
    @Test
    public void testPeek() {
        this.sls.add("Bar");
        assertEquals("Bar", this.sls.peek());
    }
    /**
     * Тестирует pop().
     */
    @Test
    public void testPop() {
        this.sls.add("Bar");
        this.sls.add("Baz");
        this.sls.pop();
        assertEquals("Bar", this.sls.peek());
    }
    /**
     * Тестирует push().
     */
    @Test
    public void testPush() {
        String result = this.sls.push("Bar");
        String expected = this.sls.peek();
        assertEquals(expected, result);
    }
    /**
     * Тестирует remove().
     */
    @Test
    public void testRemove() {
        String[] expected = {"Foo16", "Foo14", "Foo12", "Foo10", "Foo8", "Foo4", "Foo2", "Foo0"};
        this.sls.remove(5);
        String[] result = this.sls.toArray(new String[8]);
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует search(). Указанный объект есть в стэке.
     */
    @Test
    public void testSearchObjectExists() {
        int expected = 5;
        int result = this.sls.search("Foo6");
        assertEquals(expected, result);
    }
    /**
     * Тестирует search(). Указанного объекта нет в стэке.
     */
    @Test
    public void testSearchObjectNotExists() {
        int expected = -1;
        int result = this.sls.search("Azaza");
        assertEquals(expected, result);
    }
    /**
     * Тестирует set(int index, E e).
     */
    @Test
    public void testSet() {
        String[] expected = {"Foo16", "Foo14", "Foo12", "Foo10", "Foo8", "Azaza", "Foo4", "Foo2", "Foo0"};
        this.sls.set(5, "Azaza");
        String[] result = this.sls.toArray(new String[9]);
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        assertEquals(9, this.sls.size());
    }
    /**
     * Тестирует toArray().
     */
    @Test
    public void testToArray() {
        Object[] expected = {"Foo16", "Foo14", "Foo12", "Foo10", "Foo8", "Foo6", "Foo4", "Foo2", "Foo0"};
        Object[] result = this.sls.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует toArray(E[] a).
     */
    @Test
    public void testToEArray() {
        String[] expected = {"Foo16", "Foo14", "Foo12", "Foo10", "Foo8", "Foo6", "Foo4", "Foo2", "Foo0"};
        String[] result = this.sls.toArray(new String[9]);
        assertArrayEquals(expected, result);
    }
}
