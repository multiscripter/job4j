package ru.job4j.chess;

/**
 * Класс OccupiedWayException реализует исключение "Поле занято".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class OccupiedWayException extends Exception {
    /**
     * Конструктор.
     */
    OccupiedWayException() {
        super("Way occupied by another figure.");
    }
}