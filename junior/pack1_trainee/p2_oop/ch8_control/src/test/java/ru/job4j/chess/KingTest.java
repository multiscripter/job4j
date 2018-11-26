package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс KingTest тестирует сущность Король (King).
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-08
 */
public class KingTest {
    /**
     * Тестирует пользовательское исключение OccupiedPositionException.
     */
    @Test(expected = OccupiedPositionException.class)
    public void testOccupiedPositionException() {
        Board board = new Board();
        Cell cell = board.getCell("d1");
        King king1 = new King(cell, Color.BLACK);
        King king2 = new King(cell, Color.WHITE);
    }
    /**
     * Тестирует пользовательское исключение OccupiedWayException.
     */
    @Test(expected = OccupiedWayException.class)
    public void testOccupiedWayException() {
        Board board = new Board();
        Cell cell1 = board.getCell("d3");
        King king1 = new King(cell1, Color.BLACK);
        Cell cell2 = board.getCell("c2");
        King king2 = new King(cell2, Color.WHITE);
        board.move(cell1, cell2);
    }
    /**
     * Тестирует move(). d3 -> d4.
     */
    @Test
    public void checkMoveRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        King king = new King(cell, Color.BLACK);
        Cell expected = board.getCell("d4");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, king.getCell());
    }
    /**
     * Тестирует move(). d3 -> d5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveRowPlus2cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("d5");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> e5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlusRowPlus2cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("e5");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> e4.
     */
    @Test
    public void checkMoveColPlusRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        King king = new King(cell, Color.BLACK);
        Cell expected = board.getCell("e4");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, king.getCell());
    }
    /**
     * Тестирует move(). d3 -> e5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlus2CellsRowPlus2cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("e5");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> e3.
     */
    @Test
    public void checkMoveColPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        King king = new King(cell, Color.BLACK);
        Cell expected = board.getCell("e3");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, king.getCell());
    }
    /**
     * Тестирует move(). d3 -> f3.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlus2Cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("f3");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> c4.
     */
    @Test
    public void checkMoveColPlusRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        King king = new King(cell, Color.BLACK);
        Cell expected = board.getCell("c4");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, king.getCell());
    }
    /**
     * Тестирует move(). d3 -> b5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColPlus2CellsRowMinus2Cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("b5");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> d2.
     */
    @Test
    public void checkMoveRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        King king = new King(cell, Color.BLACK);
        Cell expected = board.getCell("d2");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, king.getCell());
    }
    /**
     * Тестирует move(). d3 -> d1.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveRowMinus2Cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("d1");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> c2.
     */
    @Test
    public void checkMoveColMinusRowMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        King king = new King(cell, Color.BLACK);
        Cell expected = board.getCell("c2");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, king.getCell());
    }
    /**
     * Тестирует move(). d3 -> b1.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinus2CellsRowMinus2Cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("b1");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> c3.
     */
    @Test
    public void checkMoveColMinus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        King king = new King(cell, Color.BLACK);
        Cell expected = board.getCell("c3");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, king.getCell());
    }
    /**
     * Тестирует move(). d3 -> b3.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinus2Cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("b3");
        board.move(src, dest);
    }
    /**
     * Тестирует move(). d3 -> c2.
     */
    @Test
    public void checkMoveColMinusRowPlus() {
        Board board = new Board();
        Cell cell = board.getCell("d3");
        King king = new King(cell, Color.BLACK);
        Cell expected = board.getCell("c2");
        try {
            board.move(cell, expected);
        } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
            ex.printStackTrace();
        }
        assertEquals(expected, king.getCell());
    }
    /**
     * Тестирует move(). d3 -> b5.
     */
    @Test(expected = ImposibleMoveException.class)
    public void checkMoveColMinus2CellsRowPlus2Cells() {
        Board board = new Board();
        Cell src = board.getCell("d3");
        King king = new King(src, Color.BLACK);
        Cell dest = board.getCell("b5");
        board.move(src, dest);
    }
}
