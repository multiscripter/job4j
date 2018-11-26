package ru.job4j.calculator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class CalculatorTest тестирует методы класса Calculator.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 0.1
 */
public class CalculatorTest {
    /**
     * Тестирует метод add().
     */
    @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод substruct().
     */
    @Test
    public void whenAddThreeMinusTwoThenOne() {
        Calculator calc = new Calculator();
        calc.substruct(3D, 2D);
        double result = calc.getResult();
        double expected = 1D;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод multiple().
     */
    @Test
    public void whenAddThreeMultipleThreeThenNine() {
        Calculator calc = new Calculator();
        calc.multiple(3D, 3D);
        double result = calc.getResult();
        double expected = 9D;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод div().
     */
    @Test
    public void whenAddSixDivideThreeThenTwo() {
        Calculator calc = new Calculator();
        calc.div(6D, 3D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
}