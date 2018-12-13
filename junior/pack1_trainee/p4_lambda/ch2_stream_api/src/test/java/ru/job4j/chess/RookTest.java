package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс RookTest тестирует сущность Ладья (Rook).
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-05-08
 * @since 2017-05-08
 */
public class RookTest {
    /**
     * Тестирует move(). d3 -> d8.
     */
    @Test
    public void checkMoveRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        Rook rook = new Rook(cell, Color.BLACK);
        Cell expected = board.getCell("d8");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, rook.getCell());
    }
    /**
     * Тестирует move(). d3 -> h5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlusRowPlus() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        Rook rook = new Rook(src, Color.BLACK);
        Cell dest = board.getCell("h5");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> h3.
     */
    @Test
    public void checkMoveColPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        Rook rook = new Rook(cell, Color.BLACK);
        Cell expected = board.getCell("h3");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, rook.getCell());
    }
    /**
     * Тестирует move(). d3 -> f1.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlusRowMinus() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        Rook rook = new Rook(src, Color.BLACK);
        Cell dest = board.getCell("f1");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> h3.
     */
    @Test
    public void checkMoveRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        Rook rook = new Rook(cell, Color.BLACK);
        Cell expected = board.getCell("d2");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, rook.getCell());
    }
    /**
     * Тестирует move(). d3 -> c2.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinusRowMinus() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        Rook rook = new Rook(src, Color.BLACK);
        Cell dest = board.getCell("c2");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> a3.
     */
    @Test
    public void checkMoveColMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        Rook rook = new Rook(cell, Color.BLACK);
        Cell expected = board.getCell("a3");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, rook.getCell());
    }
    /**
     * Тестирует move(). d3 -> a8.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinusRowPlus() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        Rook rook = new Rook(src, Color.BLACK);
        Cell dest = board.getCell("a8");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> a9.
     */
    @Test(expected = IllegalPositionException.class)
    public void checkMoveColMinusRowPlus1() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        Rook rook = new Rook(src, Color.BLACK);
        Cell dest = board.getCell("a9");
        board.move(src, dest);
    }
}
