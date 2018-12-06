package ru.job4j.control;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.Random;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс Board реализует сущность Игровое поле.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-09-06
 * @since 2017-08-25
 */
@ThreadSafe
class Board {
    /**
     * Массив клеток игрового поля.
     */
    private final ReentrantLock[][] board;
    /**
     * Сущности.
     */
    private final HashMap<String, IEntity> entities = new HashMap<>();
    /**
     * Существа.
     */
    private final HashMap<String, Runnable> creatures = new HashMap<>();
    /**
     * Высота игрового поля.
     */
    private final int height;
    /**
     * Рандомизатор.
     */
    private final Random random = new Random();
    /**
     * Ширина игрового поля.
     */
    private final int width;
    /**
     * Конструктор.
     * @param width количество клеток в поле по ширине.
     * @param height количество клеток в поле по высоте.
     * @throws IllegalArgumentException если хотя бы одна из координат < 2 или > Integer.MAX_VALUE.
     */
    Board(final int width, final int height) throws IllegalArgumentException {
        this.board = new ReentrantLock[width][height];
        if (width < 2 || height < 2 || width > Integer.MAX_VALUE || height > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.height = height;
        this.width = width;
        for (int a = 0; a < this.width; a++) {
            for (int b = 0; b < this.height; b++) {
                this.board[a][b] = new ReentrantLock();
            }
        }
    }
    /**
     * Генерирует игровое поле.
     * @param bombers количество бомберменов.
     * @param monsters количество монстров.
     * @param blocks количество блоков.
     */
    public void generate(final int bombers, final int monsters, final int blocks) {
        Position pos;
        IEntity tmp;
        for (int a = 0; a < this.width; a++) {
            for (int b = 0; b < this.height; b++) {
                if (a != 0 && a % 2 != 0 && b != 0 && b % 2 != 0) {
                    pos = new Position(a, b);
                    if (this.blockPosition(pos)) {
                        tmp = new Concrete(this, pos);
                        this.entities.put(tmp.getName(), tmp);
                    }
                }
            }
        }
        boolean result = false;
        ReentrantLock lock;
        ArrayList<Position> cpos = new ArrayList<>();
        for (int a = 0; a < bombers; a++) {
            do {
                pos = new Position(this.random.nextInt(this.width), this.random.nextInt(this.height));
                if (!this.getLock(pos).isLocked()) {
                    result = this.blockPosition(pos);
                }
            } while (!result);
            tmp = new Bomberman(this, pos);
            cpos.add(pos);
            this.entities.put(tmp.getName(), tmp);
            this.creatures.put(tmp.getName(), (Runnable) tmp);
            result = false;
        }
        for (int a = 0; a < monsters; a++) {
            do {
                pos = new Position(this.random.nextInt(this.width), this.random.nextInt(this.height));
                if (!this.getLock(pos).isLocked()) {
                    result = this.blockPosition(pos);
                }
            } while (!result);
            this.releasePosition(pos);
            tmp = new Monster(this, pos);
            cpos.add(pos);
            this.entities.put(tmp.getName(), tmp);
            this.creatures.put(tmp.getName(), (Runnable) tmp);
            result = false;
        }
        for (int a = 0; a < blocks; a++) {
            do {
                pos = new Position(this.random.nextInt(this.width), this.random.nextInt(this.height));
                if (!this.getLock(pos).isLocked()) {
                    result = this.blockPosition(pos);
                }
            } while (!result);
            tmp = new Brick(this, pos);
            this.entities.put(tmp.getName(), tmp);
            result = false;
        }
        for (Position item : cpos) {
            this.releasePosition(item);
        }
        System.out.println(this);
    }
    /**
     * Устанавливает (блокирует) позицию.
     * @param pos объект координат позиции.
     * @return true если позиция установелна. Иначе false.
     */
    public boolean blockPosition(final Position pos) {
        return this.getLock(pos).tryLock();
    }
    /**
     * Возвращает объект блокировки в указанной позиции.
     * @param pos объект координат позиции.
     * @return объект позициии на поле.
     * @throws NonexistentPositionException исключение "Несуществующая позиция".
     */
    public ReentrantLock getLock(final Position pos) throws NonexistentPositionException {
        if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() >= this.width || pos.getY() >= this.height) {
            throw new NonexistentPositionException(pos);
        }
        return this.board[pos.getX()][pos.getY()];
    }
    /**
     * Освобождает (разблокирует) позицию.
     * @param pos объект координат позиции.
     * @return true если позиция освобождена. Иначе false.
     */
    public boolean releasePosition(final Position pos) {
        ReentrantLock lock = this.getLock(pos);
        boolean result = false;
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
            result = true;
        }
        return result;
    }
    /**
     * Запускает возню на игровом поле.
     */
    public void start() {
        Thread[] ts = new Thread[this.creatures.size()];
        int a = 0;
        for (Runnable item : this.creatures.values()) {
            ts[a] = new Thread(item);
            ts[a++].start();
        }
    }
    /**
     * Возвращает строковое представление игрового поля.
     * @return строковое представление игрового поля.
     */
    @Override
    public String toString() {
        String[][] ents = new String[this.width][this.height];
        for (IEntity item : this.entities.values()) {
            ents[item.getPosition().getX()][item.getPosition().getY()] = item.getSname();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int a = 0; a < this.width; a++) {
            sb.append("-");
        }
        sb.append("+\n");
        String tmp;
        for (int a = 0; a < this.width; a++) {
            sb.append("|");
            for (int b = 0; b < this.height; b++) {
                tmp = ents[a][b];
                sb.append(tmp == null ? " " : tmp);
            }
            sb.append("|\n");
        }
        sb.append("+");
        for (int a = 0; a < this.width; a++) {
            sb.append("-");
        }
        sb.append("+\n");
        return sb.toString();
    }
}
