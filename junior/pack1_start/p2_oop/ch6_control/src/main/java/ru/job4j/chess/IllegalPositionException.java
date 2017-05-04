package ru.job4j.chess;

/**
 * Класс IllegalPositionException реализует исключение "Недопустимая позиция".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-04
 */
class IllegalPositionException extends RuntimeException {
    /**
     * Конструктор.
     * @param position позиция на доске.
     */
    IllegalPositionException(String position) {
        super("Illegal position: " + position);
    }
}