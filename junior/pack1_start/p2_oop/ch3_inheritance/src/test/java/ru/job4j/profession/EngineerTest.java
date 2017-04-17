package ru.job4j.profession;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Class EngineerTest тестирует методы класса Engineer.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-17
 */
public class EngineerTest {
    /**
     * Объект Engineer.
     */
    private Engineer engineer;
    /**
     * Фамилия инженера.
     */
    private String engineerNameLast;
    /**
     * Название проекта.
     */
    private String projectName;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.engineerNameLast = "Ильюшин";
        this.engineer = new Engineer(this.engineerNameLast, "Сергей", Gender.MALE, new Date(7430400), 22, "");
        this.projectName = "Ил-2";
    }
    /**
     * Тестирует метод public String makeJob(String projectName).
     */
    @Test
    public void testMakeJob() {
        String result = this.engineer.makeJob(this.projectName);
        String expected = "Инженер " + this.engineerNameLast + " работает над проектом " + this.projectName;
        assertEquals(expected, result);
    }
    /**
     * Тестирует метод public int getProjects().
     */
    @Test
    public void testGetProjects() {
        this.engineer.makeJob(this.projectName);
        this.engineer.makeJob("Ил-12");
        int result = this.engineer.getProjects();
        int expected = 2;
        assertEquals(expected, result);
    }
    /**
     * Тестирует метод public int getNameLast().
     */
    @Test
    public void testGetNameLast() {
        String result = this.engineer.getNameLast();
        String expected = this.engineerNameLast;
        assertEquals(expected, result);
    }
}