package ru.job4j.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
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
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.config.DBDriver;
import ru.job4j.models.User;
/**
 * Класс TrackerRepositoryTest тестирует класс TrackerRepository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-15
 * @since 2018-03-14
 */
public class TrackerRepositoryTest {
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
     * Тестирует public List<Tuple> getFields(T obj, HashMap<String, List<String>> params).
     */
    @Test
    public void testGetFields() {
        try {
            LinkedList<HashMap<String, String>> expected = this.driver.select("select id, name from users order by id");
            TrackerRepository<User> repos = new TrackerRepository();
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
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<T> getObjects(T obj, HashMap<String, List<String>> params).
     */
    @Test
    public void testGetObjects() {
        try {
            LinkedList<HashMap<String, String>> result = this.driver.select("select * from users where id > 1 order by id");
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            User[] expected = new User[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (iter.hasNext()) {
                cur = iter.next();
                expected[a++] = new User(Integer.parseInt(cur.get("id")), cur.get("name"), cur.get("login"), cur.get("email"), sdf.parse(cur.get("created")), cur.get("pass"));
            }
            TrackerRepository<User> repos = new TrackerRepository();
            HashMap<String, String> params = new HashMap<>();
            params.put("whereField", "id");
            params.put("whereOp", ">");
            params.put("whereVal", "1");
            params.put("orderBy", "name");
            params.put("orderDir", "desc");
            List<User> users = repos.getObjects(new User(), params);
            assertArrayEquals(expected, users.toArray(new User[users.size()]));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}