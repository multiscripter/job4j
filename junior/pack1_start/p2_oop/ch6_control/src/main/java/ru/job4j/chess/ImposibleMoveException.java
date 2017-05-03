package ru.job4j.chess;

/**
 * Класс ImposibleMoveException реализует исключение "Движение невозможно".
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class ImposibleMoveException extends Exception {
    /**
     * Конструктор.
     */
    public ImposibleMoveException() {
        super("Imposible movement.");
    }
}