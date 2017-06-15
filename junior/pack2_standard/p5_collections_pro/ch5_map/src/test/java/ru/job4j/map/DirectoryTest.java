package ru.job4j.map;

import java.util.GregorianCalendar;
import java.util.Iterator;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс DirectoryTest тестирует класс Directory.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-15
 */
public class DirectoryTest {
    /**
     * Объект cправочника.
     */
    private Directory<String, User> dict;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.dict = new Directory<>();
        for (int a = 1; a < 11; a++) {
            String name = "Replicant" + a;
            User user = new User(name, new GregorianCalendar(2016, a - 1, 10 + a), 0);
            dict.insert(name, user);
        }
    }
    /**
     * Тестирует delete(K key).
     */
    @Test(expected = NullPointerException.class)
    public void testDelete() {
        this.dict.delete("Replicant1");
        this.dict.get("Replicant1");
    }
    /**
     * Тестирует get(K key).
     */
    @Test
    public void testGet() {
        User expected = new User("Replicant5", new GregorianCalendar(2016, 4, 15), 0);
        User result = this.dict.get("Replicant5");
        assertEquals(expected, result);
    }
    /**
     * Тестирует iterator.hasNext().
     */
    @Test
    public void testIteratorHasNext() {
        Iterator iter = this.dict.iterator();
        assertTrue(iter.hasNext());
    }
    /**
     * Тестирует iterator.next().
     */
    @Test
    public void testIteratorNext() {
        Iterator iter = this.dict.iterator();
        User expected = new User("Replicant5", new GregorianCalendar(2016, 4, 15), 0);
        User result = null;
        for (int a = 0; a < 5; a++) {
            if (iter.hasNext()) {
                result = (User) iter.next();
            }
        }
        assertEquals(expected, result);
    }
    /**
     * Тестирует iterator.remove().
     */
    @Test(expected = NullPointerException.class)
    public void testIteratorRemove() {
        Iterator iter = this.dict.iterator();
        while (iter.hasNext()) {
            User u = (User) iter.next();
        }
        iter.remove();
        this.dict.get("Replicant0");
    }
}
