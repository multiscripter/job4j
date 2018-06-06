package ru.job4j.models;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.TimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс ItemTest тестирует класс Item.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-25
 * @since 2018-04-20
 */
public class ItemTest {
    /**
     * Элемент TODO-листа.
     */
    private Item item;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        this.item = new Item(1, "Fake Item title", "Fake Item descr", 1524254391000L, false);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Объекты равны.
     */
    @Test
    public void testEqualsObjectAreEqual() {
        Item other = new Item(1, "Fake Item title", "Fake Item descr", 1524254391000L, false);
        assertEquals(this.item, other);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * this == obj.
     */
    @Test
    public void testEquals2refsOfSameObject() {
        Item actual = this.item;
        assertEquals(this.item, actual);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * obj == null.
     */
    @Test
    public void testEqualsObjectIsNull() {
        Item nullItem = null;
        assertFalse(this.item.equals(nullItem));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * this.getClass() != obj.getClass().
     */
    @Test
    public void testEqualsDifferentClasses() {
        assertFalse(this.item.equals(new String()));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * this.id != other.getId().
     */
    @Test
    public void testEqualsDifferentUserId() {
        Item tedui = new Item(2, "Fake Item title", "Fake Item descr", 1524254391000L, false);
        assertFalse(this.item.equals(tedui));
    }
    /**
     * Тестирует public String getCreatedStr().
     */
    @Test
    public void testGetCreatedStr() {
        assertEquals("2018-04-20 19:59:51", this.item.getCreatedStr());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeInMillis(1524254391000L);
        int expected = Objects.hash(1, "Fake Item title", "Fake Item descr", cal, false);
        assertEquals(expected, this.item.hashCode());
    }
    /**
     * Тестирует public void setCreated(final Date created).
     */
    @Test
    public void testSetCreated() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeInMillis(1524254391000L);
        this.item.setCreated(cal.getTime());
        assertEquals(cal.getTime(), this.item.getCreated());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        String expected = "item[id: 1, item: Fake Item title, description: Fake Item descr, created: 2018-04-20 19:59:51, done: false]";
        assertEquals(expected, this.item.toString());
    }
}