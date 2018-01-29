package ru.job4j.control;

import java.util.Objects;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import ru.job4j.control.service.Address;
/**
 * Класс AddressTest тестирует класс Address.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2018-01-09
 */
public class AddressTest {
    /**
     * Адрес.
     */
    private Address address;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.address = new Address(1, "РФ", "Москва", "Кремль");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Address expected = this.address;
        Address actual = new Address(1, "РФ", "Москва", "Кремль");
        assertEquals(expected, actual);
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
     * Сравнение с объектом с другим идентификатором.
     */
    @Test
    public void testEqualsWithObjectWithDifferentId() {
        assertFalse(this.address.equals(new Address(-1, "РФ", "Москва",
                "Кремль")));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим адресом.
     */
    @Test
    public void testEqualsWithObjectWithDifferentAddr() {
        assertFalse(this.address.equals(new Address(1, "РФ", "Москва",
                "ГУМ")));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим городом.
     */
    @Test
    public void testEqualsWithObjectWithDifferentCity() {
        assertFalse(this.address.equals(new Address(1, "РФ", "Питер",
                "Кремль")));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другой страной.
     */
    @Test
    public void testEqualsWithObjectWithDifferentCountry() {
        assertFalse(this.address.equals(new Address(1, "Лимпопо", "Москва",
                "Кремль")));
    }
    /**
     * Тестирует public String getAddr().
     */
    @Test
    public void testGetAddr() {
        assertEquals("Кремль", this.address.getAddr());
    }
    /**
     * Тестирует public String getCity().
     */
    @Test
    public void testGetCity() {
        assertEquals("Москва", this.address.getCity());
    }
    /**
     * Тестирует public String getCountry().
     */
    @Test
    public void testGetCountry() {
        assertEquals("РФ", this.address.getCountry());
    }
    /**
     * Тестирует public int getId().
     */
    @Test
    public void testGetId() {
        assertEquals(1, this.address.getId());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.address.getAddr(), this.address.getCity(), this.address.getCountry(), this.address.getId());
        assertEquals(expected, this.address.hashCode());
    }
    /**
     * Тестирует public void setAddr(final String addr).
     */
    @Test
    public void testSetAddr() {
        String expected = "Смольный";
        this.address.setAddr("Смольный");
        assertEquals(expected, this.address.getAddr());
    }
    /**
     * Тестирует public void setCity(final String city).
     */
    @Test
    public void testSetCity() {
        String expected = "Питер";
        this.address.setCity("Питер");
        assertEquals(expected, this.address.getCity());
    }
    /**
     * Тестирует public void setCountry(final String country).
     */
    @Test
    public void testSetCountry() {
        String expected = "Лимпопо";
        this.address.setCountry("Лимпопо");
        assertEquals(expected, this.address.getCountry());
    }
    /**
     * Тестирует public void setId(final int id).
     */
    @Test
    public void testSetId() {
        int expected = 100;
        this.address.setId(expected);
        assertEquals(expected, this.address.getId());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        assertEquals("Address[id: 1, country: РФ, city: Москва, addr: Кремль]", this.address.toString());
    }
}