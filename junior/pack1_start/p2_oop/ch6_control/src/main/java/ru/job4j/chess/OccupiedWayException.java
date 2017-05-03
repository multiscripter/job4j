package ru.job4j.chess;

/**
 * Класс OccupiedWayException реализует исключение "Клетка занята".
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class OccupiedWayException extends Exception {
    /**
     * Конструктор.
     */
    public OccupiedWayException() {
        super("Way occupied by another figure.");
    }
}