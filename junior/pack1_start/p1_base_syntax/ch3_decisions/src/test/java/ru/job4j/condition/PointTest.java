package ru.job4j.condition;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class PointTest тестирует метод класса Point.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version %G%
 * @since 1
 */
public class PointTest {
    /**
     * Тестирует метод is() с условием, когда точка на функции.
     */
    @Test
    public void whenPointOnFunction() {
        Point point = new Point(2, 6);
        boolean result = point.is(1, 4);
        boolean expected = true;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод is() с условием, когда точка не на функции.
     */
    @Test
    public void whenPointNotOnFunction() {
        Point point = new Point(2, 3);
        boolean result = point.is(1, 4);
        boolean expected = false;
        assertThat(result, is(expected));
    }
}
