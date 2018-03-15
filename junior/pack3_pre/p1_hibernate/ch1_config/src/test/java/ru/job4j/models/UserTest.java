package ru.job4j.models;

import java.util.Date;
import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс UserTest тестирует класс User.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-09
 * @since 2018-03-08
 */
public class UserTest {
    /**
     * Пользователь.
     */
    private User user;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        String email = "nofamily@kremlin.gov";
        String pass = "d6d530636f8e051b3ae25dcbb3450acd";
        this.user = new User(0, "Безфамильный", "president", email, new Date(-543888000000L), pass);
    }
    /**
     * Тестирует public User().
     */
    @Test
    public void testUser() {
        User user = new User();
        assertTrue(user.getClass().getSimpleName().equals("User"));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        User expected = new User(0, "Безфамильный", "president", "nofamily@kremlin.gov", new Date(-543888000000L), "d6d530636f8e051b3ae25dcbb3450acd");
        assertTrue(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        User obj = this.user;
        assertTrue(obj.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.user.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.user.equals(new String()));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        User expected = new User(100500, "Безфамильный", "president", "nofamily@kremlin.gov", new Date(-543888000000L), "d6d530636f8e051b3ae25dcbb3450acd");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        User expected = new User(0, "ololo", "president", "nofamily@kremlin.gov", new Date(-543888000000L), "d6d530636f8e051b3ae25dcbb3450acd");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные login.
     */
    @Test
    public void testEqualsDifferentLogins() {
        User expected = new User(0, "Безфамильный", "ololo", "nofamily@kremlin.gov", new Date(-543888000000L), "d6d530636f8e051b3ae25dcbb3450acd");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные email.
     */
    @Test
    public void testEqualsDifferentEmails() {
        User expected = new User(0, "Безфамильный", "president", "ololo", new Date(-543888000000L), "d6d530636f8e051b3ae25dcbb3450acd");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные created.
     */
    @Test
    public void testEqualsDifferentCreated() {
        User expected = new User(0, "Безфамильный", "president", "nofamily@kremlin.gov", new Date(-290304000000L), "d6d530636f8e051b3ae25dcbb3450acd");
        assertFalse(expected.equals(this.user));
    }
    /**
     * Тестирует public Date getCreated().
     */
    @Test
    public void testGetCreated() {
        assertEquals(new Date(-543888000000L), this.user.getCreated());
    }
    /**
     * Тестирует public String getEmail().
     */
    @Test
    public void testGetEmail() {
        assertEquals("nofamily@kremlin.gov", this.user.getEmail());
    }
    /**
     * Тестирует public int getId().
     */
    @Test
    public void testGetId() {
        assertEquals(0, this.user.getId());
    }
    /**
     * Тестирует public String getLogin().
     */
    @Test
    public void testGetLogin() {
        assertEquals("president", this.user.getLogin());
    }
    /**
     * Тестирует public String getName().
     */
    @Test
    public void testGetName() {
        assertEquals("Безфамильный", this.user.getName());
    }
    /**
     * Тестирует public String getPass().
     */
    @Test
    public void testGetPass() {
        assertEquals("d6d530636f8e051b3ae25dcbb3450acd", this.user.getPass());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(0, "Безфамильный", "president", "nofamily@kremlin.gov", new Date(-543888000000L));
        int actual = this.user.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void setCreated(final Date created).
     */
    @Test
    public void testSetCreated() {
        long expected = -290304000000L;
        this.user.setCreated(new Date(expected));
        assertEquals(expected, this.user.getCreated().getTime());
    }
    /**
     * Тестирует public void setEmail(final String email).
     */
    @Test
    public void testSetEmail() {
        this.user.setEmail("grud@cprf.ru");
        assertEquals("grud@cprf.ru", this.user.getEmail());
    }
    /**
     * Тестирует public void setId(int id).
     */
    @Test
    public void testSetId() {
        this.user.setId(100500);
        assertEquals(100500, this.user.getId());
    }
    /**
     * Тестирует public void setLogin(final String login).
     */
    @Test
    public void testSetLogin() {
        this.user.setLogin("Новый логин");
        assertEquals("Новый логин", this.user.getLogin());
    }
    /**
     * Тестирует public void setName(final String name).
     */
    @Test
    public void testSetName() {
        this.user.setName("Новое имя");
        assertEquals("Новое имя", this.user.getName());
    }
    /**
     * Тестирует public void setPass(String pass).
     */
    @Test
    public void testSetPass() {
        this.user.setPass("96e449a1e61f6507be460f9a9683e49c");
        assertEquals("96e449a1e61f6507be460f9a9683e49c", this.user.getPass());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        String expected = String.format("user[id: %d, name: %s, login: %s, email: %s, created: %s]", 0, "Безфамильный", "president", "nofamily@kremlin.gov", new Date(-543888000000L));
        String actual = this.user.toString();
        assertEquals(expected, actual);
    }
}