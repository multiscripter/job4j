package ru.job4j.streamapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

/**
 * Класс ConvertList реализует функционал конвертации массив массив в список списков и наоборот.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-28
 * @since 2018-11-28
 */
class ConvertList {
    /**
     * Конвертирует массив массив в список списков.
     * @param array конвертируемый массив.
     * @return список список объектов-обёрток целых чисел.
     */
    public List<List<Integer>> intArrArrToListListInteger(int[][] array) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.stream(array).forEach(x -> {
            list.add(Arrays.stream(x).boxed().collect(Collectors.toList()));
        });
        return list;
    }
    /**
     * Конвертирует список списков в массив массив.
     * @param list конвертируемый список.
     * @return список.
     */
    public int[][] listListIntegerToIntArrArr(List<List<Integer>> list) {
        int[][] array = new int[list.size()][];
        for (int a = 0; a < list.size(); a++) {
            array[a] = list.get(a).stream().mapToInt(i -> i).toArray();
        }
        return array;
    }
    /**
     * Конвертирует список массивов в один список.
     * @param list конвертируемый список массивов.
     * @return список.
     */
    public List<Integer> listIntArrToListInteger(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        list.forEach(x -> result.addAll(IntStream.of(x).boxed().collect(Collectors.toList())));
        return result;
    }
}
