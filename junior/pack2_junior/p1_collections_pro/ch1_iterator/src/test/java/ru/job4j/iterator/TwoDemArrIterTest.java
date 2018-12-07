package ru.job4j.iterator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс TwoDemArrIterTest тестирует класс TwoDemArrIter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-05-18
 */
public class TwoDemArrIterTest {
    /**
     * Тестирует hasNext().
     */
    @Test
    public void testHasNext() {
        Integer[][] arr = new Integer[][]{{1, 2}, {3, 4}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
        assertTrue(tdai.hasNext());
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext2x2equalsWithString() {
        String expected = "1, 2, 3, 4";
        Integer[][] arr = new Integer[][]{{1, 2}, {3, 4}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
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
        Integer[][] arr = new Integer[][]{{1, 2}, {3, 4}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
        tdai.next();
        tdai.remove();
    }
    /**
     * Тестирует forEachRemaining().
     */
    @Test
    public void testForEachRemaining() {
        String expected = "1, 2, 3, 4";
        Integer[][] arr = new Integer[][]{{1, 2}, {3, 4}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
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
     * Тестирует next(). Массив 2х2.
     */
    @Test
    public void testNext2x2() {
        Integer[][] arr = new Integer[][]{{1, 2}, {3, 4}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
        int expected = 4;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next(). Массив 3х3.
     */
    @Test
    public void testNext3x3() {
        Integer[][] arr = new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
        int expected = 7;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next(). Массив 4х3.
     */
    @Test
    public void testNext4x3() {
        Integer[][] arr = new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
        int expected = 10;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next(). Массив 3х4.
     */
    @Test
    public void testNext3x4() {
        Integer[][] arr = new Integer[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
        int expected = 10;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next(). Массив 4х4.
     */
    @Test
    public void testNext4x4() {
        Integer[][] arr = new Integer[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
        int expected = 16;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = tdai.next();
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует next(). Вложенные массивы разной длины.
     */
    @Test
    public void testNextArrayDifferentLength() {
        Integer[][] arr = new Integer[][]{{1, 2, 3, 4}, {5, 6, 7}, {8, 9, 10, 11, 12}, {13}, {14, 15, 16}};
        TwoDemArrIter<Integer> tdai = new TwoDemArrIter<>(arr);
        int expected = 16;
        int result = -1;
        for (int a = 0; a < expected; a++) {
            result = tdai.next();
        }
        assertEquals(expected, result);
    }
}
