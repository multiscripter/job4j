package ru.job4j.control;

import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс CounterDeadLock иллюстрирует решение проблемы deadlock с помощью использования
 * объектов блокировок.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-30
 * @see http://docs.oracle.com/javase/tutorial/essential/concurrency/newlocks.html
 */
@ThreadSafe
class CounterLock implements ICounter {
    /**
     * Некоторое числовое поле.
     */
    private int val;
    /**
     * Объект блокировки (монитор).
     */
    private final ReentrantLock lock = new ReentrantLock();
    /**
     * Конструктор без параметров.
     */
    CounterLock() {
        this.val = 0;
    }
    /**
     * Конструктор.
     * @param val число.
     */
    CounterLock(int val) {
        this.val = val;
    }
    /**
     * Снижает.
     * @param otherCounter объект Счётчика.
     */
    private synchronized void decrease(CounterLock otherCounter) {
        otherCounter.decrement();
    }
    /**
     * Декрементирует счётчик.
     * @return декрементированное значение счётчика.
     */
    public int decrement() {
        return --this.val;
    }
    /**
     * Повышает.
     * @param otherCounter объект.
     * @return true если счётчик успешно изменён. Иначе false.
     */
    @GuardedBy("lock")
    public synchronized boolean increase(CounterLock otherCounter) {
        boolean selfLock = this.lock.tryLock();
        boolean otherLock = otherCounter.lock.tryLock();
        if (selfLock && otherLock) {
            otherCounter.increment();
            otherCounter.decrease(this);
        }
        if (selfLock) {
            this.lock.unlock();
        }
        if (otherLock) {
            otherCounter.lock.unlock();
        }
        return selfLock && otherLock;
    }
    /**
     * Инкрементирует счётчик.
     * @return инкрементированное значение счётчика.
     */
    public int increment() {
        return ++this.val;
    }
    /**
     * Возвращает значение счётчика.
     * @return значение счётчика.
     */
    public int getVal() {
        return this.val;
    }
    /**
     * Устанавливает значение счётчика.
     * @param val значение счётчика.
     */
    public void setVal(int val) {
        this.val = val;
    }
    /**
     * Возвращает строковое представление объекта.
     * @return строковое представление объекта.
     */
    public synchronized String toString() {
        return String.format("{val: %d}", this.val);
    }
}