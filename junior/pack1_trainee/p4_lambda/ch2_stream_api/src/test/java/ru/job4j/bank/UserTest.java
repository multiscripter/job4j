package ru.job4j.bank;

import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Класс UserTest тестирует класс User.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-11
 * @since 2018-12-11
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
        this.user = new User("Doe", "0OIKZHGH");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Равные объекты.
     */
    @Test
    public void testEqualsObjectAreEquals() {
        User user = new User("Doe", "0OIKZHGH");
        assertTrue(this.user.equals(user));
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
     * Тестирует public boolean equals(Object obj).
     * Сравнение с пользователем с другим именем.
     */
    @Test
    public void testEqualsUsersHaveDifferentNames() {
        User anotherUser = new User("ololo", "0OIKZHGH");
        assertFalse(this.user.equals(anotherUser));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с пользователем с другим паспортом.
     */
    @Test
    public void testEqualsUsersHaveDifferentPassports() {
        User anotherUser = new User("Doe", "ololo");
        assertFalse(this.user.equals(anotherUser));
    }
    /**
     * Тестирует public String getName().
     */
    @Test
    public void testGetName() {
        assertEquals("Doe", this.user.getName());
    }
    /**
     * Тестирует public String getPassport().
     */
    @Test
    public void testGetPassport() {
        assertEquals("0OIKZHGH", this.user.getPassport());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        assertEquals(Objects.hash("Doe", "0OIKZHGH"), this.user.hashCode());
    }
    /**
     * Тестирует public void setName(String name).
     */
    @Test
    public void testSetName() {
        this.user.setName("Foo");
        assertEquals("Foo", this.user.getName());
    }
    /**
     * Тестирует public void setPassport(String passport).
     */
    @Test
    public void testSetPassport() {
        this.user.setPassport("bar");
        assertEquals("bar", this.user.getPassport());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        String expected = "User{name: Doe, passport: 0OIKZHGH}";
        assertEquals(expected, this.user.toString());
    }
}
