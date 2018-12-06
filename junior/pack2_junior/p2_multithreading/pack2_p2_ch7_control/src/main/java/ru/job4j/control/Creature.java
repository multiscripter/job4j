package ru.job4j.control;

import java.util.ArrayList;
/**
 * Класс Creature реализует сущность Существо.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-09-06
 * @since 2017-08-25
 */
abstract class Creature implements ICreature, Runnable {
    /**
     * Игровое поле.
     */
    private final Board board;
    /**
     * Объект координат позиции.
     */
    private final Position pos;
    /**
     * Массив движений существа.
     */
    private final ArrayList<Movement> moves;
    /**
     * Коснтруктор.
     * @param board игровое поле.
     * @param pos объект координат позиции.
     */
    Creature(final Board board, final Position pos) {
        this.board = board;
        this.pos = pos;
        this.moves = new ArrayList<>();
        this.moves.add(new MoveUp(this.board));
        this.moves.add(new MoveRight(this.board));
        this.moves.add(new MoveDown(this.board));
        this.moves.add(new MoveLeft(this.board));
    }
    /**
     * Возвращает массив движений существа.
     * @return массив движений существа.
     */
    public ArrayList<Movement> getMoves() {
        return this.moves;
    }
    /**
     * Возвращает игровое поле.
     * @return игровое поле.
     */
    public Board getBoard() {
        return this.board;
    }
    /**
     * Возвращает позицию.
     * @return объект координат позициии на поле.
     */
    public Position getPosition() {
        return this.pos;
    }
    /**
     * Убивает существо.
     */
    public void kill() {
        Thread.currentThread().interrupt();
    }
    /**
     * Возвращает позицию.
     * @param dir направление движения.
     * @return true если передвижение выполнено успешно. Иначе false.
     */
    public boolean move(final Direction dir) {
        long end = System.currentTimeMillis() + 500;
        boolean result;
        Position srcPos = new Position(this.pos.getX(), this.pos.getY());
        Position destPos = new Position(-1, -1);
        try {
            destPos = this.moves.get(dir.ordinal()).getPosition(this.pos);
            do {
                result = this.board.blockPosition(destPos);
                if (result) {
                    this.pos.set(destPos);
                    break;
                }
            } while (System.currentTimeMillis() < end && !result);
            if (result) {
                do {
                    result = this.board.releasePosition(srcPos);
                } while (!result);
            }
        } catch (NonexistentPositionException ex) {
            result = false;
        }
        return result;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public abstract void run();
}
/**
 * Класс Movement реализует сущность Движение.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-08-25
 */
abstract class Movement {
    /**
     * Возвращает объект координат целевой клетки поля по направлению движения.
     * @param pos объект координат позиции.
     * @return объект координат целевой клетки поля по направлению движения.
     */
    public abstract Position getPosition(final Position pos);
}
/**
 * Класс MoveUp реализует сущность Движение вверх.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-08-25
 */
class MoveUp extends Movement {
    /**
     * Игровое поле.
     */
    private final Board board;
    /**
     * Конструктор.
     * @param board игровое поле.
     */
    MoveUp(final Board board) {
        this.board = board;
    }
    /**
     * Возвращает объект координат целевой клетки поля по направлению движения.
     * @param pos объект координат позиции.
     * @return объект координат целевой клетки поля по направлению движения.
     */
    public Position getPosition(final Position pos) {
        Position newPos = new Position(pos.getX(), pos.getY() - 1);
        this.board.getLock(newPos);
        return newPos;
    }
}
/**
 * Класс MoveRight реализует сущность Движение вправо.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-08-25
 */
class MoveRight extends Movement {
    /**
     * Игровое поле.
     */
    private final Board board;
    /**
     * Конструктор.
     * @param board игровое поле.
     */
    MoveRight(final Board board) {
        this.board = board;
    }
    /**
     * Возвращает объект координат целевой клетки поля по направлению движения.
     * @param pos объект координат позиции.
     * @return объект координат целевой клетки поля по направлению движения.
     */
    public Position getPosition(final Position pos) {
        Position newPos = new Position(pos.getX() + 1, pos.getY());
        this.board.getLock(newPos);
        return newPos;
    }
}
/**
 * Класс MoveDown реализует сущность Движение вниз.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-08-25
 */
class MoveDown extends Movement {
    /**
     * Игровое поле.
     */
    private final Board board;
    /**
     * Конструктор.
     * @param board игровое поле.
     */
    MoveDown(final Board board) {
        this.board = board;
    }
    /**
     * Возвращает объект координат целевой клетки поля по направлению движения.
     * @param pos объект координат позиции.
     * @return объект координат целевой клетки поля по направлению движения.
     */
    public Position getPosition(final Position pos) {
        Position newPos = new Position(pos.getX(), pos.getY() + 1);
        this.board.getLock(newPos);
        return newPos;
    }
}
/**
 * Класс MoveLeft реализует сущность Движение влево.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-08-25
 */
class MoveLeft extends Movement {
    /**
     * Игровое поле.
     */
    private final Board board;
    /**
     * Конструктор.
     * @param board игровое поле.
     */
    MoveLeft(final Board board) {
        this.board = board;
    }
    /**
     * Возвращает объект координат целевой клетки поля по направлению движения.
     * @param pos объект координат позиции.
     * @return объект координат целевой клетки поля по направлению движения.
     */
    public Position getPosition(final Position pos) {
        Position newPos = new Position(pos.getX() - 1, pos.getY());
        this.board.getLock(newPos);
        return newPos;
    }
}
