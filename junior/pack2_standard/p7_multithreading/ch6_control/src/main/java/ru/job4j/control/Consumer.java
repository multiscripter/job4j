package ru.job4j.control;

/**
 * Класс Consumer реализует сущность "Потребитель".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-08-31
 */
class Consumer extends Thread {
    /**
     * Объект буфера.
     */
    private final IBoundedBuffer buffer;
    /**
     * Счётчик объектов.
     */
    private static int counter = 0;
    /**
     * Идентификатор объекта.
     */
    private int id;
    /**
     * Количество запросов к очереди.
     */
    private int reqs;
    /**
     * Объект сборщика.
     */
    private Collector collector;
    /**
     * Конструктор.
     * @param buffer объект буфера.
     * @param reqs количество запросов к очереди.
     * @param collector объект сборщика.
     */
    Consumer(IBoundedBuffer buffer, int reqs, Collector collector) {
        this.buffer = buffer;
        this.id = counter++;
        this.reqs = reqs;
        this.collector = collector;
        this.setName(String.format("Consumer-%d", this.id));
    }
    /**
     * Потребляет данные.
     * @param data данные Производителя.
     */
    private void consume(Data data) {
        if (data != null) {
            this.collector.setValue(data.getProducerId(), data.getData());
        }
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            while (this.reqs-- > 0) {
                this.consume((Data) this.buffer.remove());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }
}