package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс PawnTest тестирует сущность Пешка (Pawn).
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-05-08
 * @since 2017-05-08
 */
public class PawnTest {
    /**
     * Тестирует move(). e2 -> e3.
     */
    @Test
    public void checkMoveWhiteStartRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("e2");
        Pawn pawn = new Pawn(cell, Color.WHITE);
        Cell expected = board.getCell("e3");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, pawn.getCell());
    }
    /**
     * Тестирует move(). e2 -> e4.
     */
    @Test
    public void checkMoveWhiteStartRowPlus2Cells() {
        Board board = new Board();
        Cell cell = board.getCell("e2");
        Pawn pawn = new Pawn(cell, Color.WHITE);
        Cell expected = board.getCell("e4");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, pawn.getCell());
    }
    /**
     * Тестирует move(). e2 -> e5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteStartRowPlus3cells() {
        Board board = new Board();
        Cell src = board.getCell("e2");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("e5");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e2 -> f3.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteStartColPlusRowPlus() {
        Board board = new Board();
        Cell src = board.getCell("e2");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("f3");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e2 -> f2.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteStartColPlus() {
        Board board = new Board();
        Cell src = board.getCell("e2");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("f2");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e2 -> f1.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteStartColPlusRowMinus() {
        Board board = new Board();
        Cell src = board.getCell("e2");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("f1");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e2 -> e1.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteStartRowMinus() {
        Board board = new Board();
        Cell src = board.getCell("e2");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("e1");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e2 -> d1.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteStartColMinusRowMinus() {
        Board board = new Board();
        Cell src = board.getCell("e2");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("d1");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e2 -> d2.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteStartColMinus() {
        Board board = new Board();
        Cell src = board.getCell("e2");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("d2");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e2 -> d3.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteStartColMinusRowPlus() {
        Board board = new Board();
        Cell src = board.getCell("e2");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("d3");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e3 -> e4.
     */
    @Test
    public void checkMoveWhiteRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("e3");
        Pawn pawn = new Pawn(cell, Color.WHITE);
        Cell expected = board.getCell("e4");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, pawn.getCell());
    }
    /**
     * Тестирует move(). e3 -> e5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveWhiteRowPlus2Cells() {
        Board board = new Board();
        Cell src = board.getCell("e3");
        Pawn pawn = new Pawn(src, Color.WHITE);
        Cell dest = board.getCell("e5");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). e7 -> e6.
     */
    @Test
    public void checkMoveBlackStartRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("e7");
        Pawn pawn = new Pawn(cell, Color.BLACK);
        Cell expected = board.getCell("e6");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, pawn.getCell());
    }
    /**
     * Тестирует move(). e7 -> e5.
     */
    @Test
    public void checkMoveBlackStartRowMinus2Cells() {
        Board board = new Board();
        Cell cell = board.getCell("e7");
        Pawn pawn = new Pawn(cell, Color.BLACK);
        Cell expected = board.getCell("e5");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, pawn.getCell());
    }
    /**
     * Тестирует move(). e7 -> e4.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveBlackStartRowPlus3Cells() {
        Board board = new Board();
        Cell src = board.getCell("e7");
        Pawn pawn = new Pawn(src, Color.BLACK);
        Cell dest = board.getCell("e4");
        board.move(src, dest);
    }
}
