package ru.job4j.control;

import java.util.LinkedList;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.MusicTypeDAO;
import ru.job4j.control.service.MusicType;
/**
 * Класс MusicTypeDAOTest тестирует класс MusicTypeDAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2018-01-10
 */
public class MusicTypeDAOTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * MusicTypeDAO.
     */
    private MusicTypeDAO mts;
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.driver.close();
        } catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.driver = DBDriver.getInstance();
            if (!this.driver.isDBDriverSet()) {
                this.driver.setDbDriver();
            }
            this.mts = new MusicTypeDAO();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<MusicType> getMusicTypesByIds(final String[] ids) throws SQLException.
     */
    @Test
    public void testGetMusicTypesByIds() {
        try {
            int id = 1;
            MusicType expected = new MusicType(id, "rap");
            MusicType actual = this.mts.getMusicTypesByIds(new String[]{Integer.toString(id)}).getFirst();
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public LinkedList<MusicType> getMusicTypesByIds(final String[] ids) throws SQLException.
     */
    @Test
    public void testGetMusicTypeByIdThrowsSQLException() {
        try {
            this.mts.getMusicTypesByIds(new String[]{"-1"});
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<> getMusicTypes() throws SQLException.
     */
    @Test
    public void testGetMusicTypes() {
        try {
            LinkedList<MusicType> expected = new LinkedList<>();
            expected.add(new MusicType(3, "pop"));
            expected.add(new MusicType(1, "rap"));
            expected.add(new MusicType(2, "rock"));
            LinkedList<MusicType> actual = this.mts.getMusicTypes();
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<MusicType> getUserMusicTypesByUserId(final int id) throws SQLException.
     */
    @Test
    public void testGetUserMusicTypesByUserId() {
        try {
            LinkedList<MusicType> expected = new LinkedList<>();
            expected.add(new MusicType(3, "pop"));
            expected.add(new MusicType(1, "rap"));
            expected.add(new MusicType(2, "rock"));
            LinkedList<MusicType> actual = this.mts.getUserMusicTypesByUserId(1);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public LinkedList<MusicType> getUserMusicTypesByUserId(final int id) throws SQLException.
     */
    @Test
    public void testGetUserMusicTypesByUserIdThrowsSQLException() {
        try {
            this.mts.getUserMusicTypesByUserId(-1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}