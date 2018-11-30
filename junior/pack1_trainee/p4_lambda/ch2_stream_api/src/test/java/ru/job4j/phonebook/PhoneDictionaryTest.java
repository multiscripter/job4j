package ru.job4j.phonebook;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс PhoneDictionaryTest тестирует класс PhoneDictionary.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-30
 * @since 2018-11-30
 */
public class PhoneDictionaryTest {
    /**
     * Телефонный справочник.
     */
    private PhoneDictionary pd;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.pd = new PhoneDictionary();
        this.pd.add(new Person("Владимир", "Путин", "111", "Питер"));
        this.pd.add(new Person("Владимир", "Жинировский", "222", "Москва"));
        this.pd.add(new Person("Алексей", "Навальновский", "12", "Москва"));
    }
    /**
     * Тестирует public boolean add(final Person person).
     */
    @Test
    public void testAdd() {
        assertTrue(this.pd.add(new Person("Михаил", "Прохоров", "333", "Москва")));
    }
    /**
     * Тестирует public List<Person> find(String key).
     * Ключа поиска в имени.
     */
    @Test
    public void testFindKeyInName() {
        List<Person> expected = new ArrayList<>();
        expected.add(new Person("Владимир", "Путин", "111", "Питер"));
        expected.add(new Person("Владимир", "Жинировский", "222", "Москва"));
        List<Person> actual = this.pd.find("Влад");
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<Person> find(String key).
     * Ключа поиска в фамилии.
     */
    @Test
    public void testFindKeyInSurname() {
        List<Person> expected = new ArrayList<>();
        expected.add(new Person("Владимир", "Жинировский", "222", "Москва"));
        expected.add(new Person("Алексей", "Навальновский", "12", "Москва"));
        List<Person> actual = this.pd.find("овский");
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<Person> find(String key).
     * Ключа поиска в телефоне.
     */
    @Test
    public void testFindKeyInPhone() {
        List<Person> expected = new ArrayList<>();
        expected.add(new Person("Владимир", "Путин", "111", "Питер"));
        expected.add(new Person("Алексей", "Навальновский", "12", "Москва"));
        List<Person> actual = this.pd.find("1");
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<Person> find(String key).
     * Ключа поиска в адресе.
     */
    @Test
    public void testFindKeyInAddress() {
        List<Person> expected = new ArrayList<>();
        expected.add(new Person("Владимир", "Жинировский", "222", "Москва"));
        expected.add(new Person("Алексей", "Навальновский", "12", "Москва"));
        List<Person> actual = this.pd.find("Москва");
        assertEquals(expected, actual);
    }
}
