package ru.job4j.control;

import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс CounterAtomic реализует сущность Счётчик.
 * Для синхронизации используется класс AtomicInteger.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-31
 * @see http://docs.oracle.com/javase/tutorial/essential/concurrency/atomicvars.html
 * @see http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-frame.html
 */
@ThreadSafe
class CounterAtomic implements ICounter {
    /**
     * Некоторое числовое поле.
     */
    private AtomicInteger val;
    /**
     * Конструктор без параметров.
     */
    CounterAtomic() {
        this.val = new AtomicInteger(0);
    }
    /**
     * Конструктор.
     * @param val число.
     */
    CounterAtomic(int val) {
        this.val = new AtomicInteger(val);
    }
    /**
     * Декрементирует счётчик.
     * @return декрементированное значение счётчика.
     */
    public int decrement() {
        return this.val.decrementAndGet();
    }
    /**
     * Инкрементирует счётчик.
     * @return инкрементированное значение счётчика.
     */
    public int increment() {
        return this.val.incrementAndGet();
    }
    /**
     * Возвращает значение счётчика.
     * @return значение счётчика.
     */
    public int getVal() {
        return this.val.get();
    }
    /**
     * Устанавливает значение счётчика.
     * @param val значение счётчика.
     */
    public void setVal(int val) {
        this.val.set(val);
    }
    /**
     * Возвращает строковое представление объекта.
     * @return строковое представление объекта.
     */
    public String toString() {
        return String.format("{val: %d}", this.val.get());
    }
}