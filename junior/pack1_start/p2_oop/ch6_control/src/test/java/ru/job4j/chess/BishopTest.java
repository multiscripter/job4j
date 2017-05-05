package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс BishopTest тестирует сущность Слон (Bishop).
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-05
 */
public class BishopTest {
    /**
     * Тестирует пользовательское исключение OccupiedPositionException.
     */
    @Test(expected = OccupiedPositionException.class)
    public void testOccupiedPositionException() {
        Board board = new Board();
        Cell cell = board.getCell("c1");
        Bishop bishop1 = new Bishop(cell, Color.BLACK);
        Bishop bishop2 = new Bishop(cell, Color.WHITE);
    }
    /**
     * Тестирует move(). Слон c1 -> h6.
     */
    @Test
    public void checkMoveColPlusRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("c1");
        Bishop bishop = new Bishop(cell, Color.BLACK);
        Cell expected = board.getCell("h6");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, bishop.getCell());
    }
    /**
     * Тестирует move(). Слон c1 -> a3.
     */
    @Test
    public void checkMoveColMinusRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("c1");
        Bishop bishop = new Bishop(cell, Color.BLACK);
        Cell expected = board.getCell("a3");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, bishop.getCell());
    }
    /**
     * Тестирует move(). Слон h8 -> a1.
     */
    @Test
    public void checkMoveColMinusRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("h8");
        Bishop bishop = new Bishop(cell, Color.BLACK);
        Cell expected = board.getCell("a1");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, bishop.getCell());
    }
    /**
     * Тестирует move(). Слон a4 -> d1.
     */
    @Test
    public void checkMoveColPlusRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("a4");
        Bishop bishop = new Bishop(cell, Color.BLACK);
        Cell expected = board.getCell("d1");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, bishop.getCell());
    }
}