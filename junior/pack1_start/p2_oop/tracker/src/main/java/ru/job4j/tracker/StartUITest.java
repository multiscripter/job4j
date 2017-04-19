package ru.job4j.tracker;

/**
 * Class StartUITest реализует сущность тестирования пользовательского интрефэйса трэкера.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-19
 */
public class StartUITest {
    /**
     * Точка входа в программу.
     * @param args массив аргументов командной строки.
     */
    public static void main(String[] args) {
        Input input = new StubInput(new String[] {"StubTask1"});
        new StartUI(input).init();
    }
}