package ru.job4j.jdbc;

import java.util.Scanner;
/**
 * Класс ConsoleInput реализует сущность ввода из консоли.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
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
    /**
     * Опрашивает пользователя.
     * @param question вопрос пользователю.
     * @param range диапазон допустимых значений.
     * @return ответ пользователя.
     */
    public int ask(String question, int[] range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        }
        throw new MenuOutException("out of menu range");
    }
}
