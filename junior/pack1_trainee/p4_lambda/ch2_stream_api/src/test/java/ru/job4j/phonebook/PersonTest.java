package ru.job4j.phonebook;

import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Класс PersonTest тестирует класс Person.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-30
 * @since 2018-11-30
 */
public class PersonTest {
    /**
     * Человек.
     */
    private Person person;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.person = new Person("Дмитрий", "Медведев", "111", "Питер");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.person, this.person);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.person.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.person.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим bvtytv.
     */
    @Test
    public void testEqualsWithObjectWithDifferentName() {
        Person person2 = new Person("Иван", "Медведев", "111", "Питер");
        assertFalse(this.person.equals(person2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другой фамилией.
     */
    @Test
    public void testEqualsWithObjectWithDifferentSurname() {
        Person person2 = new Person("Дмитрий", "Иванов", "111", "Питер");
        assertFalse(this.person.equals(person2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим телефоном.
     */
    @Test
    public void testEqualsWithObjectWithDifferentPhone() {
        Person person2 = new Person("Дмитрий", "Медведев", "222", "Питер");
        assertFalse(this.person.equals(person2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим адресом.
     */
    @Test
    public void testEqualsWithObjectWithDifferentAddress() {
        Person person2 = new Person("Дмитрий", "Медведев", "111", "Москва");
        assertFalse(this.person.equals(person2));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.person.getAddress(), this.person.getName(), this.person.getPhone(), this.person.getSurname());
        assertEquals(expected, this.person.hashCode());
    }
}
