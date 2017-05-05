package ru.job4j.chess;

/**
 * Класс Bishop реализует сущность "Шахматная фигура Слон".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
        Cell cell = this.getCell();
        int curCol = cell.getCol();
        int destCol = dest.getCol();
        boolean colRise = destCol > curCol;
        int colDist = colRise ? destCol - curCol : curCol - destCol;
        int curRow = cell.getRow();
        int destRow = dest.getRow();
        boolean rowRise = destRow > curRow;
        int rowDist = rowRise ? destRow - curRow : curRow - destRow;
        if (colDist != rowDist) {
            throw new ImposibleMoveException();
        }
        Cell[] way = new Cell[colDist];
        int tmpCol;
        int tmpRow;
        for (int a = 0; a < colDist; a++) {
            if (colRise) {
                tmpCol = curCol + a + 1;
            } else {
                tmpCol = curCol - a - 1;
            }
            if (rowRise) {
                tmpRow = curRow + a + 1;
            } else {
                tmpRow = curRow - a - 1;
            }
            way[a] = cell.getBoard().getCell(tmpCol, tmpRow);
        }
        return way;
    }
}
