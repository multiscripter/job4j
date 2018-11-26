package ru.job4j.task4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс PaintTest тестирует методы класса Paint.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-22
 */
public class PaintTest {
    /**
     * Объект рисования.
     */
    private Paint paint;
    /**
     * Объект потока печати в консоль.
     */
    private PrintStream originalOut;
    /**
     * Объект потока печати массива байтов.
     */
    private ByteArrayOutputStream tempOut;
    /**
     * Строка с символом "новая строка".
     */
    private String ls;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.paint = new Paint();
        this.originalOut = System.out;
        this.tempOut = new ByteArrayOutputStream();
        this.ls = System.getProperty("line.separator");
    }
    /**
     * Тестирует функционал рисования треугольника.
     */
    @Test
    public void checkDrawTriangle() {
        System.setOut(new PrintStream(this.tempOut));
        this.paint.draw(new Triangle());
        String result = this.tempOut.toString();
        System.setOut(this.originalOut);
        String expected = String.format("  *  %s * * %s*****%s", this.ls, this.ls, this.ls);
        assertEquals(expected, result);
    }
    /**
     * Тестирует функционал рисования прямоугольника.
     */
    @Test
    public void checkDrawSquare() {
        System.setOut(new PrintStream(this.tempOut));
        this.paint.draw(new Square());
        String result = this.tempOut.toString();
        System.setOut(this.originalOut);
        String expected = String.format("*****%s*   *%s*****%s", this.ls, this.ls, this.ls);
        assertEquals(expected, result);
    }
}
