package ru.job4j.chess;

/**
 * Класс Figure реализует сущность "Шахматная фигура".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
abstract class Figure {
    /**
     * Объект поля, в которой находится фигура.
     */
    private final Cell cell;
    /**
     * Цвет фигуры: Color.BLACK или Color.WHITE.
     */
    private final Color color;
    /**
     * Название фигуры.
     */
    private final String name;
    /**
     * Юникод-сомвол фигуры.
     */
    private final String unicode;
    /**
     * Конструктор.
     * @param startPosition объект поля с начальной позицией.
     * @param color цвет фигуры.
     * @param name название фигуры.
     * @param unicode юникод-сомвол фигуры.
     */
    Figure(Cell startPosition, Color color, String name, String unicode) {
        if (startPosition.isOccupied()) {
            throw new OccupiedPositionException(startPosition.getPosition());
        }
        this.cell = startPosition;
        this.color = color;
        this.name = name;
        this.unicode = unicode;
    }
    /**
     * Возвращает текущее поле фигуры.
     * @return текущее поле фигуры.
     */
    public Cell getCell() {
        return this.cell;
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
     * Возвращает юникод-сомвол фигуры.
     * @return юникод-сомвол фигуры.
     */
    public String getUnicode() {
        return this.unicode;
    }
    /**
     * Возвращает позицию фигуры в строке вида буквацифра (пример: "e2").
     * @return юникод-сомвол фигуры.
     */
    public String getPosition() {
        return this.cell.getPosition();
    }
    /**
     * Проводит фигуру по пути от текущей позиции до поля dest.
     * @param dest поле назначения.
     * @return массив полей, пройденных фигурой.
     * @throws ImposibleMoveException исключение "Движение невозможно".
     */
    abstract Cell[] way(Cell dest) throws ImposibleMoveException;
}
