package ru.job4j.tracker;

import java.util.Scanner;
/**
 * Class ConsoleInput реализует сущность ввода из консоли.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-19
 */
public class ConsoleInput implements Input {
    /**
     * Объект класса Scanner.
     */
    private Scanner scanner = new Scanner(System.in);
    /**
     * Опрашивает пользователя.
     * @param question вопрос пользователю.
     * @return ответ пользователя.
     */
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }
}