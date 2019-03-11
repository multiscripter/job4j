package ru.job4j.addresses;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Класс ProfileTest тестирует класс Profile.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-11
 * @since 2019-03-11
 */
public class ProfileTest {
    /**
     * Объект анкеты.
     */
    private Profile profile;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        Address address = new Address(0, 1, "Охотный ряд", "Москва");
        this.profile = new Profile(address);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.profile, this.profile);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.profile.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.profile.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с другой анкетой.
     */
    @Test
    public void testEqualsWithObjectWithDifferentScore() {
        Address address2 = new Address(0, 7, "Моховая", "Москва");
        assertFalse(this.profile.equals(new Profile(address2)));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.profile.getAddress());
        assertEquals(expected, this.profile.hashCode());
    }
    /**
     * Тестирует public Address getAddress().
     */
    @Test
    public void testGetAddress() {
        Address expected = new Address(0, 1, "Охотный ряд", "Москва");
        Address actual = this.profile.getAddress();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void setAddress(Address address).
     */
    @Test
    public void testSetAddress() {
        Address expected = new Address(0, 7, "Моховая", "Москва");
        this.profile.setAddress(new Address(0, 7, "Моховая", "Москва"));
        assertEquals(expected, this.profile.getAddress());
    }
}