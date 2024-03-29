package ru.job4j.chess;

/**
 * Класс Queen реализует сущность "Шахматная фигура Ферзь".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-13
 * @since 2017-05-08
 */
public class Queen extends Figure {
    /**
     * Конструктор.
     * @param startPosition объект поля с начальной позицией.
     * @param color цвет фигуры.
     */
    public Queen(Cell startPosition, Color color) {
        super(startPosition, color, "Queen", color == Color.BLACK ? "U+265B" : "U+2655");
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
        if (Math.abs(src[0] - dst[0]) != Math.abs(src[1] - dst[1]) && Math.abs(src[0] - dst[0]) != 0 && Math.abs(src[1] - dst[1]) != 0) {
            throw new ImposibleMoveException(this.getCell().getPosition(), dest.getPosition());
        }
        return super.way(dest);
    }
}
