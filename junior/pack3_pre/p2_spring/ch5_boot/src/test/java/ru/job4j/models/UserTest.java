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
 * @version 2019-08-01
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
        this.user = new User(true, 0L, "TestUser1", "passhash");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        User expected = new User(true, 0L, "TestUser1", "passhash");
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
     * Разные activity.
     */
    @Test
    public void testEqualsDifferentActivities() {
        User expected = new User(false, 0L, "TestUser1", "passhash");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        User expected = new User(true, 100500L, "TestUser1", "passhash");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        User expected = new User(true, 0L, "TestUser2", "passhash");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные хаши паролей.
     */
    @Test
    public void testEqualsDifferentPassHashes() {
        User expected = new User(true, 0L, "TestUser2", "anotherPassHash");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(true, 0, "TestUser1", "passhash");
        int actual = this.user.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        User user = new User();
        user.setActivity(true);
        user.setId(0L);
        user.setName("TestUser1");
        assertEquals("User[id: 0, name: TestUser1, activity: true]", user.toString());
    }
}
