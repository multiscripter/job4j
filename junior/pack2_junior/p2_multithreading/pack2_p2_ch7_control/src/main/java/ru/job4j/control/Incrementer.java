package ru.job4j.control;

/**
 * Класс Incrementer реализует сущность Инкрементатор счётчика.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-29
 */
class Incrementer extends Thread {
    /**
     * Объект счётчика.
     */
    private final ICounter counter;
    /**
     * Количество итераций.
     */
    private final int iterations;
    /**
     * Конструктор.
     * @param counter объект счётчика.
     * @param iterations количество итераций.
     */
    Incrementer(ICounter counter, int iterations) {
        this.counter = counter;
        this.iterations = iterations;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        for (int a = 0; a < this.iterations; a++) {
            this.counter.increment();
        }
    }
}