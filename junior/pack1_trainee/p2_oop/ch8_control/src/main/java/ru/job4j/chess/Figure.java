package ru.job4j.chess;

/**
 * Класс Figure реализует сущность "Шахматная фигура".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-03
 */
abstract class Figure {
    /**
     * Объект поля, в котором находится фигура.
     */
    private Cell cell;
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
        this.cell.setFigure(this);
        this.cell.getBoard().addFigure(this);
    }
    /**
     * Возвращает текущее поле фигуры.
     * @return текущее поле фигуры.
     */
    public Cell getCell() {
        return this.cell;
    }
    /**
     * Устанавливает поле фигуры.
     * @param cell поле фигуры.
     */
    public void setCell(Cell cell) {
        this.cell = cell;
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
     * Возвращает путь из полей в виде массива от текущего поля (исключительно) до поля dest (включительно).
     * @param dest поле назначения.
     * @return массив полей, пройденных фигурой.
     * @throws ImposibleMoveException исключение "Движение невозможно".
     */
    public Cell[] way(Cell dest) throws ImposibleMoveException {
        int tmpCol = this.cell.getCol();
        int tmpRow = this.cell.getRow();
        int colShift = tmpCol - dest.getCol();
        int rowShift = tmpRow - dest.getRow();
        if (colShift == 0 && rowShift == 0) {
            throw new ImposibleMoveException(this.getCell().getPosition(), dest.getPosition());
        }
        Cell[] way = new Cell[Math.abs(colShift != 0 ? colShift : rowShift)];
        colShift = colShift == 0 ? 0 : colShift < 0 ? 1 : -1;
        rowShift = rowShift == 0 ? 0 : rowShift < 0 ? 1 : -1;
        for (int a = 0; a < way.length; a++) {
            tmpCol += colShift;
            tmpRow += rowShift;
            way[a] = this.cell.getBoard().getCell(tmpCol, tmpRow);
        }
        return way;
    }
}
