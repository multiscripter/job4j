package ru.job4j.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Tuple;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertArrayEquals;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.config.DBDriver;
import ru.job4j.models.User;
/**
 * Класс TrackerRepositoryTest тестирует класс TrackerRepository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-12
 * @since 2018-03-14
 */
public class TrackerRepositoryTest {
    /**
     * Репозиторий.
     */
    private static TrackerRepository<User> repos;
    /**
     * Драйвер бд.
     */
    private static DBDriver driver;
    /**
     * Логгер.
     */
    private static Logger logger = LogManager.getLogger("TrackerRepositoryUserTest");
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
            String db = "H2"; // H2 | HyperSQL | PostgreSQL
            if (db.equals("H2")) {
                // http://www.h2database.com/html/features.html#in_memory_databases
                // В H2 алиасы по умолчанию могут быть выкючены.
                // http://www.h2database.com/html/faq.html#column_names_incorrect
                driver = new DBDriver("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;IFEXISTS=TRUE", "sa", "");
            } else if (db.equals("HyperSQL")) {
                driver = new DBDriver("jdbc:hsqldb:mem:jpack3p1ch1task0;get_column_name=false;ifexists=true", "SA", "");
            } else if (db.equals("PostgreSQL")) {
                driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch1task0", "postgres", "postgresrootpass");
            }
            path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            path = String.format("%s../../src/test/resources/junior.pack3.p1.ch1.task0.%s.sql", path, db);
            repos = new TrackerRepository<>(db);
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
     * Тестирует public List<Tuple> getFields(T obj, HashMap<String, List<String>> params).
     */
    @Test
    public void testGetFields() {
        try {
            LinkedList<HashMap<String, String>> expected = driver.select("select id, name from users order by id");
            HashMap<String, List<String>> params = new HashMap<>();
            params.put("select", Arrays.asList("id", "name"));
            List<Tuple> tuples = repos.getFields(new User(), params);
            LinkedList<HashMap<String, String>> actual = new LinkedList<>();
            for (Tuple tuple : tuples) {
                HashMap<String, String> cur = new HashMap<>();
                cur.put("id", Integer.toString((Integer) tuple.get(0)));
                cur.put("name", (String) tuple.get(1));
                actual.add(cur);
            }
            assertArrayEquals(expected.toArray(new HashMap[expected.size()]), actual.toArray(new HashMap[actual.size()]));
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<T> getObjects(T obj, HashMap<String, List<String>> params).
     */
    @Test
    public void testGetObjects() {
        try {
            LinkedList<HashMap<String, String>> result = driver.select("select * from users where id > 1 order by id");
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            User[] expected = new User[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (iter.hasNext()) {
                cur = iter.next();
                expected[a++] = new User(Integer.parseInt(cur.get("id")), cur.get("name"), cur.get("login"), cur.get("email"), sdf.parse(cur.get("created")), cur.get("pass"));
            }
            HashMap<String, String> params = new HashMap<>();
            params.put("whereField", "id");
            params.put("whereOp", ">");
            params.put("whereVal", "1");
            params.put("orderBy", "name");
            params.put("orderDir", "desc");
            List<User> users = repos.getObjects(new User(), params);
            assertArrayEquals(expected, users.toArray(new User[users.size()]));
        } catch (Exception ex) {
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