package ru.job4j.chess;

/**
 * Класс Pawn реализует сущность "Шахматная фигура Пешка".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-08
 */
class Pawn extends Figure {
    /**
     * Конструктор.
     * @param startPosition объект поля с начальной позицией.
     * @param color цвет фигуры.
     */
    Pawn(Cell startPosition, Color color) {
        super(startPosition, color, "Pawn", color == Color.BLACK ? "U+265F" : "U+2659");
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
        if (dst[0] != src[0] || (this.getColor() == Color.BLACK && ((src[1] < dst[1]) || (src[1] - dst[1] > 2 && src[1] == 6) || (src[1] - dst[1] != 1 && src[1] < 6))) || (this.getColor() == Color.WHITE && ((src[1] > dst[1]) || (dst[1] - src[1] > 2 && src[1] == 1) || (dst[1] - src[1] != 1 && src[1] > 1)))) {
            throw new ImposibleMoveException(this.getCell().getPosition(), dest.getPosition());
        }
        Cell[] way = super.way(dest);
        return way;
    }
}
