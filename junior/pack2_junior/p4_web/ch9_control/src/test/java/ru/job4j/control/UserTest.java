package ru.job4j.control;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import ru.job4j.control.service.Address;
import ru.job4j.control.service.MusicType;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
/**
 * Класс UserTest тестирует класс User.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2018-01-09
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
        this.user = new User();
        this.user.setAddress(new Address(1, "РФ", "Москва", "Кремль"));
        this.user.setId(1);
        this.user.setLogin("ТестовыйЛогин");
        this.user.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"), new MusicType(2, "rock"))));
        this.user.setPass("testpass");
        this.user.setRole(new Role(1, "administrator"));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        User expected = new User();
        expected.setAddress(new Address(1, "РФ", "Москва", "Кремль"));
        expected.setId(1);
        expected.setLogin("ТестовыйЛогин");
        expected.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"), new MusicType(2, "rock"))));
        expected.setRole(new Role(1, "administrator"));
        assertEquals(expected, this.user);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.user, this.user);
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
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.user.equals(new Integer(0)));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим идентификатором.
     */
    @Test
    public void testEqualsWithObjectWithDifferentId() {
        User user2 = new User();
        user2.setAddress(new Address(1, "РФ", "Москва", "Кремль"));
        user2.setId(-1);
        user2.setLogin("ТестовыйЛогин");
        user2.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"), new MusicType(2, "rock"))));
        user2.setPass("testpass");
        user2.setRole(new Role(1, "administrator"));
        assertFalse(this.user.equals(user2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим логином.
     */
    @Test
    public void testEqualsWithObjectWithDifferentLogin() {
        User user2 = new User();
        user2.setAddress(new Address(1, "РФ", "Москва", "Кремль"));
        user2.setId(1);
        user2.setLogin("ДругойЛогин");
        user2.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"), new MusicType(2, "rock"))));
        user2.setPass("testpass");
        user2.setRole(new Role(1, "administrator"));
        assertFalse(this.user.equals(user2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другой ролью.
     */
    @Test
    public void testEqualsWithObjectWithDifferentRole() {
        User user2 = new User();
        user2.setAddress(new Address(1, "РФ", "Москва", "Кремль"));
        user2.setId(1);
        user2.setLogin("ТестовыйЛогин");
        user2.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"), new MusicType(2, "rock"))));
        user2.setPass("testpass");
        user2.setRole(new Role(2, "moderator"));
        assertFalse(this.user.equals(user2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом с другим адресом.
     */
    @Test
    public void testEqualsWithObjectWithDifferentAddress() {
        User user2 = new User();
        user2.setAddress(new Address(5, "Лимпопо", "ффф", "фффф"));
        user2.setId(1);
        user2.setLogin("ТестовыйЛогин");
        user2.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"), new MusicType(2, "rock"))));
        user2.setPass("testpass");
        user2.setRole(new Role(1, "administrator"));
        assertFalse(this.user.equals(user2));
    }
    /**
     * Тестирует public Address getAddress().
     */
    @Test
    public void testGetAddress() {
        Address expected = new Address(1, "РФ", "Москва", "Кремль");
        assertEquals(expected, this.user.getAddress());
    }
    /**
     * Тестирует public String getAddr().
     */
    @Test
    public void testGetAddr() {
        assertEquals("Кремль", this.user.getAddr());
    }
    /**
     * Тестирует public int getAddressId().
     */
    @Test
    public void testGetAddressId() {
        assertEquals(1, this.user.getAddressId());
    }
    /**
     * Тестирует public String getCountry().
     */
    @Test
    public void testGetCountry() {
        assertEquals("РФ", this.user.getCountry());
    }
    /**
     * Тестирует public String getCity().
     */
    @Test
    public void testGetCity() {
        assertEquals("Москва", this.user.getCity());
    }
    /**
     * Тестирует public int getId().
     */
    @Test
    public void testGetId() {
       assertEquals(1, this.user.getId());
    }
    /**
     * Тестирует public String getLogin().
     */
    @Test
    public void testGetLogin() {
        assertEquals("ТестовыйЛогин", this.user.getLogin());
    }
    /**
     * Тестирует public LinkedList<MusicType> getMusicTypes().
     */
    @Test
    public void testGetMusicTypes() {
        assertEquals(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"), new MusicType(2, "rock"))), this.user.getMusicTypes());
    }
    /**
     * Тестирует public String getMusicTypeNamesAsString().
     */
    @Test
    public void testGetMusicTypeNamesAsString() {
        assertEquals("rap, rock", this.user.getMusicTypeNamesAsString());
    }
    /**
     * Тестирует public String getPass().
     */
    @Test
    public void testGetPass() {
        assertEquals("testpass", this.user.getPass());
    }
    /**
     * Тестирует public Role getRole().
     */
    @Test
    public void testGetRole() {
        Role expected = new Role(1, "administrator");
        Role actual = this.user.getRole();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public int getRoleId().
     */
    @Test
    public void testGetRoleId() {
        assertEquals(1, this.user.getRoleId());
    }
    /**
     * Тестирует public String getRoleName().
     */
    @Test
    public void testGetRoleName() {
        assertEquals("administrator", this.user.getRoleName());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(this.user.getAddress(), this.user.getId(), this.user.getLogin(), this.user.getRole());
        assertEquals(expected, this.user.hashCode());
    }
    /**
     * Тестирует public void setAddress(Address address).
     */
    @Test
    public void testSetAddress() {
        this.user.setAddress(new Address(1, "Египет", "Гиза", "Пирамида"));
        assertEquals(new Address(1, "Египет", "Гиза", "Пирамида"), this.user.getAddress());
    }
    /**
     * Тестирует public void setAddressId(int id).
     */
    @Test
    public void testSetAddressId() {
        int id = 100;
        this.user.setAddressId(id);
        assertEquals(id, this.user.getAddressId());
    }
    /**
     * Тестирует public void setId(int id).
     */
    @Test
    public void testSetId() {
        this.user.setId(100);
        assertEquals(100, this.user.getId());
    }
    /**
     * Тестирует public void setLogin(final String login).
     */
    @Test
    public void testSetLogin() {
        this.user.setLogin("Новый_Тестовый_Логин");
        assertEquals("Новый_Тестовый_Логин", this.user.getLogin());
    }
    /**
     * Тестирует public void setMusicTypes(LinkedList<MusicType> mtypes).
     */
    @Test
    public void testSetMusicTypes() {
        this.user.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(4, "disco"), new MusicType(5, "techno"))));
        assertEquals(new LinkedList<>(Arrays.asList(new MusicType(4, "disco"), new MusicType(5, "techno"))), this.user.getMusicTypes());
    }
    /**
     * Тестирует public void setPass(String pass).
     */
    @Test
    public void testSetPass() {
        this.user.setPass("Новый_Тестовый_Пароль");
        assertEquals("Новый_Тестовый_Пароль", this.user.getPass());
    }
    /**
     * Тестирует public void setRole(Role role).
     */
    @Test
    public void testSetRole() {
        this.user.setRole(new Role(2, "moderator"));
        assertEquals(new Role(2, "moderator"), this.user.getRole());
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        assertEquals("user[id: 1, login: ТестовыйЛогин, music types: [[MusicType[id: 1, name: rap], MusicType[id: 2, name: rock]]], role: Role[id: 1, name: administrator], address: Address[id: 1, country: РФ, city: Москва, addr: Кремль]]", this.user.toString());
    }
}