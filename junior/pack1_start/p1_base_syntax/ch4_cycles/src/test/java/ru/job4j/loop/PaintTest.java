package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class PaintTest тестирует методы класса Paint.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-08
 */
public class PaintTest {
    /**
     * Тестирует метод piramid с аргументом 2.
     */
    @Test
    public void testPiramidWithParamTwo() {
        Paint paint = new Paint();
        String result = paint.piramid(2);
        String sep = System.getProperty("line.separator");
        String expected = String.format(" ^ %s^^^", sep);
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод piramid с аргументом 3.
     */
    @Test
    public void testPiramidWithParamThree() {
       Paint paint = new Paint();
        String result = paint.piramid(3);
        String sep = System.getProperty("line.separator");
        String expected = String.format("  ^  %s ^^^ %s^^^^^", sep, sep);
        assertThat(result, is(expected));
    }
}
