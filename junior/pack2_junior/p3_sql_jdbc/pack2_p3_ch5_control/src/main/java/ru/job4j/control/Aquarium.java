package ru.job4j.control;

import java.util.concurrent.locks.ReentrantLock;
import java.util.Iterator;
import java.util.List;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс Aquarium реализует сущность Аквариум.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-09
 */
@ThreadSafe
class Aquarium extends Thread {
    /**
     * Объект блокировки (монитор).
     */
    private final ReentrantLock lock;
    /**
     * Коллекция особей.
     */
    private final FishList population;
    /**
     * Счётчик лет.
     */
    private int yearCounter;
    /**
     * Конструктор.
     * @param capacity максимальное количество рыб в аквариуме.
     */
    Aquarium(int capacity) {
        this.lock = new ReentrantLock();
        this.population = new FishList(capacity);
        this.yearCounter = 0;
    }
    /**
     * Добавляет рыб в аквариум.
     * @param fishes список рыб.
     * @return список добавленных элементов. Иначе false.
     */
    @GuardedBy("lock")
    public List<Fish> addAll(List<Fish> fishes) {
        List<Fish> result = null;
        if (this.lock.tryLock()) {
            try {
                result = this.population.addAll(fishes);
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Получает список популяции.
     * @return список популяции.
     */
    public FishList getFishList() {
        return this.population;
    }
    /**
     * Получает размер популяции.
     * @return размер популяции.
     */
    public int getPopulationSize() {
        int result = -1;
        do {
            result = this.population.size();
        } while (result == -1);
        return result;
    }
    /**
     * Запускает жизнь в аквариуме.
     * @param fishes список с рыбами.
     */
    public void live(List<Fish> fishes) {
        Iterator<Fish> iter = fishes.iterator();
        while (iter.hasNext()) {
            iter.next().start();
        }
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            this.live(this.population.getList());
            while (true) {
                System.out.format("%nYear is %d%n----------%n", this.yearCounter++);
                Thread.currentThread().sleep(1000);
                System.out.format("----------%nPopulation: %d%n", this.getPopulationSize(), this.yearCounter++);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}