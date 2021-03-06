package ru.job4j.chess;

/**
 * Класс Knight реализует сущность "Шахматная фигура Конь".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-13
 * @since 2017-05-08
 */
public class Knight extends Figure {
    /**
     * Конструктор.
     * @param startPosition объект поля с начальной позицией.
     * @param color цвет фигуры.
     */
    public Knight(Cell startPosition, Color color) {
        super(startPosition, color, "Knight", color == Color.BLACK ? "U+265E" : "U+2658");
    }
    /**
     * Возвращает путь из полей в виде массива от текущего поля (исключительно) до поля dest (включительно).
     * @param dest поле назначения.
     * @return массив полей, пройденных фигурой.
     * @throws ImposibleMoveException исключение "Движение невозможно".
     */
    public Cell[] way(Cell dest) throws ImposibleMoveException {
        int[] src = new int[] {this.getCell().getCol(), this.getCell().getRow()};
        int[] dst = new int[] {dest.getCol(), dest.getRow()};
        if (Math.abs(dst[0] - src[0]) + Math.abs(dst[1] - src[1]) != 3 || Math.abs(dst[0] - src[0]) == 0 || Math.abs(dst[1] - src[1]) == 0) {
            throw new ImposibleMoveException(this.getCell().getPosition(), dest.getPosition());
        }
        return new Cell[] {dest};
    }
}
