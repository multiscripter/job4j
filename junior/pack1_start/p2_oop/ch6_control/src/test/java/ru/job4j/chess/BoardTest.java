package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
/**
 * Класс BoardTest тестирует класс Board.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-04
 */
public class BoardTest {
    /**
     * Тестирует addFigure().
     */
    @Test
    public void testAddFigure() {
        String position = "c1";
        Board board = new Board();
        Cell cell = board.getCell(position);
        Bishop bishop = new Bishop(cell, Color.BLACK);
        board.addFigure(bishop);
        assertEquals(board.getFigureByPosition(position), bishop);
    }
    /**
     * Тестирует move().
     */
    @Test
    public void testMove() {
        String position = "c1";
        Board board = new Board();
        Cell cell = board.getCell(position);
        Bishop bishop = new Bishop(cell, Color.BLACK);
        board.addFigure(bishop);
        try {
            board.move(cell, board.getCell("h6"));
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}