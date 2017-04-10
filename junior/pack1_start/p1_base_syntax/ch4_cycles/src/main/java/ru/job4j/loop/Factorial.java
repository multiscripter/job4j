package ru.job4j.loop;

/**
 * Class Factorial вычисляет факториал простых чисел.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-08
 */
public class Factorial {
    /**
     * Вычисляет факториал числа.
     * @param n число, факториал которого нужно вычислить.
     * @return факториал числа.
     */
    public int calc(int n) {
        int fac = 1;
        if (n > 0) {
            for (; n > 0; n--) {
                fac *= n;
            }
        }
        return fac;
    }
}
