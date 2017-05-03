package ru.job4j.chess;

/**
 * Класс Cell реализует сущность "Поле шахматной доски".
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class Cell {
    /**
     * Колонка поля (a-h).
     */
    private final char col;
    /**
     * Ряд поля (1-8).
     */
    private final int row;
    /**
     * Цвет поля: Color.BLACK или Color.WHITE.
     */
    private final Color color;
    /**
     * Конструктор.
     * @param col колонка поля.
     * @param row ряд поля.
     */
    Cell(char col, int row, Color color) {
        this.col = col;
        this.row = row;
        this.color = color;
    }
    /**
     * Возвращает колонку поля.
     * @return колонка поля.
     */
    public char getCol() {
        return this.col;
    }
    /**
     * Возвращает ряд поля.
     * @return ряд поля.
     */
    public int getRow() {
        return this.row;
    }
    /**
     * Возвращает цвет поля.
     * @return цвет поля.
     */
    public Color getColor() {
        return this.color;
    }
}
