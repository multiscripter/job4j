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
 * @version 2018-05-14
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
        this.brand = new Brand(1, "Ford", new Founder(1, "Ford", "Henry"));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Brand expected = new Brand(1, "Ford", new Founder(1, "Ford", "Henry"));
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
        Brand expected = new Brand(0, "Ford", new Founder(1, "Ford", "Henry"));
        assertFalse(expected.equals(this.brand));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        Brand expected = new Brand(1, "Chevrolet", new Founder(1, "Ford", "Henry"));
        assertFalse(expected.equals(this.brand));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentFounders() {
        Brand expected = new Brand(1, "Ford", new Founder(1, "Chevrolet", "Louis"));
        assertFalse(expected.equals(this.brand));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(new Founder(1, "Ford", "Henry"), 1, "Ford");
        int actual = this.brand.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Ford");
        brand.setFounder(new Founder(1, "Ford", "Henry"));
        assertEquals("Brand[id: 1, name: Ford, founder: Founder[id: 1, nameLast: Ford, name: Henry]]", brand.toString());
    }
}