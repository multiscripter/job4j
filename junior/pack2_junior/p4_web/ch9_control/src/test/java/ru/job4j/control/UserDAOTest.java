package ru.job4j.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.Address;
import ru.job4j.control.service.MusicType;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Класс UserDAOTest тестирует класс UserDAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-23
 * @since 2017-12-17
 */
public class UserDAOTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Кодировка окружения.
     */
    private String enc;
    /**
     * UserDAO.
     */
    private UserDAO us;
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.driver.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.driver = DBDriver.getInstance();
        if (!this.driver.isDBDriverSet()) {
            this.driver.setDbDriver();
        }
        this.enc = Charset.defaultCharset().toString();
        this.us = new UserDAO();
        this.us.setEncoding(this.enc);
    }
    /**
     * Тестирует public boolean addUser(User user) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testAddUser() {
        try {
            User u5 = new User();
            u5.setAddress(new Address(5, "РФ", "Москва", "ЛДПР"));
            u5.setLogin("zhirik");
            u5.setPass("zhirik");
            u5.setRole(new Role(3, "user"));
            u5.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"))));
            assertTrue(this.us.addUser(u5));
            this.driver.executeSql("delete from users_musictypes where user_id > 5");
            this.driver.executeSql("delete from users where id > 4");
            this.driver.executeSql("delete from addresses where id > 5");
        } catch (NoSuchAlgorithmException | ParseException | SQLException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public boolean deleteUser(final int id) throws SQLException.
     */
    @Test
    public void testDeleteUser() {
        try {
            User u5 = new User();
            u5.setAddress(new Address(5, "РФ", "Москва", "ЛДПР"));
            u5.setLogin("zhirik");
            u5.setPass("zhirik");
            u5.setRole(new Role(3, "user"));
            u5.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"))));
            this.us.addUser(u5);
            assertTrue(this.us.deleteUser(u5.getId()));
        } catch (NoSuchAlgorithmException | ParseException | SQLException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public boolean deleteUser(final int id) throws SQLException.
     * Несуществующий пользователь.
     */
    @Test
    public void testDeleteUserOnNonexistentUser() {
        try {
            User u5 = new User();
            u5.setAddress(new Address(5, "РФ", "Москва", "ЛДПР"));
            u5.setId(100);
            u5.setLogin("zhirik");
            u5.setPass("zhirik");
            u5.setRole(new Role(3, "user"));
            u5.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"))));
            assertFalse(this.us.deleteUser(u5.getId()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public boolean editUser(User user) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testEditUser() {
        try {
            User u5 = new User();
            u5.setAddress(new Address(0, "РФ", "Москва", "ЛДПР"));
            u5.setLogin("zhirik");
            u5.setPass("zhirik");
            u5.setRole(new Role(3, "user"));
            u5.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"))));
            this.us.addUser(u5);
            u5.setAddress(new Address(u5.getAddressId(), "США", "Вашингтон", "Капитолийский холм"));
            u5.setLogin("zhirik_updated");
            u5.setPass("zhirik_updated");
            u5.setRole(new Role(2, "moderator"));
            u5.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"), new MusicType(2, "rock"))));
            assertTrue(this.us.editUser(u5));
            this.us.deleteUser(u5.getId());
        } catch (NoSuchAlgorithmException | ParseException | SQLException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public String getEncoding().
     */
    @Test
    public void testGetEncoding() {
        assertEquals(this.us.getEncoding(), this.enc);
    }
    /**
     * Тестирует public String getPassHash(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetPassHash() {
        try {
            String expected = "fea80f2db003d4ebc4536023814aa885";
            String actual = this.us.getPassHash("Lorem ipsum dolor sit amet");
            assertEquals(expected, actual);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public User getUserByAddress(Address address) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUserByAddress() {
        try {
            int userId = 1;
            Address address = new Address(userId, "РФ", "Москва", "Кремль");
            User expected = this.us.getUserById(userId);
            User actual = this.us.getUserByAddress(address);
            assertEquals(expected, actual);
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public User getUserByAddress(Address address) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     * Адрес не существует.
     */
    @Test
    public void testGetUserByAddressWithNonexistentAddress() {
        try {
            Address address = new Address(100, "Зимбабве", "Москва",
                    "Кремль");
            assertNull(this.us.getUserByAddress(address));
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public User getUserById(final int id) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUserById() {
        try {
            int userId = 1;
            User expected = new User();
            expected.setAddress(new Address(1, "РФ", "Москва", "Кремль"));
            expected.setId(userId);
            expected.setLogin("president");
            expected.setPass("ab788932cee4ff449d2ec584da8af2b7");
            expected.setRole(new Role(1, "administrator"));
            expected.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"), new MusicType(1, "rap"), new MusicType(2, "rock"))));
            User actual = this.us.getUserById(userId);
            assertEquals(expected, actual);
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public User getUserById(final int id) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     * Несуществующий идентификатор.
     */
    @Test
    public void testGetUserByIdWithNonexistentId() {
        try {
            assertNull(this.us.getUserById(-1));
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public User getUserByLogPass(String login, String pass) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUserByLogPass() {
        try {
            String login = "president";
            String pass = "putin";
            User expected = new User();
            expected.setAddress(new Address(1, "РФ", "Москва", "Кремль"));
            expected.setId(1);
            expected.setLogin(login);
            expected.setPass(pass);
            expected.setRole(new Role(1, "administrator"));
            expected.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"), new MusicType(1, "rap"), new MusicType(2, "rock"))));
            User actual = this.us.getUserByLogPass(login, pass);
            assertEquals(expected, actual);
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public User getUserByLogPass(String login, String pass) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     * Несуществующий логин
     */
    @Test
    public void testGetUserByLogPassWithNonexistentLogin() {
        try {
            assertNull(this.us.getUserByLogPass("NonexistentLogin", "pass"));
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<User> getUsersByMusicTypes(final MusicType[] mtypes, final String order, final boolean desc) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUsersByMusicTypes() {
        try {
            LinkedList<User> expected = new LinkedList<>();
            expected.add(this.us.getUserById(1));
            expected.add(this.us.getUserById(2));
            expected.add(this.us.getUserById(3));
            MusicType[] mtypes = new MusicType[2];
            mtypes[0] = new MusicType(1, "rap");
            mtypes[1] = new MusicType(2, "rock");
            LinkedList<User> actual = this.us.getUsersByMusicTypes(mtypes, "id", false);
            assertEquals(expected, actual);
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<User> getUsersByRole(final Role role, final String order, final boolean desc) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUsersByRole() {
        try {
            LinkedList<User> expected = new LinkedList<>();
            expected.add(this.us.getUserById(1));
            expected.add(this.us.getUserById(2));
            LinkedList<User> actual = this.us.getUsersByRole(new Role(1, "administrator"), "id", false);
            assertEquals(expected, actual);
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<User> getUsersByRole(final Role role, final String order, final boolean desc) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     * Несуществующая роль.
     */
    @Test
    public void testGetUsersByRoleWithNonexistentRole() {
        try {
            LinkedList<User> actual = this.us.getUsersByRole(new Role(5,
                    "owner"), "id", false);
            assertTrue(actual.isEmpty());
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<User> getUsers(final String order, final boolean desc) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUsers() {
        try {
            LinkedList<User> expected = new LinkedList<>();
            User u1 = new User();
            u1.setAddress(new Address(1, "РФ", "Москва", "Кремль"));
            u1.setId(1);
            u1.setLogin("president");
            u1.setPass("putin");
            u1.setRole(new Role(1, "administrator"));
            u1.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"), new MusicType(1, "rap"), new MusicType(2, "rock"))));
            expected.add(u1);
            User u2 = new User();
            u2.setAddress(new Address(2, "РФ", "Москва", "ФБК"));
            u2.setId(2);
            u2.setLogin("savior");
            u2.setPass("navalniy");
            u2.setRole(new Role(1, "administrator"));
            u2.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"), new MusicType(2, "rock"))));
            expected.add(u2);
            User u3 = new User();
            u3.setAddress(new Address(3, "РФ", "Брянск", "Брянский лес"));
            u3.setId(3);
            u3.setLogin("bogomaz");
            u3.setPass("bogomaz");
            u3.setRole(new Role(2, "moderator"));
            u3.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(1, "rap"))));
            expected.add(u3);
            User u4 = new User();
            u4.setAddress(new Address(4, "США", "Вашингтон", "Капитолийский холм"));
            u4.setId(4);
            u4.setLogin("tramp");
            u4.setPass("tramp");
            u4.setRole(new Role(2, "moderator"));
            u4.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"))));
            expected.add(u4);
            /*User u5 = new User();
            u5.setAddress(new Address(5, "РФ", "Москва", "ЛДПР"));
            u5.setId(5);
            u5.setLogin("zhirik");
            u5.setPass("zhirik");
            u5.setRole(new Role(3, "user"));
            u5.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"))));
            expected.add(u5);*/
            LinkedList<User> actual = this.us.getUsers("", false);
            assertEquals(expected, actual);
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public LinkedList<User> getUsers(final String order, final boolean desc) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUsersThrowsSQLException() {
        try {
            this.us.getUsers("nonexistens_order", false);
        } catch (IOException | NoSuchAlgorithmException | ParseException | SQLException ex) {
            assertTrue(ex instanceof SQLException);
        }
    }
    /**
     * Тестирует public void setEncoding(String enc).
     */
    @Test
    public void testSetEncoding() {
        this.us.setEncoding("ISO-8859-1");
        assertEquals("ISO-8859-1", this.us.getEncoding());
        this.us.setEncoding(this.enc);
    }
}