package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс ColorTest тестирует перечисление Color.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-17
 * @since 2018-12-17
 */
public class ColorTest {
    /**
     * Тестирует public String getValue().
     */
    @Test
    public void testGetValue() {
        Color color = Color.WHITE;
        assertEquals("White", color.getValue());
    }
}
