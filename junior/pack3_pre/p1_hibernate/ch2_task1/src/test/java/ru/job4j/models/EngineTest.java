package ru.job4j.models;

import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс EngineTest тестирует класс Engine.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-26
 * @since 2018-04-26
 */
public class EngineTest {
    /**
     * Двигатель.
     */
    private Engine engine;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.engine = new Engine(0, "VAZ-11183-50");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Engine expected = new Engine(0, "VAZ-11183-50");
        assertEquals(expected, this.engine);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Engine obj = this.engine;
        assertEquals(obj, this.engine);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        Engine engine = null;
        assertFalse(this.engine.equals(engine));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.engine.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        Engine expected = new Engine(100500, "VAZ-11183-50");
        assertFalse(expected.equals(this.engine));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        Engine expected = new Engine(0, "VAZ-11186");
        assertFalse(expected.equals(this.engine));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(0, "VAZ-11183-50");
        int actual = this.engine.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        Engine engine = new Engine();
        engine.setId(0);
        engine.setName("VAZ-11183-50");
        assertEquals("Engine[id: 0, name: VAZ-11183-50]", engine.toString());
    }
}