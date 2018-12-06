package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Class BubbleSortTest тестирует методы класса BubbleSort.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
 * @since 2017-04-10
 */
public class BubbleSortTest {
    /**
     * Тестирует public int[] sort(int[] array).
     * Сортирующий массив методом пузырьковой сортировки.
     */
    @Test
    public void testSort() {
        int[] array = {-1, 1, 6, 5, -2, 3, 4, 0, 2};
        BubbleSort bubbleSort = new BubbleSort();
        int[] actual = bubbleSort.sort(array);
        int[] expected = {-2, -1, 0, 1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, actual);
    }
    /**
     * Тестирует public int[] sort(int[] array).
     * Пустой массив.
     */
    @Test
    public void testSortEmptyArray() {
        BubbleSort bubbleSort = new BubbleSort();
        assertArrayEquals(bubbleSort.sort(new int[]{}), new int[]{});
    }
}