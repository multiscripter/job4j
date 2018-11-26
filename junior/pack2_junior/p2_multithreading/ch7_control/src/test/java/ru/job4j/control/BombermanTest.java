package ru.job4j.control;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс BombermanTest тестирует работу класса Bomberman.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-11
 * @since 2017-08-25
 */
public class BombermanTest {
    /**
     * Игровове поле.
     */
    private Board board;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.board = new Board(11, 11);
    }
    /**
     * Игра в демо-режиме.
     */
    @Ignore@Test
    public void checkAutoPlay() {
        this.board.generate(1, 5, 10);
        this.board.start();
        try {
            while (true) {
                Thread.currentThread().sleep(500);
                System.out.println(this.board);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует движение Бомбермэна. Последовательность перемещения: RIGHT, DOWN, LEFT, UP.
     */
    @Test
    public void checkMove1() {
        boolean[] expected = new boolean[] {true, true, true, true};
        boolean[] result = new boolean[4];
        try {
            Thread manT = new Thread(new Runnable() {
                @Override
                public void run() {
                    Position pos = new Position(0, 0);
                    try {
                        while (!board.blockPosition(pos)) {
                            this.wait(1);
                        }
                        Bomberman b = new Bomberman(board, pos);
                        Direction[] seq = new Direction[] {Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP};
                        int a = 0;
                        for (Direction dir : seq) {
                            result[a++] = b.move(dir);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            manT.start();
            manT.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует движение Бомбермэна. Последовательность перемещения: UP, RIGHT, DOWN, LEFT.
     * Вверх движение невозможно: край поля.
     */
    @Test
    public void checkMove2() {
        boolean[] expected = new boolean[] {false, true, true, true};
        boolean[] result = new boolean[4];
        try {
            Thread manT = new Thread(new Runnable() {
                /**
                 * Переопределёный метод.
                 */
                @Override
                public void run() {
                    Position pos = new Position(0, 0);
                    try {
                        while (!board.blockPosition(pos)) {
                            this.wait(1);
                        }
                        Bomberman b = new Bomberman(board, pos);
                        Direction[] seq = new Direction[] {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
                        int a = 0;
                        for (Direction dir : seq) {
                            result[a++] = b.move(dir);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            manT.start();
            manT.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует движение Бомбермэна. Последовательность перемещения: RIGHT, DOWN, LEFT, UP.
     * При движении RIGHT клетка занята другим Бомбермэном.
     */
    @Test
    public void checkMoveSecondCellOccupiedByAnotherBomberman() {
        boolean[] expected = new boolean[] {true, false, true, true};
        boolean[] result = new boolean[4];
        try {
            Thread manT = new Thread(new Runnable() {
                /**
                 * Переопределёный метод.
                 */
                @Override
                public void run() {
                    Position pos = new Position(1, 1);
                    try {
                        while (!board.blockPosition(pos)) {
                            this.wait(1);
                        }
                        Bomberman b1 = new Bomberman(board, pos);
                        Direction[] seq = new Direction[] {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
                        int a = 0;
                        for (Direction dir : seq) {
                            long start = System.currentTimeMillis();
                            result[a++] = b1.move(dir);
                            Thread.currentThread().sleep(1000 - (System.currentTimeMillis() - start));
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            Thread manT2 = new Thread(new Runnable() {
                /**
                 * Переопределёный метод.
                 */
                @Override
                public void run() {
                    Position pos = new Position(2, 0);
                    try {
                        while (!board.blockPosition(pos)) {
                            this.wait(1);
                        }
                        Bomberman b2 = new Bomberman(board, pos);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            manT2.start();
            manT.start();
            manT2.join();
            manT.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует исключение NonexistentPositionException.
     */
    @Test(expected = NonexistentPositionException.class)
    public void testNonexistentPositionException() {
        try {
            while (this.board.getLock(new Position(0, -1)) == null) {
                this.wait();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
