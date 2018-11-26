package ru.job4j.array;

/**
 * Class BubbleSort реализует пузырьковую сортировку.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 */
public class BubbleSort {
    /**
     * Сортирует массив методом пузырьковой сортировки.
     * @param array исходный массив.
     * @return отсортированный массив.
     */
    public int[] sort(int[] array) {
        if (array.length > 1) {
            int size = array.length;
            int tmp = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 1; j < (size - i); j++) {
                    if (array[j - 1] > array[j]) {
                        tmp = array[j - 1];
                        array[j - 1] = array[j];
                        array[j] = tmp;
                    }
                }
            }
        }
        return array;
    }
}