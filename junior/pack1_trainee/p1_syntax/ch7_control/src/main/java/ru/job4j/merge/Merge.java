package ru.job4j.merge;

/**
 * Class Merge реализует слияние отсортированных массивов.
 * Использован алгоритм сортировки слиянием.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-13
 */
public class Merge {
    /**
     * Сливает 2 массива в 1 c сортировкой значения по возрастанию.
     * @param first первый сливаемый массив типа int.
     * @param second второй сливаемый массив типа int.
     * @return array объединённый и отсортированный массив типа int.
     */
    public int[] merge(int[] first, int[] second) {
        int[] merged = new int[first.length + second.length];
        int tmp;
        for (int a = 0, b = 0, c = 0; c < merged.length; c++) {
            if (a == first.length) {
                tmp = second[b++];
            } else if (b == second.length || first[a] < second[b]) {
                tmp = first[a++];
            } else {
                tmp = second[b++];
            }
            merged[c] = tmp;
        }
        return merged;
    }
}
