package ru.job4j.monitsync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс Counter реализует сущность Счётчик.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-25
 */
@ThreadSafe
class Counter {
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
    Counter() {
        this.val = 0;
        this.lock = new Object();
    }
    /**
     * Конструктор.
     * @param val число.
     */
    Counter(int val) {
        this.val = val;
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
     * Возвращает число.
     * @return число.
     */
    public int getVal() {
        return this.val;
    }
    /**
     * Возвращает строковое представление объекта.
     * @return строковое представление объекта.
     */
    public String toString() {
        return String.format("{val: %d}", this.val);
    }
}