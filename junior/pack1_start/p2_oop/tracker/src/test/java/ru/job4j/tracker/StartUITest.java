package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Класс StartUITest тестирует работу приложения Tracker.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
        Input input = new StubInput(new String[]{"0", expected, "Testing adding new task", "6"});
        new StartUI(input, tracker).init();
        String result = tracker.findByName(expected)[0].getName();
        assertEquals(expected, result);
    }
    /**
     * Тестирует функционал отображения всех заявок.
     */
    @Test
    public void checkShowAllItems() {
        int expected = this.taskQuantity;
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        int result = this.tracker.getQuantity();
        assertEquals(expected, result);
    }
    /**
     * Тестирует функционал редактирования заявки.
     */
    @Test
    public void checkEditItem() {
        String[] expected = {"3", "task2", "Edited description"};
        Input input = new StubInput(new String[]{"2", expected[0], expected[1], expected[2], "1", "6"});
        new StartUI(input, tracker).init();
        Item item = this.tracker.findById(expected[0]);
        String[] result = {item.getId(), item.getName(), item.getDesc()};
        assertEquals(expected, result);
    }
    /**
     * Тестирует функционал удаления заявки.
     */
    @Test
    public void checkDeleteItem() {
        String id = "7";
        Input input = new StubInput(new String[]{"3", id, "6"});
        new StartUI(input, tracker).init();
        Item result = this.tracker.findById(id);
        assertTrue(result.isEmpty());
    }
    /**
     * Тестирует функционал поиска заявки по идентификатору.
     */
    @Test
    public void checkFindById() {
        String id = "8";
        Input input = new StubInput(new String[]{"4", id, "6"});
        new StartUI(input, tracker).init();
        String result = this.tracker.findById(id).getId();
        assertEquals(id, result);
    }
    /**
     * Тестирует функционал поиска заявки по имени.
     */
    @Test
    public void checkFindByName() {
        String name = "task8";
        String[] expected = {name, name};
        Input input = new StubInput(new String[]{"5", name, "6"});
        new StartUI(input, tracker).init();
        Item[] items = this.tracker.findByName(name);
        String[] result = {items[0].getName(), items[1].getName()};
        assertArrayEquals(expected, result);
    }
}