package ru.job4j.chess;

import java.util.Arrays;

/**
 * Класс Board реализует сущность "Шахматная доска". Поле A1 - чёрное слева внизу.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-03
 */
class Board {
    /**
     * Массив фигур.
     */
    private final Figure[] figures;
    /**
     * Указатель в массиве.
     */
    private int figuresPointer;
    /**
     * Массив полей доски.
     */
    private final Cell[][] cells;
    /**
     * Массив названий колонок доски.
     */
    private final char[] cols;
    /**
     * Конструктор.
     */
    Board() {
        this.figures = new Figure[32];
        this.figuresPointer = 0;
        this.cells = new Cell[8][8];
        this.cols = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        int count = 0;
        for (int col = 0; col < this.cols.length; col++) {
            for (int row = 0; row < this.cols.length; row++) {
                Color color = count++ % 2 == 0 ? Color.BLACK : Color.WHITE;
                this.cells[col][row] = new Cell(this, col, row, color);
            }
        }
    }
    /**
     * Проверяет координаты позиции.
     * @param col индекс колонки.
     * @param row индекс ряда.
     */
    private void checkCoords(int col, int row) {
        if (col < 0 || col > 7 || row < 0 || row > 7) {
            throw new IllegalPositionException(String.format("col: %d, row: %d", col, row));
        }
    }
    /**
     * Проверяет координаты позиции.
     * @param position название поля добавляемой фигуры в виде строки буквацифра ("e2").
     * @return true если позиция корректна, иначе false.
     */
    private int[] getPosition(String position) {
        int col = Arrays.binarySearch(this.cols, position.charAt(0));
        if (col == -1) {
            throw new IllegalPositionException(position);
        }
        int row = Integer.valueOf(String.valueOf(position.charAt(1))) - 1;
        this.checkCoords(col, row);
        return new int[]{col, row};
    }
    /**
     * Добавляет фигуру в массив фигур и сдвигает указатель массива.
     * @param figure добавляемая фигура.
     */
    public void addFigure(Figure figure) {
        this.figures[this.figuresPointer++] = figure;
    }
    /**
     * Получает фигуру по позиции.
     * @param position строка с позицией фигуры вида "h6".
     * @return найденная фигура.
     */
    public Figure getFigureByPosition(String position) {
        return this.getCell(position).getFigure();
    }
    /**
     * Получает поле доски.
     * @param position название поля добавляемой фигуры в виде строки буквацифра ("e2").
     * @return массив с индексами колонки и ряда.
     */
    public Cell getCell(String position) {
        int[] pos = this.getPosition(position);
        return this.cells[pos[0]][pos[1]];
    }
    /**
     * Получает поле доски.
     * @param col индекс колонки.
     * @param row индекс ряда.
     * @return массив с индексами колонки и ряда.
     */
    public Cell getCell(int col, int row) {
        this.checkCoords(col, row);
        return this.cells[col][row];
    }
    /**
     * Двигает фигуру.
     * @param source поле с передвигаемой фигурой.
     * @param dest поле назначения.
     * @return true если движение фигуры прощло успешно, иначе false.
     * @throws ImposibleMoveException исключение "Движение невозможно".
     * @throws OccupiedWayException исключение "Поле занято".
     * @throws FigureNotFoundException исключение "Фигура не найдена".
     */
    public boolean move(Cell source, Cell dest) throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Figure figure = source.getFigure();
        Cell[] way = figure.way(dest);
        for (Cell cell : way) {
            if (cell.isOccupied()) {
                throw new OccupiedWayException();
            }
        }
        dest.setFigure(figure);
        return true;
    }
}
