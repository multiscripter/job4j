package ru.job4j.control;

import java.util.Objects;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import ru.job4j.control.service.Role;
/**
 * Класс RoleTest тестирует класс Role.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2017-12-18
 */
public class RoleTest {
    /**
     * Роль.
     */
    private Role role;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.role = new Role(1, "administrator");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Role expected = new Role(1, "administrator");
        Role actual = this.role;
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.role, this.role);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.role.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.role.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим идентификатором.
     */
    @Test
    public void testEqualsWithObjectWithDifferentId() {
        assertFalse(this.role.equals(new Role(-1, "administrator")));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим именем.
     */
    @Test
    public void testEqualsWithObjectWithDifferentName() {
        assertFalse(this.role.equals(new Role(1, "owner")));
    }
    /**
     * Тестирует public int getId().
     */
    @Test
    public void testGetId() {
        assertEquals(1, this.role.getId());
    }
    /**
     * Тестирует public String getName().
     */
    @Test
    public void testGetName() {
        assertEquals("administrator", this.role.getName());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.role.getId(), this.role.getName());
        assertEquals(expected, this.role.hashCode());
    }
    /**
     * Тестирует public void setId(final int id).
     */
    @Test
    public void testSetId() {
        int expected = 100;
        this.role.setId(expected);
        assertEquals(expected, this.role.getId());
    }
    /**
     * Тестирует public void setName(final String name).
     */
    @Test
    public void testSetName() {
        String expected = "SuperAdmin";
        this.role.setName(expected);
        assertEquals(expected, this.role.getName());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        assertEquals("Role[id: 1, name: administrator]", this.role.toString());
    }
}