package ru.job4j.chess;

/**
 * Класс ImposibleMoveException реализует исключение "Движение невозможно".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class ImposibleMoveException extends RuntimeException {
    /**
     * Конструктор.
     */
    ImposibleMoveException() {
        super("Imposible movement.");
    }
}