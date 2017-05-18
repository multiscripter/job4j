package ru.job4j.iterator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс TwoDemArrIterTest тестирует класс TwoDemArrIter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-18
 */
public class TwoDemArrIterTest {
    /**
     * Тестирует hasNext().
     */
    @Test
    public void testHasNext() {
        int[][] arr = new int[][]{{1, 2}, {3, 4}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        assertTrue(tdai.hasNext());
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext2x2equalsWithString() {
        String expected = new String("1, 2, 3, 4");
        int[][] arr = new int[][]{{1, 2}, {3, 4}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        StringBuilder sb = new StringBuilder();
        while (tdai.hasNext()) {
            sb.append(tdai.next());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        String result = sb.toString();
        assertEquals(expected, result);
    }
    /**
     * Тестирует remove().
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        int[][] arr = new int[][]{{1, 2}, {3, 4}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        tdai.next();
        tdai.remove();
    }
    /**
     * Тестирует forEachRemaining().
     */
    @Test
    public void testForEachRemaining() {
        String expected = new String("1, 2, 3, 4");
        int[][] arr = new int[][]{{1, 2}, {3, 4}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        StringBuilder sb = new StringBuilder();
        tdai.forEachRemaining(item -> {
            sb.append(item);
            sb.append(", ");
        });
        sb.delete(sb.length() - 2, sb.length());
        String result = sb.toString();
        assertEquals(expected, result);
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext2x2() {
        int[][] arr = new int[][]{{1, 2}, {3, 4}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        int expected = 4;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = (Integer) tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext3x3() {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        int expected = 7;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = (Integer) tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext4x3() {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        int expected = 10;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = (Integer) tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext3x4() {
        int[][] arr = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        int expected = 10;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = (Integer) tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext4x4() {
        int[][] arr = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        TwoDemArrIter tdai = new TwoDemArrIter(arr);
        int expected = 16;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = (Integer) tdai.next();
        }
        assertEquals(expected, result);
    }
}