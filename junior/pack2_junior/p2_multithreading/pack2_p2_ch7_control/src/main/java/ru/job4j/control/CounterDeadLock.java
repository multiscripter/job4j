package ru.job4j.control;

import net.jcip.annotations.ThreadSafe;
/**
 * Класс CounterDeadLock иллюстрирует проблему deadlock.
 * При использовании synchronized-метода монитором является объект по ссылке this. А это значит,
 * что при вызове одного synchronized-метода блокировка ставится на все synchronized-методы
 * в объекте. Если убрать synchronized из метода decrease, то deadlock не возникнет, но программа
 * вернёт некорректные значения из-за условия гонок при вызове метода decrement().
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-30
 * @see http://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html
 */
@ThreadSafe
class CounterDeadLock implements ICounter {
    /**
     * Некоторое числовое поле.
     */
    private int val;
    /**
     * Конструктор без параметров.
     */
    CounterDeadLock() {
        this.val = 0;
    }
    /**
     * Конструктор.
     * @param val число.
     */
    CounterDeadLock(int val) {
        this.val = val;
    }
    /**
     * Снижает.
     * @param counter объект.
     */
    public synchronized void decrease(CounterDeadLock counter) {
        counter.decrement();
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
     * @param counter объект.
     */
    public synchronized void increase(CounterDeadLock counter) {
        counter.increment();
        counter.decrease(this);
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