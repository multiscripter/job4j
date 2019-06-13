package ru.job4j.models;

import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс UserTest тестирует класс User.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-30
 * @since 2018-05-10
 */
public class UserTest {
    /**
     * Пользователь.
     */
    private User user;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.user = new User(0L, "TestUser1");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        User expected = new User(0L, "TestUser1");
        assertEquals(expected, this.user);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        User obj = this.user;
        assertEquals(obj, this.user);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        User user = null;
        assertFalse(this.user.equals(user));
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
        User expected = new User(100500L, "TestUser1");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        User expected = new User(0L, "TestUser2");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(0, "TestUser1");
        int actual = this.user.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        User user = new User();
        user.setId(0L);
        user.setName("TestUser1");
        assertEquals("User[id: 0, name: TestUser1]", user.toString());
    }
}
