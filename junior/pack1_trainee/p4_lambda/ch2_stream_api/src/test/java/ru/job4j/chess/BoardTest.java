package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс BoardTest тестирует класс Board.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-17
 * @since 2017-05-04
 */
public class BoardTest {
    /**
     * Тестирует addFigure().
     */
    @Test
    public void checkIsFigureOnBoard() {
        String position = "c1";
        Board board = new Board();
        Cell cell = board.getCell(position);
        Bishop bishop = new Bishop(cell, Color.BLACK);
        assertEquals(board.getFigureByPosition(position), bishop);
    }
    /**
     * Тестирует пользовательское исключение IllegalPositionException.
     */
    @Test(expected = IllegalPositionException.class)
    public void testIllegalPositionExceptionColGreaterThenH() {
        Board board = new Board();
        board.getCell("I1");
    }
    /**
     * Тестирует пользовательское исключение IllegalPositionException.
     */
    @Test(expected = IllegalPositionException.class)
    public void testIllegalPositionExceptionRowLowerThen1() {
        Board board = new Board();
        board.getCell("c0");
    }
    /**
     * Тестирует пользовательское исключение IllegalPositionException.
     */
    @Test(expected = IllegalPositionException.class)
    public void testIllegalPositionExceptionRowGreaterThen8() {
        Board board = new Board();
        board.getCell("f9");
    }
    /**
     * Тестирует пользовательское исключение FigureNotFoundException.
     */
    @Test(expected = FigureNotFoundException.class)
    public void testMoveFigureNotFoundException() {
        Board board = new Board();
        try {
            board.move(board.getCell("f8"), board.getCell("a1"));
        } catch (ImposibleMoveException | OccupiedWayException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует пользовательское исключение ImposibleMoveException.
     */
    @Test(expected = ImposibleMoveException.class)
    public void testMoveImposibleMoveException() {
        Board board = new Board();
        Cell cell = board.getCell("f8");
        new Bishop(cell, Color.WHITE);
        try {
            board.move(cell, board.getCell("a1"));
        } catch (FigureNotFoundException | OccupiedWayException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует пользовательское исключение OccupiedWayException.
     */
    @Test(expected = OccupiedWayException.class)
    public void testMoveOccupiedWayException() {
        Board board = new Board();
        Cell cell = board.getCell("c8");
        new Bishop(cell, Color.WHITE);
        new Bishop(board.getCell("f5"), Color.BLACK);
        try {
            board.move(cell, board.getCell("h3"));
        } catch (FigureNotFoundException | ImposibleMoveException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует пользовательское исключение OccupiedWayException.
     * col < 0.
     */
    @Test(expected = IllegalPositionException.class)
    public void testCheckCoordsThroughCallGetCellColLessZero() {
        Board board = new Board();
        board.getCell(-1, 1);
    }
    /**
     * Тестирует пользовательское исключение OccupiedWayException.
     * row < 0.
     */
    @Test(expected = IllegalPositionException.class)
    public void testCheckCoordsThroughCallGetCellColGreaterSeven() {
        Board board = new Board();
        board.getCell(8, 1);
    }
}
