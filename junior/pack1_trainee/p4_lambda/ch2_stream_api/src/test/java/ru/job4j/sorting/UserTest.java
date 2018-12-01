package ru.job4j.sorting;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Класс UserTest тестирует класс User.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-01
 * @since 2018-12-01
 */
public class UserTest {
    /**
     * Человек.
     */
    private User user;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.user = new User("Emelyan", 73);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.user, this.user);
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
        assertFalse(this.user.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим именем.
     */
    @Test
    public void testEqualsWithObjectWithDifferentName() {
        User user2 = new User("Иван", 1);
        assertFalse(this.user.equals(user2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим возрастом.
     */
    @Test
    public void testEqualsWithObjectWithDifferentAge() {
        User user2 = new User("Emelyan", 1);
        assertFalse(this.user.equals(user2));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.user.getName(), this.user.getAge());
        assertEquals(expected, this.user.hashCode());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        String expected = "User{name: Emelyan, age: 73}";
        assertEquals(expected, this.user.toString());
    }
}
