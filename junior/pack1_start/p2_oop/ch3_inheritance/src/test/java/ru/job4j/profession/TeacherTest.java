package ru.job4j.profession;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Class TeacherTest тестирует методы класса Teacher.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-17
 */
public class TeacherTest {
    /**
     * Объект Teacher.
     */
    private Teacher teacher;
    /**
     * Фамилия преподавателя.
     */
    private String teacherNameLast;
    /**
     * Фамилия ученика.
     */
    private Learner learner;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.teacherNameLast = "Коменский";
        this.teacher = new Teacher(this.teacherNameLast, "Ян Амос", Gender.MALE, new Date(7430400), 20, "");
        this.learner = new Learner("Петрова", "Анна", Gender.FEMALE, new Date(148089600), "Прикладная математика");
    }
    /**
     * Тестирует метод public String makeJob(String learnerNameLast).
     */
    @Test
    public void testMakeJob() {
        String result = this.teacher.makeJob(this.learner);
        String expected = "Преподаватель " + this.teacherNameLast + " учит учащегося по фамилии " + this.learner.getNameLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует метод public int getLearners().
     */
    @Test
    public void testGetLearners() {
        this.teacher.makeJob(this.learner);
        this.teacher.makeJob(new Learner("Смирнова", "Алла", Gender.FEMALE, new Date(148089600), "Физика твёрдых тел"));
        int result = this.teacher.getLearners();
        int expected = 2;
        assertEquals(expected, result);
    }
    /**
     * Тестирует метод public int getNameLast().
     */
    @Test
    public void testGetNameLast() {
        String result = this.teacher.getNameLast();
        String expected = this.teacherNameLast;
        assertEquals(expected, result);
    }
}