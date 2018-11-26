package ru.job4j.iterator;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс EvenIndexArrIterTest тестирует класс EvenIndexArrIter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-22
 */
public class EvenIndexArrIterTest {
    /**
     * Тестирует пользовательское исключение TooShortArrayException.
     */
    @Test(expected = TooShortArrayException.class)
    public void testTooShortArrayException() {
        int[] arr = new int[0];
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
    }
    /**
     * Тестирует hasNext().
     */
    @Test
    public void testHasNextFalse() {
        int[] arr = new int[]{1};
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        assertFalse(enai.hasNext());
    }
    /**
     * Тестирует hasNext().
     */
    @Test
    public void testHasNextTrue() {
        int[] arr = new int[]{1, 2};
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        assertTrue(enai.hasNext());
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext() {
        int[] arr = new int[]{2, 3, 4};
        int expected = 2;
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        int result = (int) enai.next();
        assertEquals(expected, result);
    }
    /**
     * Тестирует пользовательское исключение UnsupportedOperationException.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedOperationException() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        enai.remove();
    }
    /**
     * Проверяет работу итератора на массиве чётной длины. Первый элемент имеет значение 1.
     */
    @Test
    public void checkIteratorArrEvenLengthFirstElemHasValueOne() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        int[] expected = new int[]{2, 4, 6};
        ArrayList<Integer> al = new ArrayList();
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        while (enai.hasNext()) {
            al.add((Integer) enai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Проверяет работу итератора на массиве чётной длины. Первый элемент имеет значение 0.
     */
    @Test
    public void checkIteratorArrEvenLengthFirstElemHasValueZero() {
        int[] arr = new int[]{0, 1, 2, 3, 4, 5, 6};
        int[] expected = new int[]{0, 2, 4, 6};
        ArrayList<Integer> al = new ArrayList();
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        while (enai.hasNext()) {
            al.add((Integer) enai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Проверяет работу итератора на массиве нечётной длины. Первый элемент имеет значение 1.
     */
    @Test
    public void checkIteratorArrOddLengthFirstElemHasValueOne() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        int[] expected = new int[]{2, 4, 6};
        ArrayList<Integer> al = new ArrayList();
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        while (enai.hasNext()) {
            al.add((Integer) enai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Проверяет работу итератора на массиве нечётной длины. Первый элемент имеет значение 0.
     */
    @Test
    public void checkIteratorArrOddLengthFirstElemHasValueZero() {
        int[] arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        int[] expected = new int[]{0, 2, 4, 6};
        ArrayList<Integer> al = new ArrayList();
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        while (enai.hasNext()) {
            al.add((Integer) enai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Проверяет работу итератора на массиве чётной длины. Первый элемент имеет значение -3.
     */
    @Test
    public void checkIteratorArrEvenLengthFirstElemHasValueMinusThree() {
        int[] arr = new int[]{-3, -2, -1, 0, 1, 2, 3, 4};
        int[] expected = new int[]{-2, 0, 2, 4};
        ArrayList<Integer> al = new ArrayList();
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        while (enai.hasNext()) {
            al.add((Integer) enai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Проверяет работу итератора на массиве нечётной длины. Первый элемент имеет значение -4.
     */
    @Test
    public void checkIteratorArrOddLengthFirstElemHasValueMinusFour() {
        int[] arr = new int[]{-4, -3, -2, -1, 0, 1, 2, 3, 4};
        int[] expected = new int[]{-4, -2, 0, 2, 4};
        ArrayList<Integer> al = new ArrayList();
        EvenIndexArrIter enai = new EvenIndexArrIter(arr);
        while (enai.hasNext()) {
            al.add((Integer) enai.next());
        }
        int[] result = al.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
}