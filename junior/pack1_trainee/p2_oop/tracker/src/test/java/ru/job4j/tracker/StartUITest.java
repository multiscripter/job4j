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
 * @version 2018-12-06
 * @since 2017-04-20
 */
public class StartUITest {
    /**
     * Объект трэкера заявок.
     */
    private Tracker tracker;
    /**
     * Количество заявок.
     */
    private int taskQuantity;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.tracker = new Tracker();
        this.taskQuantity = 9;
        for (int a = 0; a < this.taskQuantity; a++) {
            this.tracker.add(new Item(this.tracker.generateId(), "task" + a, "description for task" + a));
        }
        this.tracker.add(new Item(this.tracker.generateId(), "task" + (this.taskQuantity - 1), "_description for task" + this.taskQuantity++));
    }
    /**
     * Тестирует функционал добавления новой заявки.
     */
    @Test
    public void checkAddNewTask() {
        String expected = "Test1";
        Input input = new StubInput(new String[]{"0", expected, "Testing adding new task", "y"});
        new StartUI(input, this.tracker).init();
        String result = tracker.findByName(expected)[0].getName();
        assertEquals(expected, result);
    }
    /**
     * Тестирует функционал отображения всех заявок.
     */
    @Test
    public void checkShowAllItems() {
        Item[] items = this.tracker.getAll();
        PrintStream original = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Input input = new StubInput(new String[]{"1", "y"});
        new StartUI(input, this.tracker).init();
        String result = out.toString();
        System.setOut(original);
        for (Item expected : items) {
            assertTrue(result.contains(expected.toString()));
        }
    }
    /**
     * Тестирует функционал редактирования заявки.
     */
    @Test
    public void checkEditItem() {
        String[] expected = {"3", "task2", "Edited description"};
        Input input = new StubInput(new String[]{"2", expected[0], expected[1], expected[2], "y"});
        new StartUI(input, this.tracker).init();
        Item item = this.tracker.findById(expected[0]);
        String[] result = {item.getId(), item.getName(), item.getDesc()};
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует функционал удаления заявки.
     */
    @Test
    public void checkDeleteItem() {
        String id = "7";
        Input input = new StubInput(new String[]{"3", id, "y"});
        new StartUI(input, this.tracker).init();
        Item result = this.tracker.findById(id);
        assertTrue(result.isEmpty());
    }
    /**
     * Тестирует функционал поиска заявки по идентификатору.
     */
    @Test
    public void checkFindById() {
        String id = "8";
        Input input = new StubInput(new String[]{"4", id, "y"});
        new StartUI(input, this.tracker).init();
        String result = this.tracker.findById(id).getId();
        assertEquals(id, result);
    }
    /**
     * Тестирует функционал поиска заявки по идентификатору с помощью RegExp.
     */
    @Test
    public void checkFindByIdUsingRegExp() {
        PrintStream original = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String id = "8";
        Input input = new StubInput(new String[]{"4", id, "y"});
        new StartUI(input, this.tracker).init();
        String result = out.toString();
        System.setOut(original);
        String expected = this.tracker.findById(id).toString();
        Pattern pattern = Pattern.compile(expected);
        Matcher matcher = pattern.matcher(result);
        assertTrue(matcher.find());
    }
    /**
     * Тестирует функционал поиска заявки по идентификатору с помощью String.contains().
     */
    @Test
    public void checkFindByIdUsingContains() {
        String id = "8";
        String expected = this.tracker.findById(id).toString();
        PrintStream original = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Input input = new StubInput(new String[]{"4", id, "y"});
        new StartUI(input, this.tracker).init();
        String result = out.toString();
        System.setOut(original);
        assertTrue(result.contains(expected));
    }
    /**
     * Тестирует функционал поиска заявки по имени.
     */
    @Test
    public void checkFindByName() {
        String name = "task8";
        Item[] items = this.tracker.findByName(name);
        PrintStream original = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Input input = new StubInput(new String[]{"5", name, "y"});
        new StartUI(input, this.tracker).init();
        String result = out.toString();
        System.setOut(original);
        for (Item expected : items) {
            assertTrue(result.contains(expected.toString()));
        }
    }
    /**
     * Тестирует пользовательское исключение MenuOutException.
     */
    @Test(expected = MenuOutException.class)
    public void checkMenuOutException() {
        Input input = new StubInput(new String[]{"9"});
        new StartUI(input, this.tracker).init();
    }
}
