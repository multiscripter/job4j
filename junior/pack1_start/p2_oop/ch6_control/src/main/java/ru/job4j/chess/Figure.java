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
     * Объект поля, в которой находится фигура.
     */
    private final Cell position;
    /**
     * Цвет фигуры: Color.BLACK или Color.WHITE.
     */
    private final Color color;
    /**
     * Название фигуры.
     */
    private final String name;
    /**
     * Конструктор.
     * @param startPosition объект поля с начальной позицией.
     * @param color цвет фигуры.
     */
    Figure(Cell startPosition, Color color) {
        this.position = startPosition;
        this.color = color;
    }
    /**
     * Возвращает текущее поле фигуры.
     * @return текущее поле фигуры.
     */
    public Cell getPosition() {
        return this.position;
    }
    /**
     * Возвращает цвет фигуры.
     * @return цвет фигуры.
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * Возвращает название фигуры.
     * @return название фигуры.
     */
    public String getName() {
        return this.name;
    } 
    /**
     * Проводит фигуру по пути от текущей позиции до поля dist.
     * @param dist поле назначения.
     * @return массив полей, пройденных фигурой.
     */
    public Cell[] way(Cell dist) throw ImposibleMoveException;
}
