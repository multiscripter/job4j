package ru.job4j.pupil;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс SchoolTest тестирует класс School.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-02-23
 * @since 2019-02-23
 */
public class SchoolTest {
    /**
     * Ученики.
     */
    private List<Student> pupils;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforTest() {
        this.pupils = new ArrayList<>();
        this.pupils.add(new Student(20));
        this.pupils.add(new Student(30));
        this.pupils.add(new Student(40));
        this.pupils.add(new Student(50));
        this.pupils.add(new Student(60));
        this.pupils.add(new Student(70));
    }
    /**
     * Тестирует public List<Student> collect(List<Student> students, Predicate<Student> predicat).
     * Ученикии класса А (успеваемость от 70 до 100).
     */
    @Test
    public void testCollectGetPupilsAclass() {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student(70));
        Predicate<Student> predicate = s -> s.getScore() > 69 && s.getScore() < 101;
        School school = new School();
        List<Student> actual = school.collect(pupils, predicate);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<Student> collect(List<Student> students, Predicate<Student> predicat).
     * Ученикии класса Б (успеваемость от 50 до 70).
     */
    @Test
    public void testCollectGetPupilsBclass() {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student(50));
        expected.add(new Student(60));
        Predicate<Student> predicate = s -> s.getScore() > 49 && s.getScore() < 70;
        School school = new School();
        List<Student> actual = school.collect(pupils, predicate);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<Student> collect(List<Student> students, Predicate<Student> predicat).
     * Ученикии класса В (успеваемость от 0 до 50).
     */
    @Test
    public void testCollectGetPupilsCclass() {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student(20));
        expected.add(new Student(30));
        expected.add(new Student(40));
        Predicate<Student> predicate = s -> s.getScore() > -1 && s.getScore() < 50;
        School school = new School();
        List<Student> actual = school.collect(pupils, predicate);
        assertEquals(expected, actual);
    }
}
