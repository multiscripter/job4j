package ru.job4j.utils;

import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Класс PropertyTest тестирует класс Property.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-20
 * @since 2018-01-10
 */
public class PropertyTest {
    /**
     * Свойство.
     */
    private Property prop;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.prop = new Property("propname", "propvalue");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Property expected = new Property("propname", "propvalue");
        Property actual = this.prop;
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        Property actual = this.prop;
        assertEquals(this.prop, actual);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        Property nullRef = null;
        assertFalse(this.prop.equals(nullRef));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.prop.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим именем.
     */
    @Test
    public void testEqualsWithObjectWithDifferentName() {
        assertFalse(this.prop.equals(new Property("anotherName", "propvalue")));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим значением.
     */
    @Test
    public void testEqualsWithObjectWithDifferentValue() {
        assertFalse(this.prop.equals(new Property("propname", "anotherValue")));
    }
    /**
     * Тестирует public String getName().
     */
    @Test
    public void testGetName() {
        assertEquals("propname", this.prop.getName());
    }
    /**
     * Тестирует public String getValue().
     */
    @Test
    public void testGetValue() {
        assertEquals("propvalue", this.prop.getValue());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.prop.getName(), this.prop.getValue());
        assertEquals(expected, this.prop.hashCode());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        assertEquals("property[name: propname, value: propvalue]", this.prop.toString());
    }
}