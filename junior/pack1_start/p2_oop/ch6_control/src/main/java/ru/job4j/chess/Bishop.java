package ru.job4j.chess;

/**
 * Класс Bishop реализует сущность "Шахматная фигура Слон".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-04
 */
class Bishop extends Figure {
    /**
     * Конструктор.
     * @param startPosition объект поля с начальной позицией.
     * @param color цвет фигуры.
     */
    Bishop(Cell startPosition, Color color) {
        super(startPosition, color, "Bishop", color == Color.BLACK ? "U+265D" : "U+2657");
    }
    /**
     * Возвращает путь из полей в виде массива от текущего поля (исключительно) до поля dest (включительно).
     * @param dest поле назначения.
     * @return массив полей, пройденных фигурой.
     * @throws ImposibleMoveException исключение "Движение невозможно".
     */
    public Cell[] way(Cell dest) throws ImposibleMoveException {
        Cell[] way = super.way(dest);
        if (Math.abs(this.getCell().getCol() - dest.getCol()) != Math.abs(this.getCell().getRow() - dest.getRow())) {
            throw new ImposibleMoveException();
        }
        return way;
    }
}
