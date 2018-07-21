package ru.job4j.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
//import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
//import org.junit.AfterClass;
import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.config.DBDriver;
import ru.job4j.models.Item;
/**
 * Класс TrackerDAOItemTest тестирует класс TrackerDAO на типе Item.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-21
 * @since 2018-03-08
 */
public class TrackerDAOItemTest {
    /**
     * Имя СУБД.
     */
    private static String db = "H2"; // H2 | HyperSQL | PostgreSQL
    /**
     * Item DAO.
     */
    private static TrackerDAO<Item> dao;
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
    @Before
    public void beforeAllTests() {
        System.err.println("beforeAllTests()");
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
            dao = new TrackerDAO();
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     *
    @Before
    public void beforeEachTest() {
        System.err.println("beforeEachTest()");
        try {
            driver.executeSqlScript(path);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }*/
    /**
     * Тестирует public void create(E obj).
     */
    @Test
    public void testCreate() {
        System.err.println("testCreate()");
        //key: hibernate.connection.url
        //Map<String, Object> props = dao.get();
        //for (String name : props.keySet()) {
        //    System.err.println("key: " + name + ", value: " + props.get(name));
        //}
        try {
            //String q = String.format("select count(*) as count from \"items\"");
            //List<HashMap<String, String>> r = driver.select(q);
            //int count = Integer.parseInt(r.get(0).get("COUNT"));
            //System.err.println("+++ driver. rows: " + count);
            List<Item> items = dao.read(new Item());
            System.err.println("+++ dao. rows: " + items.size());
            String name = "TrackerDAOItemTest";
            String desc = "Текст заявки TrackerDAOItemTest";
            Item expected = new Item(4, 1, name, desc, 0L);
            int id = dao.create(expected);
            //r = driver.select(q);
            //count = Integer.parseInt(r.get(0).get("COUNT"));
            //System.err.println("+++ driver. rows: " + count);
            items = dao.read(new Item());
            System.err.println("+++ dao. rows: " + items.size());
            System.err.println("id: " + id);
            expected.setId(id);
            String query = String.format("select * from items where id = %d", id);
            List<HashMap<String, String>> result = driver.select(query);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            //result.forEach(System.err::println);
            long t = sdf.parse(result.get(0).get("CREATED")).getTime();
            Item actual = new Item(Integer.parseInt(result.get(0).get("ID")), Integer.parseInt(result.get(0).get("USER_ID")), result.get(0).get("NAME"), result.get(0).get("DESCR"), t);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void delete(E obj).
     */
    @Ignore@Test
    public void testDelete() {
        try {
            int id = 1;
            Item item = new Item(id, 0, "Fake", "Fake", 0L);
            dao.delete(item); // Удлаяет запросом: delete from items where id=?
            LinkedList<HashMap<String, String>> result = driver.select(String.format("select user_id from items where id = %d", id));
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
            LinkedList<HashMap<String, String>> result = driver.select("select * from items order by id");
            Iterator<HashMap<String, String>> iter = result.iterator();
            HashMap<String, String> cur;
            Item[] expected = new Item[result.size()];
            int a = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            while (iter.hasNext()) {
                cur = iter.next();
                long t = sdf.parse(cur.get("CREATED")).getTime();
                expected[a++] = new Item(Integer.parseInt(cur.get("ID")), Integer.parseInt(cur.get("USER_ID")), cur.get("NAME"), cur.get("DESCR"), t);
            }
            List<Item> items = dao.read(new Item());
            assertArrayEquals(expected, items.toArray(new Item[items.size()]));
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void update(E obj).
     */
    @Ignore@Test
    public void testUpdate() {
        try {
            String name = "HibernateDBDriverItemTest";
            String desc = "Текст заявки HibernateDBDriverItemTest";
            Item expected = new Item(0, 1, name, desc, 0L);
            dao.create(expected);
            expected.setDesc("Обновлённый текст заявки.");
            dao.update(expected);
            LinkedList<HashMap<String, String>> result = driver.select(String.format("select * from items where id = %d", expected.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            long t = sdf.parse(result.get(0).get("created")).getTime();
            Item actual = new Item(Integer.parseInt(result.get(0).get("id")), Integer.parseInt(result.get(0).get("user_id")), result.get(0).get("name"), result.get(0).get("descr"), t);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Действия после теста.
     */
    @After
    public void afterEachTest() {
        /*System.err.println("afterEachTest()");
        try {
            dao.close();
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }*/
    }
    /**
     * Действия после всех тестов.
     *
    @AfterClass
    public static void afterAllTest() {
        System.err.println("afterAllTest()");
        try {
            //dao.close();
            //driver.close();
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }*/
}