package ru.job4j.services;

import java.io.File;
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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.config.DBDriver;
import ru.job4j.models.User;
/**
 * Класс TrackerDAOUserTest тестирует класс TrackerDAO на типе User.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-22
 * @since 2018-03-05
 */
public class TrackerDAOUserTest {
    /**
     * Имя СУБД.
     */
    private static String db = "H2"; // H2 | HyperSQL | PostgreSQL
    /**
     * Item DAO.
     */
    private static TrackerDAO<User> dao;
    /**
     * Драйвер бд.
     */
    private static DBDriver driver;
    /**
     * Логгер.
     */
    private static Logger logger = LogManager.getLogger("TrackerDAOItemTest");
    /**
     * Путь к файлу.
     */
    private static String path;
    /**
     * Действия перед тестом.
     */
    @BeforeClass
    public static void beforeAllTests() {
        try {
            if (db.equals("H2")) {
                // http://www.h2database.com/html/features.html#in_memory_databases
                // В H2 алиасы по умолчанию могут быть выкючены.
                // http://www.h2database.com/html/faq.html#column_names_incorrect
                driver = new DBDriver("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;IFEXISTS=TRUE", "sa", "");
            } else if (db.equals("HyperSQL")) {
                driver = new DBDriver("jdbc:hsqldb:mem:jpack3p1ch1task0;get_column_name=false;ifexists=true", "SA", "");
            } else if (db.equals("PostgreSQL")) {
                driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch1task0", "postgres",
                        "postgresrootpass");
            }
            path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            path = String.format("%s../../src/test/resources/junior.pack3.p1.ch1.task0.%s.sql", path, db);
            dao = new TrackerDAO(String.format("hibernate.%s.cfg.xml", db));
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeEachTest() {
        try {
            driver.executeSqlScript(path);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
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
            User expected = new User(0, "Безфамильный", "president", email, new Date(-543888000000L), pass);
            //LinkedList<HashMap<String, String>> checker = driver.select(String.format("select * from users"));
            //List<User> checker = dao.read(new User());
            //checker.forEach(System.err::println);
            int id = dao.create(expected);
            expected.setId(id);
            LinkedList<HashMap<String, String>> result = driver.select(String.format("select * from users where id = %d", id));
            User actual = new User();
            actual.setId(Integer.parseInt(result.get(0).get("id")));
            actual.setName(result.get(0).get("name"));
            actual.setLogin(result.get(0).get("login"));
            actual.setEmail(result.get(0).get("email"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            actual.setCreated(sdf.parse(result.get(0).get("created")));
            actual.setPass(result.get(0).get("pass"));
            assertEquals(expected, actual);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
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
            dao.delete(user); // Удлаяет запросом: delete from users where id=?
            LinkedList<HashMap<String, String>> result = driver.select(String.format("select id from users where pass = '%s'", pass));
            assertTrue(result.isEmpty());
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<E> read(E obj).
     */
    @Test
    public void testRead() {
        try {
            LinkedList<HashMap<String, String>> result = driver.select("select * from users order by id");
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            User[] expected = new User[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (iter.hasNext()) {
                cur = iter.next();
                expected[a++] = new User(Integer.parseInt(cur.get("id")), cur.get("name"), cur.get("login"), cur.get("email"), sdf.parse(cur.get("created")), cur.get("pass"));
            }
            List<User> users = dao.read(new User());
            assertArrayEquals(expected, users.toArray(new User[users.size()]));
        } catch (NumberFormatException | ParseException | SQLException ex) {
            logger.error("ERROR", ex);
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
            dao.create(expected);
            expected.setLogin("Фермер-коммунист");
            dao.update(expected);
            LinkedList<HashMap<String, String>> result = driver.select(String.format("select * from users where id = %d", expected.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            User actual = new User(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"), result.get(0).get("login"), result.get(0).get("email"), sdf.parse(result.get(0).get("created")), result.get(0).get("pass"));
            assertEquals(expected, actual);
        } catch (NumberFormatException | ParseException | SQLException ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Действия после всех тестов.
     */
    @AfterClass
    public static void afterAllTest() {
        try {
            driver.close();
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}