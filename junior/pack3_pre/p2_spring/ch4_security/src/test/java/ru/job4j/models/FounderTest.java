package ru.job4j.models;

import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс FounderTest тестирует класс Founder.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-30
 * @since 2018-05-14
 */
public class FounderTest {
    /**
     * Кузов.
     */
    private Founder founder;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.founder = new Founder(1L, "Ford", "Henry");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Founder expected = new Founder(1L, "Ford", "Henry");
        assertEquals(expected, this.founder);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Founder obj = this.founder;
        assertEquals(obj, this.founder);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        Founder founder = null;
        assertFalse(this.founder.equals(founder));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.founder.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        Founder expected = new Founder(0L, "Ford", "Henry");
        assertFalse(expected.equals(this.founder));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные nameLast.
     */
    @Test
    public void testEqualsDifferentLastNames() {
        Founder expected = new Founder(0L, "Johnson", "Henry");
        assertFalse(expected.equals(this.founder));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentFirstNames() {
        Founder expected = new Founder(0L, "Ford", "John");
        assertFalse(expected.equals(this.founder));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(1, "Henry", "Ford");
        int actual = this.founder.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        Founder founder = new Founder();
        founder.setId(1L);
        founder.setNameLast("Ford");
        founder.setName("Henry");
        assertEquals("Founder[id: 1, nameLast: Ford, name: Henry]", founder.toString());
    }
}