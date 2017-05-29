package ru.job4j.list;

import java.util.Iterator;
import java.util.ListIterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleLinkedListTest тестирует класс SimpleLinkedList<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-28
 */
public class SimpleLinkedListTest {
    /**
     * Объект связного списка.
     */
    private SimpleLinkedList<String> sll;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.sll = new SimpleLinkedList<>();
        for (int a = 0; a < 9; a++) {
            sll.add("Foo" + a);
        }
    }
    /**
     * Тестирует add(E e).
     */
    @Test
    public void testAdd() {
        boolean result = this.sll.add("Baz");
        assertTrue(result);
    }
    /**
     * Тестирует add(int index, E e).
     */
    @Test
    public void testAddIndex() {
        String[] expected = {"Foo0", "Foo1", "Baz", "Foo2"};
        this.sll.add(2, "Baz");
        String[] result = new String[4];
        Iterator<String> iter = this.sll.iterator();
        int a = 0;
        while (a < 4) {
            result[a++] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует addFirst(E e).
     */
    @Test
    public void testAddFirst() {
        String expected = "Baz";
        this.sll.addFirst("Baz");
        String result = this.sll.get(0);
        assertEquals(expected, result);
    }
    /**
     * Тестирует addLast(E e).
     */
    @Test
    public void testAddLast() {
        String expected = "Baz";
        this.sll.addLast("Baz");
        String result = this.sll.get(9);
        assertEquals(expected, result);
    }
    /**
     * Тестирует element().
     */
    @Test
    public void testElement() {
        String expected = "Baz";
        this.sll.addFirst("Baz");
        String result = this.sll.element();
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 0.
     */
    @Test
    public void testGetIndex0() {
        String expected = "Foo0";
        String result = this.sll.get(0);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 1.
     */
    @Test
    public void testGetIndex1() {
        String expected = "Foo1";
        String result = this.sll.get(1);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 4.
     */
    @Test
    public void testGetIndex4() {
        String expected = "Foo4";
        String result = this.sll.get(4);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 5.
     */
    @Test
    public void testGetIndex5() {
        String expected = "Foo5";
        String result = this.sll.get(5);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 6.
     */
    @Test
    public void testGetIndex6() {
        String expected = "Foo6";
        String result = this.sll.get(6);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 8.
     */
    @Test
    public void testGetIndex8() {
        String expected = "Foo8";
        String result = this.sll.get(8);
        assertEquals(expected, result);
    }
    /**
     * Тестирует getFirst().
     */
    @Test
    public void testGetFirst() {
        String expected = "Foo0";
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует getLast().
     */
    @Test
    public void testGetLast() {
        String expected = "Foo8";
        String result = this.sll.getLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует iterator().
     */
    @Test
    public void testIterator() {
        Iterator<String> iter = this.sll.iterator();
        String[] expected = {"Foo0", "Foo1", "Foo2", "Foo3", "Foo4", "Foo5", "Foo6", "Foo7", "Foo8"};
        String[] result = new String[9];
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует listIterator().
     */
    @Test
    public void testListIterator() {
        ListIterator<String> iter = this.sll.listIterator(4);
        String[] expected = {"Foo4", "Foo5", "Foo6", "Foo7", "Foo8"};
        String[] result = new String[5];
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует offer().
     */
    @Test
    public void testOffer() {
        boolean result = this.sll.offer("Baz");
        assertTrue(result);
    }
    /**
     * Тестирует offerFirst().
     */
    @Test
    public void testOfferFirst() {
        boolean result = this.sll.offerFirst("Baz");
        assertTrue(result);
    }
    /**
     * Тестирует offerLast().
     */
    @Test
    public void testOfferLast() {
        boolean result = this.sll.offerLast("Baz");
        assertTrue(result);
    }
    /**
     * Тестирует peek().
     */
    @Test
    public void testPeek() {
        String expected = "Foo0";
        String result = this.sll.peek();
        assertEquals(expected, result);
    }
    /**
     * Тестирует peekFirst().
     */
    @Test
    public void testPeekFirst() {
        String expected = "Foo0";
        String result = this.sll.peekFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует peekLast().
     */
    @Test
    public void testPeekLast() {
        String expected = "Foo8";
        String result = this.sll.peekLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует poll().
     */
    @Test
    public void testPoll() {
        String expected = "Foo1";
        this.sll.poll();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует pollFirst().
     */
    @Test
    public void testPollFirst() {
        String expected = "Foo1";
        this.sll.pollFirst();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует pollLast().
     */
    @Test
    public void testPollLast() {
        String expected = "Foo7";
        this.sll.pollLast();
        String result = this.sll.getLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует push().
     */
    @Test
    public void testPush() {
        String expected = "Bar";
        this.sll.push("Bar");
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует pop().
     */
    @Test
    public void testPop() {
        String expected = "Foo1";
        this.sll.pop();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует remove().
     */
    @Test
    public void testRemove() {
        String expected = "Foo1";
        this.sll.remove();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует remove(int index).
     */
    @Test
    public void testRemoveIndex() {
        this.sll.remove(6);
        ListIterator<String> iter = this.sll.listIterator(4);
        String[] expected = {"Foo4", "Foo5", "Foo7", "Foo8"};
        String[] result = new String[4];
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует removeFirst().
     */
    @Test
    public void testRemoveFirst() {
        String expected = "Foo1";
        this.sll.removeFirst();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует removeLast().
     */
    @Test
    public void testRemoveLast() {
        String expected = "Foo7";
        this.sll.removeLast();
        String result = this.sll.getLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует set().
     */
    @Test
    public void testSet() {
        String expected = "Baz";
        this.sll.set(5, "Baz");
        String result = this.sll.get(5);
        assertEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        assertEquals(9, this.sll.size());
    }
}
