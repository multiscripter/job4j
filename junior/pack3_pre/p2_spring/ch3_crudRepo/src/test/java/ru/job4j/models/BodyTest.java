package ru.job4j.models;

import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс BodyTest тестирует класс Body.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-30
 * @since 2018-04-26
 */
public class BodyTest {
    /**
     * Кузов.
     */
    private Body body;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.body = new Body(0L, "sedan");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Body expected = new Body(0L, "sedan");
        assertEquals(expected, this.body);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Body obj = this.body;
        assertEquals(obj, this.body);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        Body body = null;
        assertFalse(this.body.equals(body));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.body.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        Body expected = new Body(100500L, "sedan");
        assertFalse(expected.equals(this.body));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        Body expected = new Body(0L, "hatchback");
        assertFalse(expected.equals(this.body));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(0, "sedan");
        int actual = this.body.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        Body body = new Body();
        body.setId(0L);
        body.setName("sedan");
        assertEquals("Body[id: 0, name: sedan]", body.toString());
    }
}