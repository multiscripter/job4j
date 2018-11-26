package ru.job4j.funciface;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс CalculatorTest тестирует класс Calculator.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-10
 * @since 2018-09-10
 */
public class CalculatorTest {
    /**
     * Тестирует public void calculate(int start, int finish, int value, BiFunction<Integer, Integer, Double> op, Consumer<Double> media).
     * Вычисляет операцию сложения числа 1 и всех чисел из диапазона от 0 до 3.
     * Четвёртый аргумент - функция, представляющая операцию.
     * Пятый аргумент - функция, добавляющая результат вычисления в список.
     */
    @Test
    public void testCalculateFor1from1to3() {
        Calculator calc = new Calculator();
        ArrayList<Double> list = new ArrayList<>();
        calc.calculate(
            0, 3, 1,
            (value, index) -> (double) value + index,
            result -> list.add(result)
        );
        assertEquals(list, Arrays.asList(1D, 2D, 3D));
    }
    /**
     * Тестирует public void calculate(int start, int finish, int value, BiFunction<Integer, Integer, Double> op, Consumer<Double> media).
     * Вычисляет операцию сложения числа 2 и всех чисел из диапазона от 1 до 11.
     * Четвёртый аргумент - функция, представляющая операцию.
     * Пятый аргумент - функция, добавляющая результат вычисления в список.
     */
    @Test
    public void testCalculateFor2from1to10() {
        Calculator calc = new Calculator();
        ArrayList<Double> list = new ArrayList<>();
        calc.calculate(
                1, 11, 2,
                (value, index) -> (double) value * index,
                result -> list.add(result)
        );
        assertEquals(list, Arrays.asList(2D, 4D, 6D, 8D, 10D, 12D, 14D, 16D, 18D, 20D));
    }
}
