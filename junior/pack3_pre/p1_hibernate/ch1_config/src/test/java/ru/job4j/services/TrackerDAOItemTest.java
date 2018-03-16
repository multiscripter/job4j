package ru.job4j.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import ru.job4j.models.Item;
/**
 * Класс TrackerDAOItemTest тестирует класс TrackerDAO на типе Item.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-10
 * @since 2018-03-08
 */
public class TrackerDAOItemTest {
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
            this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch1task1", "postgres", "postgresrootpass");
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            this.driver.executeSqlScript(path + "junior.pack3.p1.ch1.task1.sql");
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
            String name = "TrackerDAOItemTest";
            String desc = "Текст заявки TrackerDAOItemTest";
            Item expected = new Item(0, 1, name, desc, 0L);
            TrackerDAO<Item> hdb = new TrackerDAO();
            hdb.create(expected);
            hdb.close();
            LinkedList<HashMap<String, String>> result = this.driver.select(String.format("select * from items where id = %d", expected.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            long t = sdf.parse(result.get(0).get("created")).getTime();
            Item actual = new Item(Integer.parseInt(result.get(0).get("id")), Integer.parseInt(result.get(0).get("user_id")), result.get(0).get("name"), result.get(0).get("descr"), t);
            assertEquals(expected, actual);
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
            int id = 1;
            Item item = new Item(id, 0, "Fake", "Fake", 0L);
            TrackerDAO<Item> hdb = new TrackerDAO();
            hdb.delete(item); // Удлаяет запросом: delete from items where id=?
            hdb.close();
            LinkedList<HashMap<String, String>> result = this.driver.select(String.format("select user_id from items where id = %d", id));
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
            LinkedList<HashMap<String, String>> result = this.driver.select("select * from items order by id");
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            Item[] expected = new Item[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            while (iter.hasNext()) {
                cur = iter.next();
                long t = sdf.parse(cur.get("created")).getTime();
                expected[a++] = new Item(Integer.parseInt(cur.get("id")), Integer.parseInt(cur.get("user_id")), cur.get("name"), cur.get("descr"), t);
            }
            TrackerDAO<Item> hdb = new TrackerDAO();
            List<Item> items = hdb.read(new Item());
            hdb.close();
            assertArrayEquals(expected, items.toArray(new Item[items.size()]));
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
            String name = "HibernateDBDriverItemTest";
            String desc = "Текст заявки HibernateDBDriverItemTest";
            Item expected = new Item(0, 1, name, desc, 0L);
            TrackerDAO<Item> hdb = new TrackerDAO();
            hdb.create(expected);
            expected.setDesc("Обновлённый текст заявки.");
            hdb.update(expected);
            hdb.close();
            LinkedList<HashMap<String, String>> result = this.driver.select(String.format("select * from items where id = %d", expected.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            long t = sdf.parse(result.get(0).get("created")).getTime();
            Item actual = new Item(Integer.parseInt(result.get(0).get("id")), Integer.parseInt(result.get(0).get("user_id")), result.get(0).get("name"), result.get(0).get("descr"), t);
            assertEquals(expected, actual);
        } catch (NumberFormatException | ParseException | SQLException ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}