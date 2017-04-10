package ru.job4j.array;

/**
 * Class Turn для переворота массива.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 * @see https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html
 */
public class Turn {
    /**
     * Переворачивает массив.
     * @param array исходный массив.
     * @return перевёрнутый массив.
     */
    public int[] back(int[] array) {
        if (array.length > 1) {
            for (int index = 0; index < array.length / 2; index++) {
                int tmp = array[index];
                array[index] = array[array.length - index - 1];
                array[array.length - index - 1] = tmp;
            }
        }
        return array;
    }
}