package ru.job4j.htmlcss;

import java.util.Objects;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс RoleTest тестирует класс Role.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
    @Ignore@Before
    public void beforeTest() {
        this.role = new Role(1, "administrator");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Ignore@Test
    public void testEquals() {
        Role expected = this.role;
        Role actual = new Role(1, "administrator");
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public int getId().
     */
    @Ignore@Test
    public void testGetId() {
        assertEquals(1, this.role.getId());
    }
    /**
     * Тестирует public String getName().
     */
    @Ignore@Test
    public void testGetName() {
        assertEquals("administrator", this.role.getName());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Ignore@Test
    public void testHashCode() {
        int expected = Objects.hash(this.role.getId(), this.role.getName());
        assertEquals(expected, this.role.hashCode());
    }
    /**
     * Тестирует public void setId(final int id).
     */
    @Ignore@Test
    public void testSetId() {
        int expected = 100;
        this.role.setId(expected);
        assertEquals(expected, this.role.getId());
    }
    /**
     * Тестирует public void setName(final String name).
     */
    @Ignore@Test
    public void testSetName() {
        String expected = "SuperAdmin";
        this.role.setName(expected);
        assertEquals(expected, this.role.getName());
    }
    /**
     * Тестирует public String toString().
     */
    @Ignore@Test
    public void testToString() {
        assertEquals("Role[id: 1, name: administrator]", this.role.toString());
    }
}