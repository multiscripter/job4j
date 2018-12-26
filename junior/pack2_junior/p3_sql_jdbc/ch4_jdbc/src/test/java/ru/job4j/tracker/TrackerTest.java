package ru.job4j.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс TrackerTest тестирует методы класса Tracker.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-20
 * @since 2017-04-18
 */
public class TrackerTest {
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @Before
    public void beforeTest() throws Exception {
        this.logger = LogManager.getLogger(this.getClass().getSimpleName());
        try {
            Prepare pre = new Prepare();
            pre.loadProperties("tracker.properties");
            PgSQLJDBCDriver dbDriver = new PgSQLJDBCDriver(pre.getProperties());
            dbDriver.setup();
            pre.setDbDriver(dbDriver);
            pre.executeSql("junior.pack2.p8.ch4.task2.sql");
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует Item add(Item item).
     * @throws Exception исключение.
     */
    @Test
    public void testAdd() throws Exception {
        try {
            Tracker tracker = new Tracker();
            Item expected = new Item("Имя1", "Описание1");
            Item result = tracker.add(expected);
            assertEquals(expected, result);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует void update(Item uItem).
     * @throws Exception исключение.
     */
    @Test
    public void testUpdate() throws Exception {
        try {
            Tracker tracker = new Tracker();
            Item expected = new Item("Имя2", "Описание2");
            tracker.add(expected);
            Item found = tracker.findById(expected.getId());
            found.setDesc("Новое описание Заявки2");
            assertTrue(tracker.update(found));
            Item result = tracker.findById(expected.getId());
            assertEquals(expected, result);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует Item findById(String id).
     * @throws Exception исключение.
     */
    @Test
    public void testFindById() throws Exception {
        try {
            Tracker tracker = new Tracker();
            Item expected = new Item("Имя3", "Описание3");
            tracker.add(expected);
            Item result = tracker.findById(expected.getId());
            assertEquals(expected, result);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует Item findByName(String id).
     * @throws Exception исключение.
     */
    @Test
    public void testFindByName() throws Exception {
        try {
            Tracker tracker = new Tracker();
            String name = "Имя3";
            for (int a = 1; a < 6; a++) {
                tracker.add(new Item("Имя" + a, "Описание" + a));
            }
            tracker.add(new Item(name, "Описание10"));
            Item[] found = tracker.findByName(name);
            assertEquals(2, found.length);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует void delete(String id).
     * @throws Exception исключение.
     */
    @Test
    public void testDelete() throws Exception {
        try {
            Tracker tracker = new Tracker();
            String name = "Заявка";
            for (int a = 1; a < 6; a++) {
                tracker.add(new Item(name + a, "Описание" + a));
            }
            Item[] found = tracker.findByName(name + 3);
            assertTrue(tracker.delete(found[0].getId()));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует Item[] getAll().
     * @throws Exception исключение.
     */
    @Test
    public void testGetAll() throws Exception {
        try {
            Tracker tracker = new Tracker();
            Item[] items = new Item[15];
            for (int a = 0; a < 15; a++) {
                Item item = new Item("Имя" + a, "Описание" + a);
                tracker.add(item);
                items[a] = item;
            }
            Item[] all = tracker.getAll();
            assertArrayEquals(items, all);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует int getQuantity().
     * @throws Exception исключение.
     */
    @Test
    public void testGetQuantity() throws Exception {
        try {
            Tracker tracker = new Tracker();
            tracker.add(new Item("Имя1", "Описание1"));
            tracker.add(new Item("Имя2", "Описание2"));
            assertEquals(2, tracker.getQuantity());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
}
