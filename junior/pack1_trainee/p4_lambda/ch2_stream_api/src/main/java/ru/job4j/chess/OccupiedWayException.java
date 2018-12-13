package ru.job4j.chess;

/**
 * Класс OccupiedWayException реализует исключение "Путь занят".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-13
 * @since 2017-05-03
 */
public class OccupiedWayException extends RuntimeException {
    /**
     * Конструктор.
     */
    public OccupiedWayException() {
        super("Way occupied by another figure.");
    }
}