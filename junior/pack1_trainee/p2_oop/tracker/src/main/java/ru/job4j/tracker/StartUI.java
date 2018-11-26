package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.function.Consumer;
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
    private ArrayList<Integer> range;
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
        this.range = new ArrayList<>(MenuActions.values().length);
        for (int a = 0; a < MenuActions.values().length; a++) {
            this.range.add(a);
        }
    }
    /**
     * Конструктор.
     * @param input объект класса, реализующего интерфэйс Input.
     * @param tracker объект трэкера.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        this.range = new ArrayList<>(MenuActions.values().length);
        for (int a = 0; a < MenuActions.values().length; a++) {
            this.range.add(a);
        }
    }
    /**
     * Инициализирует трэкер и интерфэйс пользователя.
     */
    public void init() {
        Consumer<String> out = x -> System.out.println(x);
        out.accept(Integer.toString(MenuActions.values().length));
        MenuActions[] actions = MenuActions.values();
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        UserAction exitAction = new UserAction(MenuActions.EXIT) {
            /**
             * Выполняет действие, выбранное пользователем.
             * @param input объект ввода.
             * @param tracker объект трэкера.
             */
            public void execute(Input input, Tracker tracker) {
                out.accept("Buy our program for 10$.");
                System.exit(0);
            }
        };
        menu.addAction(exitAction);
        do {
            menu.show();
            menu.select(input.ask("Select: ", this.range.stream().mapToInt(i -> i).toArray()));
        } while (!"y".equals(this.input.ask("Exit? y|n: ")));
        out.accept("Buy our program for 10$.");
        out.accept("");
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
