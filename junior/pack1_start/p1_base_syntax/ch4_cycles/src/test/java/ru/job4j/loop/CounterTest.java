package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class MaxTest тестирует метод класса Counter.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-08
 */
public class CounterTest {
    /**
     * Тестирует метод add().
     */
    @Test
    public void testAdd() {
        int start = 0, finish = 10, expected = 30;
        Counter counter = new Counter();
        int result = counter.add(start, finish);
        assertThat(result, is(expected));
    }
}
