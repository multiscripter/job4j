package ru.job4j.control;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс CounterSyncBlocks реализует сущность Счётчик.
 * Для синхронизации используются синхронизированные блоки внутри методов.
 * В качестве монитора используется отдельный объект.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-29
 */
@ThreadSafe
class CounterSyncBlocks implements ICounter {
    /**
     * Некоторое числовое поле.
     */
    private int val;
    /**
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор без параметров.
     */
    CounterSyncBlocks() {
        this.val = 0;
        this.lock = new Object();
    }
    /**
     * Конструктор.
     * @param val число.
     */
    CounterSyncBlocks(int val) {
        this.val = val;
    }
    /**
     * Декрементирует счётчик.
     * @return декрементированное значение счётчика.
     */
    @GuardedBy("lock")
    public int decrement() {
        synchronized (this.lock) {
            return --this.val;
        }
    }
    /**
     * Инкрементирует счётчик.
     * @return инкрементированное значение счётчика.
     */
    @GuardedBy("lock")
    public int increment() {
        synchronized (this.lock) {
            return ++this.val;
        }
    }
    /**
     * Возвращает значение счётчика.
     * @return значение счётчика.
     */
    @GuardedBy("lock")
    public int getVal() {
        synchronized (this.lock) {
            return this.val;
        }
    }
    /**
     * Устанавливает значение счётчика.
     * @param val значение счётчика.
     */
    @GuardedBy("lock")
    public void setVal(int val) {
        synchronized (this.lock) {
            this.val = val;
        }
    }
    /**
     * Возвращает строковое представление объекта.
     * @return строковое представление объекта.
     */
    @GuardedBy("lock")
    public String toString() {
        synchronized (this.lock) {
            return String.format("{val: %d}", this.val);
        }
    }
}