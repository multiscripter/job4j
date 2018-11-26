package ru.job4j.condition;

/**
 * Class Triangle описывает треугольник.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version %G%
 * @since 1
 */
public class Triangle {
    /**
     * Точка a.
     */
    private Point a;
    /**
     * Точка b.
     */
    private Point b;
    /**
     * Точка c.
     */
    private Point c;
    /**
     * Конструктор. Создаёт треугольник по трём точкам.
     * @param a точка a.
     * @param b точка b.
     * @param c точка c.
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    /**
     * Возвращает вершину A треугольника.
     * @return точка.
     */
    public Point getA() {
        return this.a;
    }
    /**
     * Возвращает вершину B треугольника.
     * @return точка.
     */
    public Point getB() {
        return this.b;
    }
    /**
     * Возвращает вершину C треугольника.
     * @return точка.
     */
    public Point getC() {
        return this.c;
    }
    /**
     * Возвращает расстояние между точками.
     * @param first первая точка.
     * @param second вторая точка.
     * @return расстояние между точками.
     */
    public double getDistance(Point first, Point second) {
        return Math.sqrt(Math.pow(second.getX() - first.getX(), 2) + Math.pow(second.getY() - first.getY(), 2));
    }
    /**
     * Возвращает площадь треугольника.
     * @return площадь треугольника.
     */
    public double area() {
        if (this.a.is(this.b.getX(), this.b.getY())) {
            return 0;
        }
        double p = 0.5 * (this.getDistance(this.a, this.b) + this.getDistance(this.b, this.c) + this.getDistance(this.c, this.a));
        return Math.sqrt(p * (p - this.getDistance(this.a, this.b)) * (p - this.getDistance(this.b, this.c)) * (p - this.getDistance(this.c, this.a)));
    }
}