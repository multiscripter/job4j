package ru.job4j.tracker;

/**
 * Interface Input реализует интрефэйс ввода.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-19
 */
public interface Input {
    /**
     * Опрашивает пользователя.
     * @param question вопрос пользователю.
     * @return ответ пользователя.
     */
    String ask(String question);
}