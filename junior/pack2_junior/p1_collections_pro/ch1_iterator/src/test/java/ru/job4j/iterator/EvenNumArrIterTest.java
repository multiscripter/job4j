package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс EvenNumArrIterTest тестирует класс EvenNumArrIter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-05-22
 */
public class EvenNumArrIterTest {
    /**
     * Тестирует пользовательское исключение TooShortArrayException.
     */
    @Test(expected = TooShortArrayException.class)
    public void testTooShortArrayException() {
        int[] arr = new int[0];
        EvenNumArrIter enai = new EvenNumArrIter(arr);
    }
    /**
     * Тестирует исключение NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() {
        int[] arr = new int[]{2, 3};
        EvenNumArrIter enai = new EvenNumArrIter(arr);
        enai.next();
        enai.next();
    }
    /**
     * Тестирует исключение UnsupportedOperationException.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedOperationException() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        EvenNumArrIter enai = new EvenNumArrIter(arr);
        enai.remove();
    }
    /**
     * Тестирует hasNext(). Следующего элемента нет.
     */
    @Test
    public void testHasNextFalse() {
        int[] arr = new int[]{1};
        EvenNumArrIter enai = new EvenNumArrIter(arr);
        assertFalse(enai.hasNext());
    }
    /**
     * Тестирует hasNext(). Следующий элемент есть.
     */
    @Test
    public void testHasNextTrue() {
        int[] arr = new int[]{2};
        EvenNumArrIter enai = new EvenNumArrIter(arr);
        assertTrue(enai.hasNext());
    }
    /**
     * Тестирует testNext().
     */
    @Test
    public void testNext() {
        int[] arr = new int[]{-5, -3, -2, 0, 1, 2, 3, 5, 6, 7, 8, 10};
        int[] expected = new int[]{-2, 0, 2, 6, 8, 10};
        ArrayList<Integer> al = new ArrayList<>();
        EvenNumArrIter enai = new EvenNumArrIter(arr);
        while (enai.hasNext()) {
            al.add((Integer) enai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует testNext(). Пустой результат.
     */
    @Test
    public void testNextEmptyResult() {
        int[] arr = new int[]{-5, -3, 1, 3, 5, 7, 11, 111};
        int[] expected = new int[]{};
        ArrayList<Integer> al = new ArrayList<>();
        EvenNumArrIter enai = new EvenNumArrIter(arr);
        while (enai.hasNext()) {
            al.add((Integer) enai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
}