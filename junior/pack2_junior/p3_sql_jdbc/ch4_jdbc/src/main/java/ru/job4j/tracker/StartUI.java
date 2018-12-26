package ru.job4j.tracker;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Класс StartUI реализует сущность пользовательского интрефэйса трэкера.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-20
 * @since 2017-04-19
 */
public class StartUI {
    /**
     * Объект ввода.
     */
    private Input input;
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Массив допустимых значений.
     */
    private ArrayList<Integer> range;
    /**
     * Объект трэкера заявок.
     */
    private Tracker tracker;
    /**
     * Конструктор.
     * @param input объект класса, реализующего интерфэйс Input.
     */
    public StartUI(Input input) {
        this.logger = LogManager.getLogger(this.getClass().getSimpleName());
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
        this.logger = LogManager.getLogger(this.getClass().getSimpleName());
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
        try {
            MenuTracker menu = new MenuTracker(this.input, this.tracker);
            menu.fillActions();
            UserAction exitAction = new UserAction(MenuActions.EXIT) {
                /**
                 * Выполняет действие, выбранное пользователем.
                 *
                 * @param input   объект ввода.
                 * @param tracker объект трэкера.
                 */
                public void execute(Input input, Tracker tracker) {
                    System.out.println("Buy our program for 10$.");
                    System.exit(0);
                }
            };
            menu.addAction(exitAction);
            do {
                menu.show();
                menu.select(input.ask("Select: ", this.range.stream().mapToInt(i -> i).toArray()));

            } while (!"y".equals(this.input.ask("Exit? y|n: ")));
            System.out.println("Buy our program for 10$.");
            System.out.println("");
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
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
