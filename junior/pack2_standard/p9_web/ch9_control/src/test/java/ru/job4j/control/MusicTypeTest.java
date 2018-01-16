package ru.job4j.control;

import java.util.Objects;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import ru.job4j.control.service.MusicType;
/**
 * Класс MusicTypeTest тестирует класс MusicType.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2017-12-18
 */
public class MusicTypeTest {
    /**
     * Музыкальный стиль.
     */
    private MusicType mtype;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.mtype = new MusicType(1, "rap");
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        MusicType expected = new MusicType(1, "rap");
        MusicType actual = this.mtype;
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.mtype, this.mtype);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.mtype.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.mtype.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим идентификатором.
     */
    @Test
    public void testEqualsWithObjectWithDifferentId() {
        assertFalse(this.mtype.equals(new MusicType(5, "rap")));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим именем.
     */
    @Test
    public void testEqualsWithObjectWithDifferentName() {
        assertFalse(this.mtype.equals(new MusicType(1, "jazz")));
    }
    /**
     * Тестирует public int getId().
     */
    @Test
    public void testGetId() {
        assertEquals(1, this.mtype.getId());
    }
    /**
     * Тестирует public String getName().
     */
    @Test
    public void testGetName() {
        assertEquals("rap", this.mtype.getName());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.mtype.getId(), this.mtype.getName());
        assertEquals(expected, this.mtype.hashCode());
    }
    /**
     * Тестирует public void setId(final int id).
     */
    @Test
    public void testSetId() {
        int expected = 100;
        this.mtype.setId(expected);
        assertEquals(expected, this.mtype.getId());
    }
    /**
     * Тестирует public void setName(final String name).
     */
    @Test
    public void testSetName() {
        this.mtype.setName("disco");
        assertEquals("disco", this.mtype.getName());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        assertEquals("MusicType[id: 1, name: rap]", this.mtype.toString());
    }
}