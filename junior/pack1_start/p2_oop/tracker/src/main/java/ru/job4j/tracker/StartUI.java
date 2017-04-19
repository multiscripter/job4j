package ru.job4j.tracker;

/**
 * Class StartUI реализует сущность пользовательского интрефэйса трэкера.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-19
 */
public class StartUI {
    /**
     * объект ввода.
     */
    private Input input;
    /**
     * Конструктор.
     * @param input объект класса, реализующего интерфэйс Input.
     */
    public StartUI(Input input) {
        this.input = input;
    }
    /**
     * Инициализирует трэкер.
     */
    public void init() {
        String name = this.input.ask("Please, enter the task`s name");
        Tracker tracker = new Tracker();
        tracker.add(new Item("Task1", name, "Description1", new String[0]));
        for (Item item : tracker.getAll()) {
            System.out.println(item.getName());
        }
    }
    /**
     * Точка входа в программу.
     * @param args массив аргументов командной строки.
     */
    public static void main(String[] args) {
        Input input = new ConsoleInput();
        new StartUI(input).init();
    }
}