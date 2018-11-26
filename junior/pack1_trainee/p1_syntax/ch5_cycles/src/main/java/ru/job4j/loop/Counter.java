package ru.job4j.loop;

/**
 * Class Counter вычисляет сумму чётных чисел в интервале [start, finish].
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-08
 */
public class Counter  {
    /**
     * Добавляет интервал и вычисляет сумму чётных чисел.
     * @param start первый конец интервала.
     * @param finish второй конец интервала.
     * @return сумма чётных чисел в интервале.
     */
    public int add(int start, int finish) {
        int sum = 0;
        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                sum += i;
            }
        }
        return sum;
    }
}
