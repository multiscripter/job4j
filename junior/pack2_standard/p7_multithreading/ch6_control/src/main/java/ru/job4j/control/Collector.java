package ru.job4j.control;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс Collector реализует сущность "Сборщик данных".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-02
 */
@ThreadSafe
class Collector {
    /**
     * Массив.
     */
    private int[] collector;
    /**
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор.
     * @param size размер массива.
     */
    Collector(int size) {
        this.collector = new int[size];
        this.lock = this;
    }
    /**
     * Получает массив.
     * @return массив.
     */
    public int[] getCollector() {
        return this.collector;
    }
    /**
     * Устанавливает данные по индексу.
     * @param index индекс элемента.
     * @param value данные.
     */
    @GuardedBy("lock")
    public void setValue(int index, int value) {
        synchronized (this.lock) {
            this.collector[index] += value;
        }
    }
}