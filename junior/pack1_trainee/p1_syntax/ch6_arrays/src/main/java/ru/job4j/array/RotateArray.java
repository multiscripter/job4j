package ru.job4j.array;

/**
 * Class RotateArray реализует поворота квадратного массива по часовой стрелке на 90 градусов.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 */
public class RotateArray {
    /**
     * Поворачивает квадратный массив по часовой стрелке.
     * @param array исходный массив.
     * @return повёрнутый массив.
     */
    public int[][] rotate(int[][] array) {
        int tmp, n = array.length;
        for (int a = 0; a < n / 2; a++) {
            for (int b = a; b < n - 1 - a; b++) {
                tmp = array[a][b];
                array[a][b] = array[n - b - 1][a];
                array[n - b - 1][a] = array[n - a - 1][n - b - 1];
                array[n - a - 1][n - b - 1] = array[b][n - a - 1];
                array[b][n - a - 1] = tmp;
        	}
        }
        return array;
    }
}