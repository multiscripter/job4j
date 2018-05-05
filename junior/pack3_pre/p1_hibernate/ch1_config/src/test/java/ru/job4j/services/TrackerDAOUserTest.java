package ru.job4j.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.config.DBDriver;
import ru.job4j.models.User;
/**
 * Класс TrackerDAOUserTest тестирует класс TrackerDAO на типе User.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-10
 * @since 2018-03-05
 */
public class TrackerDAOUserTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.logger = LogManager.getLogger(this.getClass().getName());
        try {
            this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch1task0", "postgres", "postgresrootpass");
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            this.driver.executeSqlScript(path + "junior.pack3.p1.ch1.task0.sql");
        } catch (IOException | SQLException | URISyntaxException ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void create(E obj).
     */
    @Test
    public void testCreate() {
        try {
            String email = "nofamily@kremlin.gov";
            String pass = "d6d530636f8e051b3ae25dcbb3450acd";
            User user = new User(0, "Безфамильный", "president", email, new Date(-543888000000L), pass);
            TrackerDAO<User> hdb = new TrackerDAO();
            hdb.create(user);
            hdb.close();
            LinkedList<HashMap<String, String>> result = this.driver.select(String.format("select email from users where pass = '%s'", pass));
            assertEquals(email, result.get(0).get("email"));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void delete(E obj).
     */
    @Test
    public void testDelete() {
        try {
            String pass = "a4a4ff13a635bd92d6c69ada92ac2a90";
            User user = new User(3, "Fake", "Fake", "Fake", new Date(200000000000L), "Fake");
            TrackerDAO<User> hdb = new TrackerDAO();
            hdb.delete(user); // Удлаяет запросом: delete from users where id=?
            hdb.close();
            LinkedList<HashMap<String, String>> result = this.driver.select(String.format("select id from users where pass = '%s'", pass));
            assertTrue(result.isEmpty());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<E> read(E obj).
     */
    @Test
    public void testRead() {
        try {
            LinkedList<HashMap<String, String>> result = this.driver.select("select * from users order by id");
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            User[] expected = new User[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (iter.hasNext()) {
                cur = iter.next();
                expected[a++] = new User(Integer.parseInt(cur.get("id")), cur.get("name"), cur.get("login"), cur.get("email"), sdf.parse(cur.get("created")), cur.get("pass"));
            }
            TrackerDAO<User> hdb = new TrackerDAO();
            List<User> users = hdb.read(new User());
            hdb.close();
            assertArrayEquals(expected, users.toArray(new User[users.size()]));
        } catch (NumberFormatException | ParseException | SQLException ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void update(E obj).
     */
    @Test
    public void testUpdate() {
        try {
            User expected = new User(0, "Грудинин", "Колхозник", "grud@cprf.ru", new Date(-290304000000L), "96e449a1e61f6507be460f9a9683e49c");
            TrackerDAO<User> hdb = new TrackerDAO();
            hdb.create(expected);
            expected.setLogin("Фермер-коммунист");
            hdb.update(expected);
            hdb.close();
            LinkedList<HashMap<String, String>> result = this.driver.select(String.format("select * from users where id = %d", expected.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"), result.get(0).get("login"), result.get(0).get("email"), sdf.parse(result.get(0).get("created")), result.get(0).get("pass"));
            assertEquals(expected, actual);
        } catch (NumberFormatException | ParseException | SQLException ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}