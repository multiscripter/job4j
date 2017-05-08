package ru.job4j.chess;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс KnightTest тестирует сущность Конь (Knight).
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-08
 */
public class KnightTest {
    /**
     * Тестирует move(). Правильные ходы.
     */
    @Test
    public void checkMoveLegal() {
        String[] legal = new String[] {"c6", "e6", "f5", "f3", "e2", "c2", "b3", "b5"};
        Board board = new Board();
        Cell cell = board.getCell("d4");
        Knight knight = new Knight(cell, Color.BLACK);
        for (String item : legal) {
            Cell expected = board.getCell(item);
            try {
                board.move(cell, expected);
            } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
                ex.printStackTrace();
            }
            assertEquals(expected, knight.getCell());
            try {
                board.move(expected, cell);
            } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * Тестирует move(). Неправильные ходы.
     */
    @Test
    public void checkMoveIllegal() {
        String[] illegal = new String[] {"d5", "d6", "d7", "e7", "f7", "f6", "g6", "g5", "g4", "f4", "e4"};
        Board board = new Board();
        Cell src = board.getCell("d4");
        Knight knight = new Knight(src, Color.WHITE);
        for (String item : illegal) {
            Cell dest = board.getCell(item);
            try {
                board.move(src, dest);
            } catch (ImposibleMoveException | OccupiedWayException | FigureNotFoundException ex) {
                String expected = String.format("Imposible movement. src: %s, dest: %s", src.getPosition(), dest.getPosition());
                assertEquals(expected, ex.getMessage());
            }
        }
    }
}
