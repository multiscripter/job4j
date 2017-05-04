package ru.job4j.chess;

/**
 * Класс OccupiedPositionException реализует исключение "Позиция занята".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-04
 */
class OccupiedPositionException extends RuntimeException {
    /**
     * Конструктор.
     * @param position позиция на доске.
     */
    OccupiedPositionException(String position) {
        super("Occupied position: " + position);
    }
}