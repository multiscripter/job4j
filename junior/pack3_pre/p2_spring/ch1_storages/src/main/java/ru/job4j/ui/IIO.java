package ru.job4j.ui;

/**
 * Интерфейс IIO объявляет методы взаимодействия с вводом данных пользователем.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-03
 * @since 2017-04-19
 */
public interface IIO {
    /**
     * Опрашивает пользователя.
     * @param question вопрос пользователю.
     * @return ответ пользователя.
     */
    String ask(String question);
    /**
     * Опрашивает пользователя.
     * @param question вопрос пользователю.
     * @param range диапазон допустимых значений.
     * @return ответ пользователя.
     */
    int ask(String question, int[] range);
}
