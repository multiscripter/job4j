package ru.job4j.control;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Класс Monster реализует сущность Чудовище.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-09-06
 * @since 2017-08-25
 */
class Monster extends Creature {
    /**
     * Счётчик объектов.
     */
    private static int counter = 1;
    /**
     * Имя сущности.
     */
    private final String name;
    /**
     * Короткое имя сущности.
     */
    private final String sname;
    /**
     * Коснтруктор.
     * @param board игровое поле.
     * @param pos объект координат позиции.
     */
    Monster(final Board board, final Position pos) {
        super(board, pos);
        this.name = "Monster-" + counter;
        this.sname = Integer.toString(counter++);
    }
    /**
     * Возвращает направление движения, сгенерированное случайным образом.
     * @return направление движения.
     */
    private Direction getDirection() {
        ArrayList<Direction> dirs = new ArrayList<>();
        for (Direction item : Direction.values()) {
            try {
                this.getMoves().get(item.ordinal()).getPosition(this.getPosition());
                dirs.add(item);
            } catch (NonexistentPositionException ex) {
                //Двигаться в этом направлении нельзя.
            }
        }
        return dirs.get(ThreadLocalRandom.current().nextInt(dirs.size()));
    }
    /**
     * Возвращает имя сущности.
     * @return имя сущности.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Возвращает короткое имя сущности.
     * @return короткое имя сущности.
     */
    public String getSname() {
        return this.sname;
    }
    /**
     * Переопределёный метод.
     * Движение чудовищ в случайном направлении.
     */
    @Override
    public void run() {
        this.getBoard().blockPosition(this.getPosition());
        long start;
        Direction dir = this.getDirection();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                start = System.currentTimeMillis();
                if (!this.move(dir)) {
                    dir = this.getDirection();
                }
                Thread.currentThread().sleep(1000 - (System.currentTimeMillis() - start));
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
