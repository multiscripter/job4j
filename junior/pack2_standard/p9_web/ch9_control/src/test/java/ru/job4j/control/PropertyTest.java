package ru.job4j.control;

import java.util.Objects;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import ru.job4j.control.service.Property;
/**
 * Класс PropertyTest тестирует класс Property.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
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
        assertEquals(this.prop, this.prop);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.prop.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.prop.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим значением.
     */
    @Test
    public void testEqualsWithObjectWithDifferentValue() {
        assertFalse(this.prop.equals(new Property("AnotherName",
                "propvalue")));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим именем.
     */
    @Test
    public void testEqualsWithObjectWithDifferentName() {
        assertFalse(this.prop.equals(new Property("propname",
                "AnotherPropValue")));
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
        assertEquals("Property[name: propname, value: propvalue]", this.prop.toString());
    }
}