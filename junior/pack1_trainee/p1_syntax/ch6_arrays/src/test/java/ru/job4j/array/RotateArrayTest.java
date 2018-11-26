package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class RotateArrayTest тестирует методы класса RotateArray.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 */
public class RotateArrayTest {
    /**
     * Тестирует метод int[][] rotate(int[][] array), поворачивающий матрицу 2x2 на 90 градусов по часовой стрелке.
     */
    @Test
    public void testRotateMatrix2x2CW90digrees() {
        int[][] matrix = {{1, 2}, {3, 4}};
        RotateArray rot = new RotateArray();
        int[][] result = rot.rotate(matrix);
        int[][] expected = {{3, 1}, {4, 2}};
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод int[][] rotate(int[][] array), поворачивающий матрицу 3x3 на 90 градусов по часовой стрелке.
     */
    @Test
    public void testRotateMatrix3x3CW90digrees() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RotateArray rot = new RotateArray();
        int[][] result = rot.rotate(matrix);
        int[][] expected = {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};
        assertThat(result, is(expected));
    }
}