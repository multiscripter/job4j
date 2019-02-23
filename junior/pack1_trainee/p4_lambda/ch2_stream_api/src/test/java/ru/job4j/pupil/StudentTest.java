package ru.job4j.pupil;

import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Класс StudentTest тестирует класс Student.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-02-23
 * @since 2019-02-23
 */
public class StudentTest {
    /**
     * Ученик.
     */
    private Student pupil;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.pupil = new Student(99);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.pupil, this.pupil);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.pupil.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.pupil.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с учеником с другим баллом успеваемости.
     */
    @Test
    public void testEqualsWithObjectWithDifferentScore() {
        Student pupil2 = new Student(100);
        assertFalse(this.pupil.equals(pupil2));
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.pupil.getScore());
        assertEquals(expected, this.pupil.hashCode());
    }
}
