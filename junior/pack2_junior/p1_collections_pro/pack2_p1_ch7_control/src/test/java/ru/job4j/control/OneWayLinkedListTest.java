package ru.job4j.control;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/**
 * Класс OneWayLinkedListTest тестирует класс OneWayLinkedList.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-24
 */
public class OneWayLinkedListTest {
    /**
     * Тестирует getFirst().
     */
    @Test
    public void testGetFirst() {
        OneWayLinkedList<String> owll = new OneWayLinkedList<>();
        owll.add("Alpha");
        owll.add("Bravo");
        owll.add("Charlie");
        assertEquals("Alpha", owll.getFirst());
    }
    /**
     * Тестирует getFirst(). NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void testGetFirstNoSuchElementException() {
        OneWayLinkedList<String> owll = new OneWayLinkedList<>();
        owll.getFirst();
    }
    /**
     * Тестирует getLast().
     */
    @Test
    public void testGetLast() {
        OneWayLinkedList<String> owll = new OneWayLinkedList<>();
        owll.add("Alpha");
        owll.add("Bravo");
        owll.add("Charlie");
        assertEquals("Charlie", owll.getLast());
    }
    /**
     * Тестирует getLast(). NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void testGetLastNoSuchElementException() {
        OneWayLinkedList<String> owll = new OneWayLinkedList<>();
        owll.getLast();
    }
    /**
     * Тестирует reverse(). Данные типа int.
     */
    @Test
    public void testReverseOnInt() {
        int size = 10;
        int[] expected = new int[size];
        OneWayLinkedList<Integer> owll = new OneWayLinkedList<>();
        for (int a = 0, b = size - 1; a < size; a++, b--) {
            expected[a] = b;
            owll.add(a);
        }
        try {
            owll.reverse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Integer[] reversed = owll.toArray(new Integer[size]);
        int[] result = Arrays.stream(reversed).mapToInt(Integer::intValue).toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует reverse(). Данные типа String.
     */
    @Test
    public void testReverseOnString() {
        String[] expected = {"Hotel", "Golf", "Foxtrot", "Echo", "Delta", "Charlie", "Bravo", "Alpha"};
        OneWayLinkedList<String> owll = new OneWayLinkedList<>();
        List<String> list = Arrays.asList(Arrays.copyOf(expected, expected.length));
        Collections.reverse(list);
        owll.addAll(list);
        try {
            owll.reverse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Object[] returned = owll.toArray();
        String[] result = new String[expected.length];
        for (int a = 0; a < returned.length; a++) {
            result[a] = (String) returned[a];
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        String[] data = {"Hotel", "Golf", "Foxtrot", "Echo", "Delta", "Charlie", "Bravo", "Alpha"};
        OneWayLinkedList<String> owll = new OneWayLinkedList<>();
        for (String item : data) {
            owll.add(item);
        }
        assertEquals(data.length, owll.size());
    }
}