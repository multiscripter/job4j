package ru.job4j.control;

/**
 * Класс Position реализует сущность Позиция на поле.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-04
 */
class Position {
    /**
     * Массив координат позиции.
     */
    private final int[] coords = new int[2];
    /**
     * Конструктор.
     * @param x координата по ширине.
     * @param y координата по высоте.
     * @throws IllegalArgumentException если хотя бы одна из координат < 0 или > Integer.MAX_VALUE.
     */
    Position(final int x, final int y) throws IllegalArgumentException {
        if (x < -1 || y < -1 || x > Integer.MAX_VALUE || y > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.coords[0] = x;
        this.coords[1] = y;
    }
    /**
     * Получает координату по ширине.
     * @return координата по ширине.
     */
    public int getX() {
        return this.coords[0];
    }
    /**
     * Получает координату по высоте.
     * @return координата по высоте.
     */
    public int getY() {
        return this.coords[1];
    }
    /**
     * Устанавливает координату по ширине.
     * @param pos объект координат позиции.
     */
    public void set(final Position pos) {
        this.coords[0] = pos.getX();
        this.coords[1] = pos.getY();
    }
    /**
     * Получает координату по ширине.
     * @return координата по ширине.
     */
    @Override
    public String toString() {
        return String.format("Position{x: %d, y: %d}", this.coords[0], this.coords[1]);
    }
}