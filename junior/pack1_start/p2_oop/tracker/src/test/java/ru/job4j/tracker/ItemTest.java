package ru.job4j.tracker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class ItemTest тестирует методы класса Item.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-18
 */
public class ItemTest {
    /**
     * Тестирует пустой конструктор и метод boolean isEmpty().
     */
    @Test
    public void testEmpty() {
        Item item = new Item();
        boolean result = item.isEmpty();
        assertTrue(result);
    }
    /**
     * Тестирует String getId().
     */
    @Test
    public void testGetId() {
        String id = "Идентификатор";
        Item item = new Item(id, "Имя", "Описание", new String[0]);
        String result = item.getId();
        assertEquals(id, result);
    }
}