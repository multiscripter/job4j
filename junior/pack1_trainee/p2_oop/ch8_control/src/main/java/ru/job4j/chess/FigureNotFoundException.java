package ru.job4j.chess;

/**
 * Класс FigureNotFoundException реализует исключение "Фигура не найдена".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class FigureNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     */
    FigureNotFoundException() {
        super("Figure not found.");
    }
}
