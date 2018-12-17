package ru.job4j.methref;

import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс UserTest тестирует класс User.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-17
 * @since 2018-12-17
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
        this.user = new User("Foo");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertTrue(this.user.equals(this.user));
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
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.user.equals(""));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        assertEquals(Objects.hash("Foo"), this.user.hashCode());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        assertEquals("User{name: Foo}", this.user.toString());
    }
}
