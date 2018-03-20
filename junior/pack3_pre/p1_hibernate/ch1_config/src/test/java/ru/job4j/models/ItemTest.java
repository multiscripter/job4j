package ru.job4j.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.SimpleTimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс ItemTest тестирует класс Item.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-09
 * @since 2018-03-08
 */
public class ItemTest {
    /**
     * Заявка.
     */
    private Item item;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.item = new Item(0, 1, "Имя заявки", "Текст заявки", 1520549760L);
    }
    /**
     * Тестирует public Item().
     */
    @Test
    public void testItem() {
        Item item = new Item();
        assertTrue(item != null);
    }
    /**
     * Тестирует public Item(final int id, final int userId, String name, String desc, long created).
     * createdЖ 0L
     */
    @Test
    public void testItemCreatedIsZero() {
        Item item = new Item(0, 1, "Имя заявки", "Текст заявки", 0L);
        assertTrue(item.getCreated().getTime() != 0L);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Item expected = new Item(0, 1, "Имя заявки", "Текст заявки", 1520549760L);
        assertTrue(expected.equals(this.item));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Item obj = this.item;
        assertTrue(obj.equals(this.item));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.item.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.item.equals(new String()));
    }
    /**
     * Тестирует public Date getCreated().
     */
    @Test
    public void testGetCreated() {
        assertEquals(new Date(1520549760L), this.item.getCreated());
    }
    /**
     * Тестирует public String getDesc().
     */
    @Test
    public void testGetDesc() {
        assertEquals("Текст заявки", this.item.getDesc());
    }
    /**
     * Тестирует public int getId().
     */
    @Test
    public void testGetId() {
        assertEquals(0, this.item.getId());
    }
    /**
     * Тестирует public String getName().
     */
    @Test
    public void testGetName() {
        assertEquals("Имя заявки", this.item.getName());
    }
    /**
     * Тестирует public int getUserId().
     */
    @Test
    public void testGetUserId() {
        assertEquals(1, this.item.getUserId());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "GMT");
        tz.setID("GMT+00:00");
        GregorianCalendar cal = new GregorianCalendar(tz);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeInMillis(1520549760L);
        int expected = Objects.hash(0, 1, "Имя заявки", "Текст заявки", cal);
        int actual = this.item.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public boolean isEmpty().
     */
    @Test
    public void testIsEmpty() {
        Item item = new Item();
        assertTrue(item.isEmpty());
    }
    /**
     * Тестирует public boolean isEmpty().
     * userId = 0.
     */
    @Test
    public void testIsEmptyWhenUserIdIsZero() {
        this.item.setUserId(0);
        assertFalse(this.item.isEmpty());
    }
    /**
     * Тестирует public boolean isEmpty().
     * name = null.
     */
    @Test
    public void testIsEmptyWhenNameIsNull() {
        this.item.setName(null);
        assertFalse(this.item.isEmpty());
    }
    /**
     * Тестирует public boolean isEmpty().
     * name = "".
     */
    @Test
    public void testIsEmptyWhenUserIdIsZeroAndNameIsNull() {
        this.item.setUserId(0);
        this.item.setName(null);
        assertFalse(this.item.isEmpty());
    }
    /**
     * Тестирует public boolean isEmpty().
     * id != 0.
     */
    @Test
    public void testIsEmptyWhenIdAndItemIdAndUserIdIsZero() {
        this.item.setId(999);
        assertFalse(this.item.isEmpty());
    }
    /**
     * Тестирует public void setCreated(Date created).
     */
    @Test
    public void testSetCreated() {
        long expected = System.currentTimeMillis();
        Date date = new Date(expected);
        this.item.setCreated(date);
        assertEquals(expected, this.item.getCreated().getTime());
    }
    /**
     * Тестирует public void setDesc(String desc).
     */
    @Test
    public void testSetDesc() {
        this.item.setDesc("Обновлённый текст заявки.");
        assertEquals("Обновлённый текст заявки.", this.item.getDesc());
    }
    /**
     * Тестирует public void setId(int id).
     */
    @Test
    public void testSetId() {
        this.item.setId(100500);
        assertEquals(100500, this.item.getId());
    }
    /**
     * Тестирует public void setName(String name).
     */
    @Test
    public void testSetName() {
        this.item.setName("Новое имя заявки");
        assertEquals("Новое имя заявки", this.item.getName());
    }
    /**
     * Тестирует public void setUserId(int userId).
     */
    @Test
    public void testSetUserId() {
        this.item.setUserId(999);
        assertEquals(999, this.item.getUserId());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "GMT");
        tz.setID("GMT+00:00");
        GregorianCalendar cal = new GregorianCalendar(tz);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeInMillis(1520549760L);
        String expected = String.format("item[id: 0, user id: 1, name: Имя заявки, description: Текст заявки, created: %s]", cal.getTime());
        assertEquals(expected, this.item.toString());
    }
}