package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class FactorialTest тестирует методы класса Factorial.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-08
 */
public class FactorialTest {
    /**
     * Тестирует метод add() с аргументом 0.
     */
    @Test
    public void testCalcWithParamZero() {
        int param = 0, expected = 1;
        Factorial factorial = new Factorial();
        int result = factorial.calc(param);
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод add() с аргументом 5.
     */
    @Test
    public void testCalcWithParamFive() {
        int param = 5, expected = 120;
        Factorial factorial = new Factorial();
        int result = factorial.calc(param);
        assertThat(result, is(expected));
    }
}
