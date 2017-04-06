package ru.job4j.condition;

/**
 * Class Point описывает точку в прямоугольной системе координат.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version %G%
 * @since 1
 */
public class Point {
    /**
     * Координата x.
     */
    private int x;
    /**
     * Координата y.
     */
    private int y;
    /**
     * Конструктор. Создаёт точку на плоскости по координатам.
     * @param x координата на оси x.
     * @param y координата на оси y.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Возвращает координату x точки.
     * @return координата x.
     */
    public int getX() {
        return this.x;
    }
    /**
     * Возвращает координату y точки.
     * @return координата y.
     */
    public int getY() {
        return this.y;
    }
    /**
     * Определяет находится ли точка на функции y(x) = a * x + b.
     * @param a первое число.
     * @param b второе число.
     * @return true если находится, иначе false.
     */
    public boolean is(int a, int b) {
        return this.y == a * this.x + b;
    }
}
