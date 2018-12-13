package ru.job4j.chess;

/**
 * Класс OccupiedPositionException реализует исключение "Позиция занята".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-13
 * @since 2017-05-04
 */
public class OccupiedPositionException extends RuntimeException {
    /**
     * Конструктор.
     * @param position позиция на доске.
     */
    public OccupiedPositionException(String position) {
        super("Occupied position: " + position);
    }
}