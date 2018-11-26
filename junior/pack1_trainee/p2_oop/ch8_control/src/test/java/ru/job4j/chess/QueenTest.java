package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс QueenTest тестирует сущность Ферзь (Queen).
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-08
 */
public class QueenTest {
    /**
     * Тестирует move(). d4 -> d8.
     */
    @Test
    public void checkMoveRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d4");
        Queen queen = new Queen(cell, Color.WHITE);
        Cell expected = board.getCell("d8");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, queen.getCell());
    }
    /**
     * Тестирует move(). d4 -> e7.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlus1CellRowPlus() {
        Board board = new Board();
        Cell src = board.getCell("d4");
        Queen queen = new Queen(src, Color.WHITE);
        Cell dest = board.getCell("e7");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d4 -> h4.
     */
    @Test
    public void checkMoveColPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d4");
        Queen queen = new Queen(cell, Color.WHITE);
        Cell expected = board.getCell("h4");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, queen.getCell());
    }
    /**
     * Тестирует move(). d4 -> g2.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlus3CellsRowMinus2Cells() {
        Board board = new Board();
        Cell src = board.getCell("d4");
        Queen queen = new Queen(src, Color.WHITE);
        Cell dest = board.getCell("g2");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d4 -> g1.
     */
    @Test
    public void checkMoveColPlusRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d4");
        Queen queen = new Queen(cell, Color.WHITE);
        Cell expected = board.getCell("g1");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, queen.getCell());
    }
    /**
     * Тестирует move(). d4 -> e2.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlus2CellsRowMinus() {
        Board board = new Board();
        Cell src = board.getCell("d4");
        Queen queen = new Queen(src, Color.WHITE);
        Cell dest = board.getCell("e2");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d4 -> d1.
     */
    @Test
    public void checkMoveRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d4");
        Queen queen = new Queen(cell, Color.WHITE);
        Cell expected = board.getCell("d1");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, queen.getCell());
    }
    /**
     * Тестирует move(). d4 -> c2.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinus2CellsRowMinus() {
        Board board = new Board();
        Cell src = board.getCell("d4");
        Queen queen = new Queen(src, Color.WHITE);
        Cell dest = board.getCell("c2");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d4 -> a1.
     */
    @Test
    public void checkMoveColMinusRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d4");
        Queen queen = new Queen(cell, Color.WHITE);
        Cell expected = board.getCell("a1");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, queen.getCell());
    }
    /**
     * Тестирует move(). d4 -> b1.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinus2CellsRowMinus3Cells() {
        Board board = new Board();
        Cell src = board.getCell("d4");
        Queen queen = new Queen(src, Color.WHITE);
        Cell dest = board.getCell("b1");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d4 -> d1.
     */
    @Test
    public void checkMoveColMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d4");
        Queen queen = new Queen(cell, Color.WHITE);
        Cell expected = board.getCell("d1");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, queen.getCell());
    }
    /**
     * Тестирует move(). d4 -> a5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinusRowPlus1Cell() {
        Board board = new Board();
        Cell src = board.getCell("d4");
        Queen queen = new Queen(src, Color.WHITE);
        Cell dest = board.getCell("a5");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d4 -> a7.
     */
    @Test
    public void checkMoveColMinusRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d4");
        Queen queen = new Queen(cell, Color.WHITE);
        Cell expected = board.getCell("d7");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, queen.getCell());
    }
    /**
     * Тестирует move(). d4 -> c8.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinus1CellRowPlus() {
        Board board = new Board();
        Cell src = board.getCell("d4");
        Queen queen = new Queen(src, Color.WHITE);
        Cell dest = board.getCell("c8");
        board.move(src, dest);
    }
}
