package ru.job4j.chess;

/**
 * Класс FigureNotFoundException реализует исключение "Фигура не найдена".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-13
 * @since 2017-05-03
 */
public class FigureNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     */
    public FigureNotFoundException() {
        super("Figure not found.");
    }
}
