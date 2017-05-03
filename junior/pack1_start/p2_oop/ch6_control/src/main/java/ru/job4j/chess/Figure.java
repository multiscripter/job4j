package ru.job4j.chess;

/**
 * Класс Figure реализует сущность "Шахматная фигура".
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
abstract class Figure {
    /**
     * Объект клетки, в которой находится фигура.
     */
    final Cell position;
    /**
     * Конструктор.
     * @param startPosition объект клетки с начальной позицией.
     */
    Figure(Cell startPosition) {
        this.position = startPosition;
    }
    /**
     * Проводит фигуру по пути от текущей позиции до dist.
     * @param dist клетка назначения.
     * @return массив клеток, пройденных фигурой.
     */
    Cell[] way(Cell dist) throw ImposibleMoveException {
    }
}