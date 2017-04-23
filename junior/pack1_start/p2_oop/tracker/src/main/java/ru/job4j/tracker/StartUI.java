package ru.job4j.tracker;

/**
 * Класс StartUI реализует сущность пользовательского интрефэйса трэкера.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 5
 * @since 2017-04-19
 */
public class StartUI {
    /**
     * Массив допустимых значений.
     */
    private int[] range = new int[] {0, 1, 2, 3, 4, 5};
    /**
     * Объект ввода.
     */
    private Input input;
    /**
     * Объект трэкера заявок.
     */
    private Tracker tracker;
    /**
     * Конструктор.
     * @param input объект класса, реализующего интерфэйс Input.
     */
    public StartUI(Input input) {
        this.input = input;
        this.tracker = new Tracker();
    }
    /**
     * Конструктор.
     * @param input объект класса, реализующего интерфэйс Input.
     * @param tracker объект трэкера.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }
    /**
     * Инициализирует трэкер и интерфэйс пользователя.
     */
    public void init() {
        MenuActions[] actions = MenuActions.values();
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        do {
            menu.show();
            //String entered = this.input.ask("Select: ");
            //if (!entered.equals("y") && !entered.equals("n")) {
            //    int key = Integer.valueOf(entered);
                menu.select(input.ask("Select: ", this.range));
            //}
        } while (!"y".equals(this.input.ask("Exit? y|n: ")));
        System.out.println("Buy our program for 10$.");
        System.out.println("");
    }
    /**
     * Точка входа в программу.
     * @param args массив аргументов командной строки.
     */
    public static void main(String[] args) {
        Input input = new ValidateInput();
        new StartUI(input).init();
    }
}
