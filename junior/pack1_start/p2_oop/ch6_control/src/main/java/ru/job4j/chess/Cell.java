package ru.job4j.chess;

/**
 * Класс Cell реализует сущность "Поле шахматной доски".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
class Cell {
    /**
     * Ссылка на объект доски.
     */
    private final Board board;
    /**
     * Колонка поля (0-7).
     */
    private final int col;
    /**
     * Ряд поля (0-7).
     */
    private final int row;
    /**
     * Имя поля в виде буквацифра (пример: "e2").
     */
    private final String position;
    /**
     * Цвет поля: Color.BLACK или Color.WHITE.
     */
    private final Color color;
    /**
     * Фигура в поле.
     */
    private Figure figure;
    /**
     * Конструктор.
     * @param board cсылка на объект доски.
     * @param col колонка поля.
     * @param row ряд поля.
     * @param color цвет поля.
     */
    Cell(Board board, int col, int row, Color color) {
        this.board = board;
        this.col = col;
        this.row = row;
        this.position = String.format("%c%d", this.getColName(), this.getRowName());
        this.color = color;
    }
    /**
     * Возвращает cсылку на объект доски.
     * @return cсылка на объект доски.
     */
    public Board getBoard() {
        return this.board;
    }
    /**
     * Возвращает колонку поля (начиная с нуля).
     * @return колонка поля.
     */
    public int getCol() {
        return this.col;
    }
    /**
     * Возвращает букву колонки поля.
     * @return буква колонки поля.
     */
    public char getColName() {
        return (char) ((int) 'a' + this.col);
    }
    /**
     * Возвращает индекс ряда поля (начиная с нуля).
     * @return индекс ряда поля.
     */
    public int getRow() {
        return this.row;
    }
    /**
     * Возвращает номер ряд поля.
     * @return номер ряд поля.
     */
    public int getRowName() {
        return this.row + 1;
    }
    /**
     * Возвращает имя поля.
     * @return имя поля.
     */
    public String getPosition() {
        return this.position;
    }
    /**
     * Возвращает цвет поля.
     * @return цвет поля.
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * Проверяет поле на непустоту.
     * @return true если поле непустое, иначе false;
     */
    public boolean isOccupied() {
        return this.figure != null ? true : false;
    }
    /**
     * Устанавливает фигуру в поле.
     * @param figure фигура.
     */
    public void setFigure(Figure figure) {
        if (this.figure != null) {
            throw new OccupiedPositionException(this.getPosition());
        }
        this.figure = figure;
        this.figure.setCell(this);
    }
    /**
     * Получает фигуру в поле.
     * @return фигура в поле.
     */
    public Figure getFigure() {
        if (this.figure == null) {
            throw new FigureNotFoundException();
        }
        return this.figure;
    }
}
