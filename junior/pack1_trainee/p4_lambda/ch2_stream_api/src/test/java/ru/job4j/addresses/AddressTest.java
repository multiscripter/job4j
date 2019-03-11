package ru.job4j.addresses;

import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Класс AddressTest тестирует класс Address.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-11
 * @since 2019-03-11
 */
public class AddressTest {
    /**
     * Объект адреса.
     */
    private Address address;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.address = new Address(0, 1, "Охотный ряд", "Москва");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.address, this.address);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.address.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.address.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разный номер квартиры.
     */
    @Test
    public void testEqualsWithObjectWithDifferentApartment() {
        Address address2 = new Address(100500, 1, "Охотный ряд", "Москва");
        assertFalse(this.address.equals(address2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разный номер дома.
     */
    @Test
    public void testEqualsWithObjectWithDifferentHome() {
        Address address2 = new Address(0, 100500, "Охотный ряд", "Москва");
        assertFalse(this.address.equals(address2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разная улица.
     */
    @Test
    public void testEqualsWithObjectWithDifferentStreet() {
        Address address2 = new Address(0, 1, "Маховая", "Москва");
        assertFalse(this.address.equals(address2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разный город.
     */
    @Test
    public void testEqualsWithObjectWithDifferentCity() {
        Address address2 = new Address(0, 1, "Охотный ряд", "Питер");
        assertFalse(this.address.equals(address2));
    }
    /**
     * Тестирует public int getApartment().
     */
    @Test
    public void testGetApartment() {
        assertEquals(0, this.address.getApartment());
    }
    /**
     * Тестирует public String getCity().
     */
    @Test
    public void testGetCity() {
        assertEquals("Москва", this.address.getCity());
    }
    /**
     * Тестирует public int getHome().
     */
    @Test
    public void testGetHome() {
        assertEquals(1, this.address.getHome());
    }
    /**
     * Тестирует public String getStreet().
     */
    @Test
    public void testGetStreet() {
        assertEquals("Охотный ряд", this.address.getStreet());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.address.getApartment(), this.address.getCity(), this.address.getHome(), this.address.getStreet());
        assertEquals(expected, this.address.hashCode());
    }
    /**
     * Тестирует public void setApartment(int apartment).
     */
    @Test
    public void testSetApartment() {
        final int expected = 100500;
        this.address.setApartment(expected);
        assertEquals(expected, this.address.getApartment());
    }
    /**
     * Тестирует public void setCity(String city).
     */
    @Test
    public void testSetCity() {
        this.address.setCity("Питер");
        assertEquals("Питер", this.address.getCity());
    }
    /**
     * Тестирует public void setHome(int home).
     */
    @Test
    public void testSetHome() {
        final int expected = 100500;
        this.address.setHome(expected);
        assertEquals(expected, this.address.getHome());
    }
    /**
     * Тестирует public void setStreet(String street).
     */
    @Test
    public void testSetStreet() {
        this.address.setStreet("Моховая");
        assertEquals("Моховая", this.address.getStreet());
    }
}