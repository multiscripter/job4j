package ru.job4j.control;

import net.jcip.annotations.ThreadSafe;
/**
 * Класс CounterSyncMethods реализует сущность Счётчик.
 * Для синхронизации используются синхронизированные методы.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-29
 */
@ThreadSafe
class CounterSyncMethods implements ICounter {
    /**
     * Некоторое числовое поле.
     */
    private int val;
    /**
     * Конструктор без параметров.
     */
    CounterSyncMethods() {
        this.val = 0;
    }
    /**
     * Конструктор.
     * @param val число.
     */
    CounterSyncMethods(int val) {
        this.val = val;
    }
    /**
     * Декрементирует счётчик.
     * @return декрементированное значение счётчика.
     */
    public synchronized int decrement() {
        return --this.val;
    }
    /**
     * Инкрементирует счётчик.
     * @return инкрементированное значение счётчика.
     */
    public synchronized int increment() {
        return ++this.val;
    }
    /**
     * Возвращает значение счётчика.
     * @return значение счётчика.
     */
    public synchronized int getVal() {
        return this.val;
    }
    /**
     * Устанавливает значение счётчика.
     * @param val значение счётчика.
     */
    public synchronized void setVal(int val) {
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