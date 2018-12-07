package ru.job4j.testing;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс UserServiceTest тестирует класс UserService.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-12-17
 */
public class UserServiceTest {
    /**
     * UserService.
     */
    private UserService us;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.us = new UserService();
        this.us.setEncoding(Charset.defaultCharset().toString());
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public User getUserById(final int id) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUserById() {
        try {
            GregorianCalendar cal = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("1952-10-07");
            cal.setTime(date);
            User expected = new User(1, "Путин", "president", "putin@kremlin.gov", cal, "ab788932cee4ff449d2ec584da8af2b7", new Role(1, "administrator"));
            User actual = us.getUserById(1);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public User getUserByLogPass(final String login, String pass) throws SQLException, ParseException,  NoSuchAlgorithmException, UnsupportedEncodingException.
     */
    @Test
    public void testGetUserByLogPass() {
        try {
            String login = "savior";
            String pass = "navalniy";
            GregorianCalendar cal = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("1976-06-04");
            cal.setTime(date);
            User expected = new User(2, "Навальный", login, "naval@fbk.ru", cal, "58634504e4d833e42588ecbc266085aa", new Role(2, "user"));
            User actual = this.us.getUserByLogPass(login, pass);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}