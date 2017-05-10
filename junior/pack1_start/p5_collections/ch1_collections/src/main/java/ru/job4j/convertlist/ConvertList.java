package ru.job4j.convertlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс ConvertList реализует функционал конвертации двумерного массива в ArrayList и наоборот.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-10
 */
class ConvertList {
    /**
     * Конвертирует массив в список.
     * @param array конвертируемый массив.
     * @return список.
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> list = new ArrayList<>(array.length);
        for (int a = 0; a < array.length; a++) {
            for (int b = 0; b < array[a].length; b++) {
                list.add(array[a][b]);
            }
        }
        return list;
    }
    /**
     * Конвертирует список в массив.
     * @param list конвертируемый список.
     * @param rows количество элементов во вложенных массивах.
     * @return список.
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int size = (int) Math.ceil((double) list.size() / rows);
        int[][] array = new int[size][rows];
        int a = 0, b = 0;
        for (Integer item : list) {
            array[a][b++] = item;
            if (b == rows) {
                b = 0;
                a++;
            }
        }
        return array;
    }
}
