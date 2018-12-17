package ru.job4j.chess;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Класс CellTest тестирует сущность Cell.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-17
 * @since 2018-12-17
 */
public class CellTest {
    /**
     * Тестирует public Color getColor().
     */
    @Test
    public void testGetColor() {
        Board board = new Board();
        Cell cell = board.getCell("c1");
        assertEquals(Color.BLACK, cell.getColor());
    }
    /**
     * Тестирует public void setFigure(Figure figure).
     */
    @Test(expected = OccupiedPositionException.class)
    public void testSetFigure() {
        Board board = new Board();
        Cell cell = board.getCell("c1");
        cell.setFigure(new Bishop(cell, Color.BLACK));
        cell.setFigure(null);
    }
}
