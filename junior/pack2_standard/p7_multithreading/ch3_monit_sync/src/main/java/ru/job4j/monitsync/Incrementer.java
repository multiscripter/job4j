package ru.job4j.monitsync;

/**
 * Класс Incrementer реализует сущность Инкрементатор счётчика.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-25
 */
class Incrementer extends Thread {
    /**
     * Объект счётчика.
     */
    private final Counter counter;
    /**
     * Количество итераций.
     */
    private final int iterations;
    /**
     * Конструктор.
     * @param counter объект счётчика.
     * @param iterations количество итераций.
     */
    Incrementer(Counter counter, int iterations) {
        this.counter = counter;
        this.iterations = iterations;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            for (int a = 0; a < this.iterations; a++) {
                this.counter.increment();
                this.sleep(1);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}