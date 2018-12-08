package ru.job4j.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
//import java.util.SimpleTimeZone;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Item;
/**
 * Класс ItemRepositoryTest тестирует класс ItemRepository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-08
 * @since 2018-03-28
 */
public class ItemRepositoryTest {
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
        // Установить часовой пояс по умолчанию для всего приложения.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        this.logger = LogManager.getLogger(this.getClass().getSimpleName());
        try {
            this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch1task1", "postgres", "postgresrootpass");
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            this.driver.executeSqlScript(path + "../../src/main/resources/junior.pack3.p1.ch1.task1.sql");
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<Item> getItems(HashMap<String, String> params).
     * query: select * from todolist order by id
     */
    @Test
    public void testGetItems() {
        try {
            String query = "select * from todolist order by id";
            List<HashMap<String, String>> result = this.driver.select(query);
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            Item[] expected = new Item[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            while (iter.hasNext()) {
                cur = iter.next();
                expected[a++] = new Item(Integer.parseInt(cur.get("id")), cur.get("item"), cur.get("descr"), sdf.parse(cur.get("created")).getTime(), cur.get("done").equals("t"));
            }
            ItemRepository repos = new ItemRepository();
            HashMap<String, String> params = new HashMap<>();
            params.put("orderBy", "id");
            List<Item> items = repos.getItems(params);
            assertArrayEquals(expected, items.toArray(new Item[items.size()]));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<Item> getItems(HashMap<String, String> params) throws Exception.
     * query: select * from todolist where id < 5 order by id desc
     */
    @Test
    public void testGetItemsWhichIdLowerThenFive() {
        try {
            String query = "select * from todolist where id < 5 order by id desc";
            List<HashMap<String, String>> result = this.driver.select(query);
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            Item[] expected = new Item[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            while (iter.hasNext()) {
                cur = iter.next();
                expected[a++] = new Item(Integer.parseInt(cur.get("id")), cur.get("item"), cur.get("descr"), sdf.parse(cur.get("created")).getTime(), cur.get("done").equals("t"));
            }
            ItemRepository repos = new ItemRepository();
            HashMap<String, String> params = new HashMap<>();
            params.put("whereField", "id");
            params.put("whereOp", "<");
            params.put("whereVal", "5");
            params.put("orderBy", "id");
            params.put("orderDir", "desc");
            List<Item> items = repos.getItems(params);
            assertArrayEquals(expected, items.toArray(new Item[items.size()]));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<Item> getItems(HashMap<String, String> params) throws Exception.
     * query: select * from todolist where id > 0 order by id desc
     */
    @Test
    public void testGetItemsWhichIdGreaterThenZero() {
        try {
            String query = "select * from todolist where id > 0 order by id";
            List<HashMap<String, String>> result = this.driver.select(query);
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            Item[] expected = new Item[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            while (iter.hasNext()) {
                cur = iter.next();
                expected[a++] = new Item(Integer.parseInt(cur.get("id")), cur.get("item"), cur.get("descr"), sdf.parse(cur.get("created")).getTime(), cur.get("done").equals("t"));
            }
            ItemRepository repos = new ItemRepository();
            HashMap<String, String> params = new HashMap<>();
            params.put("whereField", "id");
            params.put("whereOp", ">");
            params.put("whereVal", "0");
            params.put("orderBy", "id");
            List<Item> items = repos.getItems(params);
            assertArrayEquals(expected, items.toArray(new Item[items.size()]));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<Item> getItems(HashMap<String, String> params) throws Exception.
     * query: select * from todolist where id = 1
     */
    @Test
    public void testGetItemsWhichIdEqualsOne() {
        try {
            String query = "select * from todolist where id = 1";
            List<HashMap<String, String>> result = this.driver.select(query);
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            Item[] expected = new Item[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            while (iter.hasNext()) {
                cur = iter.next();
                expected[a++] = new Item(Integer.parseInt(cur.get("id")), cur.get("item"), cur.get("descr"), sdf.parse(cur.get("created")).getTime(), cur.get("done").equals("t"));
            }
            ItemRepository repos = new ItemRepository();
            HashMap<String, String> params = new HashMap<>();
            params.put("whereField", "id");
            params.put("whereOp", "=");
            params.put("whereVal", "1");
            List<Item> items = repos.getItems(params);
            assertArrayEquals(expected, items.toArray(new Item[items.size()]));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует выброс исключения public List<Item> getItems(HashMap<String, String> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test(expected = Exception.class)
    public void testGetItemsThrowsException() throws Exception {
        ItemRepository repos = new ItemRepository();
        HashMap<String, String> params = new HashMap<>();
        params.put("whereField", "id");
        params.put("whereOp", "ololo");
        params.put("whereVal", "ololo");
        List<Item> items = repos.getItems(params);
    }
}