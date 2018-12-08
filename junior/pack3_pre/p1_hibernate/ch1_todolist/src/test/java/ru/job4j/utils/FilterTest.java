package ru.job4j.utils;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/**
 * Класс FilterTest тестирует класс Filter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-08
 * @since 2018-04-22
 */
public class FilterTest {
    /**
     * Фильтр валидации.
     */
    private Filter filter;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.filter = new Filter("name", new String[]{"isExists", "isFilled"});
    }
    /**
     * Тестирует public String getName().
     */
    @Test
    public void testGetName() {
        assertEquals("name", this.filter.getName());
    }
    /**
     * Тестирует public String[] getFilters().
     */
    @Test
    public void testGetFilters() {
        assertArrayEquals(new String[]{"isExists", "isFilled"}, this.filter.getFilters());
    }
    /**
     * Тестирует public String getValue().
     */
    @Test
    public void testGetValue() {
        assertEquals("isExists, isFilled", this.filter.getValue());
    }
}