package ru.job4j.tracker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс StartUITest тестирует работу приложения Tracker.
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-20
 * @since 2017-04-20
 */
public class StartUITest {
    /**
     * Объект трэкера заявок.
     */
    private Tracker tracker;
    /**
     * Массив заявок.
     */
    private Item[] items;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.tracker = new Tracker();
        int taskQuantity = 9;
        try {
            Prepare pre = new Prepare();
            pre.loadProperties("tracker.properties");
            PgSQLJDBCDriver dbDriver = new PgSQLJDBCDriver(pre.getProperties());
            dbDriver.setup();
            pre.setDbDriver(dbDriver);
            pre.executeSql("junior.pack2.p8.ch4.task2.sql");
            this.items = new Item[taskQuantity];
            // Fill db by test data.
            for (int a = 0; a < taskQuantity; a++) {
                Item item = new Item("task" + a, "description for task" + a);
                this.tracker.add(item);
                this.items[a] = item;
            }
            this.tracker.add(new Item("task" + (taskQuantity - 1), "_description for task" + taskQuantity++));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует функционал добавления новой заявки.
     */
    @Test
    public void checkAddNewTask() {
        try {
            String expected = "Test1";
            Input input = new StubInput(new String[]{"0", expected, "Testing adding new task", "y"});
            new StartUI(input, this.tracker).init();
            String actual = tracker.findByName(expected)[0].getName();
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует функционал отображения всех заявок.
     */
    @Test
    public void checkShowAllItems() {
        try {
            Item[] items = this.tracker.getAll();
            PrintStream original = System.out;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            Input input = new StubInput(new String[]{"1", "y"});
            new StartUI(input, this.tracker).init();
            String actual = out.toString();
            System.setOut(original);
            for (Item expected : items) {
                assertTrue(actual.contains(expected.toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует функционал редактирования заявки.
     */
    @Test
    public void checkEditItem() {
        try {
            String[] expected = {this.items[3].getId(), "task2", "Edited description"};
            Input input = new StubInput(new String[]{"2", expected[0], expected[1], expected[2], "y"});
            new StartUI(input, this.tracker).init();
            Item item = this.tracker.findById(this.items[3].getId());
            String[] actual = {item.getId(), item.getName(), item.getDesc()};
            assertArrayEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует функционал удаления заявки.
     */
    @Test
    public void checkDeleteItem() {
        try {
            String id = this.items[7].getId();
            Input input = new StubInput(new String[]{"3", id, "y"});
            new StartUI(input, this.tracker).init();
            Item actual = this.tracker.findById(id);
            assertTrue(actual.isEmpty());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует функционал поиска заявки по идентификатору.
     */
    @Test
    public void checkFindById() {
        try {
            String id = this.items[8].getId();
            Input input = new StubInput(new String[]{"4", id, "y"});
            new StartUI(input, this.tracker).init();
            String actual = this.tracker.findById(id).getId();
            assertEquals(id, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует функционал поиска заявки по идентификатору с помощью RegExp.
     */
    @Test
    public void checkFindByIdUsingRegExp() {
        try {
            PrintStream original = System.out;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            String id = this.items[8].getId();
            Input input = new StubInput(new String[]{"4", id, "y"});
            new StartUI(input, this.tracker).init();
            String actual = out.toString();
            System.setOut(original);
            String expected = this.tracker.findById(id).toString();
            Pattern pattern = Pattern.compile(expected);
            Matcher matcher = pattern.matcher(actual);
            assertTrue(matcher.find());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует функционал поиска заявки по идентификатору с помощью String.contains().
     */
    @Test
    public void checkFindByIdUsingContains() {
        try {
            String id = this.items[8].getId();
            String expected = this.tracker.findById(id).toString();
            PrintStream original = System.out;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            Input input = new StubInput(new String[]{"4", id, "y"});
            new StartUI(input, this.tracker).init();
            String actual = out.toString();
            System.setOut(original);
            assertTrue(actual.contains(expected));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует функционал поиска заявки по имени.
     */
    @Test
    public void checkFindByName() {
        try {
            String name = "task8";
            Item[] items = this.tracker.findByName(name);
            PrintStream original = System.out;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            Input input = new StubInput(new String[]{"5", name, "y"});
            new StartUI(input, this.tracker).init();
            String actual = out.toString();
            System.setOut(original);
            for (Item expected : items) {
                assertTrue(actual.contains(expected.toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
