package ru.job4j.models;

import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс BrandTest тестирует класс Brand.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-30
 * @since 2018-05-14
 */
public class BrandTest {
    /**
     * Кузов.
     */
    private Brand brand;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.brand = new Brand(1L, "Ford", new Founder(1L, "Ford", "Henry"));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Brand expected = new Brand(1L, "Ford", new Founder(1L, "Ford", "Henry"));
        assertEquals(expected, this.brand);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Brand obj = this.brand;
        assertEquals(obj, this.brand);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        Brand brand = null;
        assertFalse(this.brand.equals(brand));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.brand.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        Brand expected = new Brand(0L, "Ford", new Founder(1L, "Ford", "Henry"));
        assertFalse(expected.equals(this.brand));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        Brand expected = new Brand(1L, "Chevrolet", new Founder(1L, "Ford", "Henry"));
        assertFalse(expected.equals(this.brand));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentFounders() {
        Brand expected = new Brand(1L, "Ford", new Founder(1L, "Chevrolet", "Louis"));
        assertFalse(expected.equals(this.brand));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(new Founder(1L, "Ford", "Henry"), 1L, "Ford");
        int actual = this.brand.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Ford");
        brand.setFounder(new Founder(1L, "Ford", "Henry"));
        assertEquals("Brand[id: 1, name: Ford, founder: Founder[id: 1, nameLast: Ford, name: Henry]]", brand.toString());
    }
}