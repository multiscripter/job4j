package ru.job4j.tracker;

/**
 * Класс StartUIApp реализует метод public static void main(String[] args).
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-20
 * @since 2017-04-19
 */
public class StartUIApp {
    /**
     * Точка входа в программу.
     * @param args массив аргументов командной строки.
     */
    public static void main(String[] args) {
        Input input = new StubInput(new String[] {"StubTask1"});
        new StartUI(input).init();
    }
}