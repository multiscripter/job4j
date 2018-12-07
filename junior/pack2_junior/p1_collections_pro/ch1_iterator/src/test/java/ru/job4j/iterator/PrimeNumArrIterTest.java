package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс PrimeNumArrIterTest тестирует класс PrimeNumArrIter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-05-22
 */
public class PrimeNumArrIterTest {
    /**
     * Тестирует пользовательское исключение TooShortArrayException.
     */
    @Test(expected = TooShortArrayException.class)
    public void testTooShortArrayException() {
        int[] arr = new int[0];
        PrimeNumArrIter pnai = new PrimeNumArrIter(arr);
    }
    /**
     * Тестирует исключение NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() {
        int[] arr = new int[]{2, 4};
        PrimeNumArrIter pnai = new PrimeNumArrIter(arr);
        pnai.next();
        pnai.next();
    }
    /**
     * Тестирует исключение UnsupportedOperationException.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedOperationException() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        PrimeNumArrIter pnai = new PrimeNumArrIter(arr);
        pnai.remove();
    }
    /**
     * Тестирует hasNext(). Следующего элемента нет.
     */
    @Test
    public void testHasNextFalse() {
        int[] arr = new int[]{6};
        PrimeNumArrIter pnai = new PrimeNumArrIter(arr);
        assertFalse(pnai.hasNext());
    }
    /**
     * Тестирует hasNext(). Следующий элемент есть.
     */
    @Test
    public void testHasNextTrue() {
        int[] arr = new int[]{3};
        PrimeNumArrIter pnai = new PrimeNumArrIter(arr);
        assertTrue(pnai.hasNext());
    }
    /**
     * Тестирует testNext().
     */
    @Test
    public void testNext() {
        int[] arr = new int[]{-2, 0, 1, 2, 4, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17};
        int[] expected = new int[]{1, 2, 7, 11, 13, 17};
        ArrayList<Integer> al = new ArrayList<>();
        PrimeNumArrIter pnai = new PrimeNumArrIter(arr);
        while (pnai.hasNext()) {
            al.add((Integer) pnai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
}
