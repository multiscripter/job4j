package ru.job4j.crud;

/**
 * Класс NoUserAttributeException реализует исключение "Нет атрибутов пользователя".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-06
 */
class NoUserAttributeException extends RuntimeException {
    /**
     * Конструктор.
     */
    NoUserAttributeException() {
        super("No user attributes");
    }
}
