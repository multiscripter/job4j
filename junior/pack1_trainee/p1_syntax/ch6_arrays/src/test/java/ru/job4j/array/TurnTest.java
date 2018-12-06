package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс TurnTest тестирует методы класса Turn.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
 * @since 2017-04-10
 */
public class TurnTest {
    /**
     * Тестирует public int[] back(int[] array).
     * Длина массива - нечётное число.
     */
    @Test
    public void testBackWhenArrayHasOddLength() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        Turn turn = new Turn();
        int[] actual = turn.back(arr);
        int[] expected = {7, 6, 5, 4, 3, 2, 1};
        assertArrayEquals(expected, actual);
    }
    /**
     * Тестирует public int[] back(int[] array).
     * Длина массива - чётное число.
     */
    @Test
    public void testBackWhenArrayHasEvenLength() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        Turn turn = new Turn();
        int[] actual = turn.back(arr);
        int[] expected = {6, 5, 4, 3, 2, 1};
        assertArrayEquals(expected, actual);
    }
    /**
     * Тестирует public int[] back(int[] array).
     * Длина массива - чётное число.
     */
    @Test
    public void testBackEmptyOnArray() {
        Turn turn = new Turn();
        assertArrayEquals(new int[]{}, turn.back(new int[]{}));
    }
}