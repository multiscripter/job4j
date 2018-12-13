package ru.job4j.chess;

/**
 * Класс IllegalPositionException реализует исключение "Недопустимая позиция".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-13
 * @since 2017-05-04
 */
public class IllegalPositionException extends RuntimeException {
    /**
     * Конструктор.
     * @param position позиция на доске.
     */
    public IllegalPositionException(String position) {
        super("Illegal position: " + position);
    }
}