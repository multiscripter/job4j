package ru.job4j.chess;

/**
 * Класс Board реализует сущность "Шахматная доска". Поле A1 - чёрное слева внизу.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class Board {
    /**
     * Массив фигур.
     */
    Figure[] figures;
    /**
     * Массив полей доски.
     */
    private final Cell[][] cells;
    /**
     * Конструктор.
     */
    public Board() {
        char cols = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        int count = 0;
        for (int col = 0; col < cols.length; col++) {
            for (int row = 1; row < 9; row++) {
                Color c = count++ % 2 == 0 ? Color.black : Color.white;
                this.cells[col][row] = new Cell(col, row, c);
            }
        }
    }
    /**
     * Двигает фигуру.
     * @param source поле с передвигаемой фигурой.
     * @param dist поле назначения.
     */
    public boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        return true;
    }
}
