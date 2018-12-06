package ru.job4j.condition;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

/**
 * Class TriangleTest тестирует методы класса Triangle.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
 * @since 2017-05-12
 */
public class TriangleTest {
    /**
     * Тестирует метод getDistance() класса Triangle.
     */
    @Test
    public void testGetDistance() {
        Triangle triangle = new Triangle(new Point(1, 2), new Point(3, 4), new Point(6, 1));
        double result = triangle.getDistance(triangle.getA(), triangle.getB());
        double expected = 2.8284271247461903;
        assertThat(result, closeTo(expected, 0.01));
        result = triangle.getDistance(triangle.getB(), triangle.getC());
        expected = 4.242640687119285;
        assertThat(result, closeTo(expected, 0.01));
        result = triangle.getDistance(triangle.getC(), triangle.getA());
        expected = 5.0990195135927845;
        assertThat(result, closeTo(expected, 0.01));
    }
    /**
     * Тестирует метод area() класса Triangle.
     */
    @Test
    public void testArea() {
        Triangle triangle = new Triangle(new Point(1, 2), new Point(3, 4), new Point(6, 1));
        double result = triangle.area();
        double expected = 5.999999999874743;
        assertThat(result, closeTo(expected, 0.01));
    }
    /**
     * Тестирует метод area() класса Triangle.
     * A и B имеют одинаковые координаты.
     */
    @Test
    public void testAreaAequalsB() {
        Triangle triangle = new Triangle(new Point(1, 2), new Point(1, 1), new Point(6, 1));
        assertEquals(0.0, triangle.area(), 0.001);
    }
}