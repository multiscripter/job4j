package ru.job4j.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.SimpleTimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс CommentTest тестирует класс Comment.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-12
 * @since 2018-03-09
 */
public class CommentTest {
    /**
     * Комментарий.
     */
    private Comment comment;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.comment = new Comment(0, 1, 1, "beforeTest() CommentTest.java", 1520549760L);
    }
    /**
     * Тестирует public Comment().
     */
    @Test
    public void testComment() {
        Comment comment = new Comment();
        assertNotNull(comment);
    }
    /**
     * Тестирует public Comment(final int id, final int itemId, final int userId, String comment, long created).
     * createdЖ 0L
     */
    @Test
    public void testCommentCreatedIsZero() {
        Comment comment = new Comment(0, 2, 3, "testCommentCreatedIsZero()", 0L);
        assertTrue(comment.getCreated().getTime() != 0L);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Comment expected = new Comment(0, 1, 1, "beforeTest() CommentTest.java", 1520549760L);
        assertTrue(expected.equals(this.comment));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Comment obj = this.comment;
        assertTrue(obj.equals(this.comment));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.comment.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.comment.equals(new String()));
    }
    /**
     * Тестирует public String getComment().
     */
    @Test
    public void testGetComment() {
        assertEquals("beforeTest() CommentTest.java", this.comment.getComment());
    }
    /**
     * Тестирует public Date getCreated().
     */
    @Test
    public void testGetCreated() {
        assertEquals(new Date(1520549760L), this.comment.getCreated());
    }
    /**
     * Тестирует public int getId().
     */
    @Test
    public void testGetId() {
        assertEquals(0, this.comment.getId());
    }
    /**
     * Тестирует public int getItemId().
     */
    @Test
    public void testGetItemId() {
        assertEquals(1, this.comment.getItemId());
    }
    /**
     * Тестирует public int getUserId().
     */
    @Test
    public void testGetUserId() {
        assertEquals(1, this.comment.getUserId());
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
        int expected = Objects.hash(0, 1, 1, "beforeTest() CommentTest.java", cal);
        int actual = this.comment.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public boolean isEmpty().
     */
    @Test
    public void testIsEmpty() {
        Comment comment = new Comment();
        assertTrue(comment.isEmpty());
    }
    /**
     * Тестирует public boolean isEmpty().
     * id = 0.
     */
    @Test
    public void testIsEmptyWhenIdIsZero() {
        this.comment.setId(0);
        assertFalse(this.comment.isEmpty());
    }
    /**
     * Тестирует public boolean isEmpty().
     * itemId = 0.
     */
    @Test
    public void testIsEmptyWhenItemIdIsZero() {
        this.comment.setItemId(0);
        assertFalse(this.comment.isEmpty());
    }
    /**
     * Тестирует public boolean isEmpty().
     * itemId = 0.
     * userId = 0.
     */
    @Test
    public void testIsEmptyWhenItemIdAndUserIdIsZero() {
        this.comment.setItemId(0);
        this.comment.setUserId(0);
        assertFalse(this.comment.isEmpty());
    }
    /**
     * Тестирует public boolean isEmpty().
     * id != 0.
     */
    @Test
    public void testIsEmptyWhenIdAndItemIdAndUserIdIsZero() {
        this.comment.setId(999);
        assertFalse(this.comment.isEmpty());
    }
    /**
     * Тестирует public void setComment(String comment).
     */
    @Test
    public void testSetComment() {
        this.comment.setComment("Обновлённый текст комментария.");
        assertEquals("Обновлённый текст комментария.", this.comment.getComment());
    }
    /**
     * Тестирует public void setCreated(Date created).
     */
    @Test
    public void testSetCreated() {
        long expected = System.currentTimeMillis();
        Date date = new Date(expected);
        this.comment.setCreated(date);
        assertEquals(expected, this.comment.getCreated().getTime());
    }
    /**
     * Тестирует public void setId(int id).
     */
    @Test
    public void testSetId() {
        this.comment.setId(100500);
        assertEquals(100500, this.comment.getId());
    }
    /**
     * Тестирует public void setItemId(int itemId).
     */
    @Test
    public void testSetItemId() {
        this.comment.setItemId(999);
        assertEquals(999, this.comment.getItemId());
    }
    /**
     * Тестирует public void setUserId(int userId).
     */
    @Test
    public void testSetUserId() {
        this.comment.setUserId(999);
        assertEquals(999, this.comment.getUserId());
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
        String expected = String.format("comment[id: 0, item id: 1, user id: 1, comment: beforeTest() CommentTest.java, created: %s]", cal.getTime());
        assertEquals(expected, this.comment.toString());
    }
}