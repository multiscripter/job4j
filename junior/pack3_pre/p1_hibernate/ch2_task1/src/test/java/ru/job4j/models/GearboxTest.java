package ru.job4j.models;

import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс GearboxTest тестирует класс Gearbox.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-26
 * @since 2018-04-26
 */
public class GearboxTest {
    /**
     * Коробка передач.
     */
    private Gearbox gearbox;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.gearbox = new Gearbox(0, "manual");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Gearbox expected = new Gearbox(0, "manual");
        assertEquals(expected, this.gearbox);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Gearbox obj = this.gearbox;
        assertEquals(obj, this.gearbox);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        Gearbox gearbox = null;
        assertFalse(this.gearbox.equals(gearbox));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.gearbox.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        Gearbox expected = new Gearbox(100500, "manual");
        assertFalse(expected.equals(this.gearbox));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        Gearbox expected = new Gearbox(0, "auto");
        assertFalse(expected.equals(this.gearbox));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(0, "manual");
        int actual = this.gearbox.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        Gearbox gearbox = new Gearbox();
        gearbox.setId(0);
        gearbox.setName("manual");
        assertEquals("Gearbox[id: 0, name: manual]", gearbox.toString());
    }
}