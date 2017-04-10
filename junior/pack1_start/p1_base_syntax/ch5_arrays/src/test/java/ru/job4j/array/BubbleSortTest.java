package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class BubbleSortTest тестирует методы класса BubbleSort.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 */
public class BubbleSortTest {
    /**
     * Тестирует метод int[] sort(int[] array), сортирующий массив методом пузырьковой сортировки.
     */
    @Test
    public void testSort() {
        int[] array = {-1, 1, 6, 5, -2, 3, 4, 0, 2};
        BubbleSort bubbleSort = new BubbleSort();
        int[] result = bubbleSort.sort(array);
        int[] expected = {-2, -1, 0, 1, 2, 3, 4, 5, 6};
        assertThat(result, is(expected));
    }
}