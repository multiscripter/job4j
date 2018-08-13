package ru.job4j.models;

import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс UserTest тестирует класс User.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-19
 * @since 2018-07-19
 */
public class UserTest {
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Пользоваетль.
     */
    private User user;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.user = new User(0, "TestName");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        User expected = new User(0, "TestName");
        assertTrue(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        User obj = this.user;
        assertTrue(obj.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.user.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.user.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        User expected = new User(100500, "TestName");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        User expected = new User(0, "ololo");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public int getId().
     */
    @Test
    public void testGetId() {
        assertEquals(0, this.user.getId());
    }
    /**
     * Тестирует public String getName().
     */
    @Test
    public void testGetName() {
        assertEquals("TestName", this.user.getName());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(0, "TestName");
        int actual = this.user.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void setId(int id).
     */
    @Test
    public void testSetId() {
        this.user.setId(100500);
        assertEquals(100500, this.user.getId());
    }
    /**
     * Тестирует public void setName(final String name).
     */
    @Test
    public void testSetName() {
        this.user.setName("Новое имя");
        assertEquals("Новое имя", this.user.getName());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        String expected = String.format("User[id: %d, name: %s]", 0, "TestName");
        String actual = this.user.toString();
        assertEquals(expected, actual);
    }
}