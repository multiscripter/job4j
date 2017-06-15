package ru.job4j.map;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс UserTest тестирует класс User.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-07
 */
public class UserTest {
    /**
     * Объект пользователя.
     */
    private User user;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.user = new User("Vasisualiy", new GregorianCalendar(1965, 11, 20), 1);
    }
    /**
     * Тестирует compareTo(). Объекты равны.
     */
    @Test
    public void testCompareTo() {
        User expected = new User("Vasisualiy", new GregorianCalendar(1965, 11, 20), 1);
        assertTrue(expected.compareTo(this.user) == 0);
    }
    /**
     * Тестирует compareTo(). Левый меньше правого.
     */
    @Test
    public void testCompareToLower() {
        User expected = new User("Vasisualiy", new GregorianCalendar(1965, 11, 20), 0);
        assertTrue(expected.compareTo(this.user) < 0);
    }
    /**
     * Тестирует compareTo(). Левый больше правого.
     */
    @Test
    public void testCompareToGreater() {
        User expected = new User("Vasisualiy", new GregorianCalendar(1965, 11, 20), 2);
        assertTrue(expected.compareTo(this.user) > 0);
    }
    /**
     * Тестирует equals(). Объекты равны.
     */
    @Test
    public void testEqualsTrue() {
        User expected = new User("Vasisualiy", new GregorianCalendar(1965, 11, 20), 1);
        assertTrue(expected.equals(this.user));
    }
    /**
     * Тестирует equals(). Объекты не равны.
     */
    @Test
    public void testEqualsFalse() {
        User expected = new User("Vasisualiy", new GregorianCalendar(1965, 11, 20), 2);
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует getBirthday().
     */
    @Test
    public void testGetBirthday() {
        String expected = "1965-11-20";
        String result = this.user.getBirthday();
        assertEquals(expected, result);
    }
    /**
     * Тестирует getBirthdayObj().
     */
    @Test
    public void testGetBirthdayObj() {
        GregorianCalendar expected = new GregorianCalendar(1965, 11, 20);
        GregorianCalendar result = this.user.getBirthdayObj();
        assertEquals(expected, result);
    }
    /**
     * Тестирует getChildren().
     */
    @Test
    public void testGetChildren() {
        assertEquals(1, this.user.getChildren());
    }
    /**
     * Тестирует getName().
     */
    @Test
    public void testGetName() {
        assertEquals("Vasisualiy", this.user.getName());
    }
    /**
     * Тестирует setBirthday().
     */
    @Test
    public void testSetBirthday() {
        GregorianCalendar expected = new GregorianCalendar(1956, 03, 04);
        GregorianCalendar newBirthday = new GregorianCalendar(1956, 03, 04);
        this.user.setBirthday(newBirthday);
        GregorianCalendar result = this.user.getBirthdayObj();
        assertEquals(expected, result);
    }
    /**
     * Тестирует setChildren().
     */
    @Test
    public void testSetChildren() {
        this.user.setChildren(2);
        assertEquals(2, this.user.getChildren());
    }
    /**
     * Тестирует setName().
     */
    @Test
    public void testSetName() {
        this.user.setName("Apolinariy");
        assertEquals("Apolinariy", this.user.getName());
    }
    /**
     * Тестирует setToString().
     */
    @Test
    public void testToString() {
        String expected = "User{name: Vasisualiy, birthday: 1965-11-20, children: 1}";
        String result = this.user.toString();
        assertEquals(expected, result);
    }
    /**
     * Два одинаковых пользователя. Ключ - user, значение user.
     */
    @Test
    public void showMapKeyUserValueUser() {
        User user1 = new User("Vasisualiy", new GregorianCalendar(1965, 11, 20), 1);
        User user2 = new User("Vasisualiy", new GregorianCalendar(1965, 11, 20), 1);
        //System.out.println("user1: " + user1.hashCode());
        //System.out.println("user2: " + user2.hashCode());
        Map<User, Object> map = new HashMap<>();
        map.put(user1, user1);
        map.put(user2, user2);
        //System.out.println(map.toString());
    }
}
