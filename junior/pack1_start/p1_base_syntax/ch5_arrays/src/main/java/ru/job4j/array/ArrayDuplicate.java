package ru.job4j.array;

import java.util.Arrays;

/**
 * Class ArrayDuplicate реализует удаление дубликатов в массиве.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 */
public class ArrayDuplicate {
    /**
     * Удаляет дубликаты в массиве.
     * @param array массив с дубликатами.
     * @return массив без дубликатов.
     */
    public String[] remove(String[] array) {
        int size = array.length;
        for (int a = 0; a < size; a++) {
            for (int b = a + 1; b < size; b++) {
                if (array[a].equals(array[b])) {
                    for (int c = b; c < size - 1; c++) {
                        array[c] = array[c + 1];
                    }
                    array = Arrays.copyOf(array, --size);
                    b--;
                }
            }
        }
        return array;
    }
}