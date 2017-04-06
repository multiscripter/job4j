package ru.job4j.max;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class MaxTest тестирует метод класса Max.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version %G%
 * @since 1
 */
public class MaxTest {
    /**
     * Тестирует метод max(). Значение из первого параметра больше, чем значение из второго.
     */
    @Test
    public void whenFirstGreaterThanSecond() {
        Max max = new Max();
        int result = max.max(2, 1);
        int expected = 2;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод max(). Значение из первого параметра меньше, чем значение из второго.
     */
    @Test
    public void whenFirstLessThanSecond() {
        Max max = new Max();
        int result = max.max(1, 2);
        int expected = 2;
        assertThat(result, is(expected));
    }
}
