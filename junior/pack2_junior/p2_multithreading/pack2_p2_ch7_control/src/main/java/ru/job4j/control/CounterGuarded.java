package ru.job4j.control;

import net.jcip.annotations.ThreadSafe;
/**
 * Класс CounterGuarded иллюстрирует использование Охранных блоков (Guarded Blocks).
 * Охранный блок - это цикл while, в условии которого проверяется некоторое состояние оьъекта.
 * В зависимости от состояния объекта поток ожидает или продолжает выполнение.
 * Охранные блоки нужны в случаях, когда один поток, вызвав синхронизированный метод другого потока,
 * захватил блокировку, но объект, чей монитор захвачем, находится не в том состоянии, который нужен
 * захватившему потоку.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-30
 * @see http://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
 */
@ThreadSafe
class CounterGuarded implements ICounter {
    /**
     * Некоторое числовое поле.
     */
    private int val;
    /**
     * Доступность объекта.
     */
    private boolean isBlocked;
    /**
     * Конструктор без параметров.
     */
    CounterGuarded() {
        this.val = 0;
        this.isBlocked = false;
    }
    /**
     * Конструктор.
     * @param val число.
     */
    CounterGuarded(int val) {
        this.val = val;
        this.isBlocked = false;
    }
    /**
     * Декрементирует счётчик.
     * @return декрементированное значение счётчика.
     */
    public synchronized int decrement() {
        while (this.isBlocked) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        this.isBlocked = true;
        this.val--;
        this.isBlocked = false;
        this.notifyAll();
        return this.val;
    }
    /**
     * Инкрементирует счётчик.
     * @return инкрементированное значение счётчика.
     */
    public synchronized int increment() {
        while (this.isBlocked) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        this.isBlocked = true;
        this.val++;
        this.isBlocked = false;
        this.notifyAll();
        return this.val;
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
    public String toString() {
        return String.format("{val: %d}", this.val);
    }
}