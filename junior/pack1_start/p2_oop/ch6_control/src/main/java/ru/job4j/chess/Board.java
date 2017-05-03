package ru.job4j.chess;

/**
 * Класс Board реализует сущность "шахматная доска".
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class Board {
    /**
     * Массив фигур.
     */
    Figure[] figures;
    /**
     * Двигает фигуру.
     * @param source клетка с передвигаемой фигурой.
     * @param dist клетка назначения.
     */
    boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        return true;
    }
}