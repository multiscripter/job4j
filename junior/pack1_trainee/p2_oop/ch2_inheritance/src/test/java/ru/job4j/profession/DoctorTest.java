package ru.job4j.profession;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Class DoctorTest тестирует методы класса Doctor.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-17
 */
public class DoctorTest {
    /**
     * Объект Doctor.
     */
    private Doctor doctor;
    /**
     * Фамилия врача.
     */
    private String doctorNameLast;
    /**
     * Объект пациента.
     */
    private Patient patient;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.doctorNameLast = "Курпатов";
        this.doctor = new Doctor(this.doctorNameLast, "Андрей", Gender.MALE, new Date(148089600), 18, "");
        this.patient = new Patient("Иванова", "Анна", Gender.FEMALE, new Date(148089600), "Агранулоцитоз");
    }
    /**
     * Тестирует метод public String makeJob(String patientNameLast).
     */
    @Test
    public void testMakeJob() {
        String result = this.doctor.makeJob(this.patient);
        String expected = "Врач " + this.doctorNameLast + " лечит пациента по фамилии " + this.patient.getNameLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует метод public int getPatients().
     */
    @Test
    public void testGetPatients() {
        this.doctor.makeJob(this.patient);
        int result = this.doctor.getPatients();
        int expected = 1;
        assertEquals(expected, result);
    }
    /**
     * Тестирует метод public int getNameLast().
     */
    @Test
    public void testGetNameLast() {
        String result = this.doctor.getNameLast();
        String expected = this.doctorNameLast;
        assertEquals(expected, result);
    }
}