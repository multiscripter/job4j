package ru.job4j.control;

/**
 * Класс Producer реализует сущность Производитель.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-31
 */
class Producer extends Thread {
    /**
     * Объект буфера.
     */
    private final IBoundedBuffer buffer;
    /**
     * Счётчик объектов.
     */
    private static int counter = 0;
    /**
     * Данные.
     */
    private int data = 0;
    /**
     * Идентификатор.
     */
    private int id;
    /**
     * Количество запросов к очереди.
     */
    private int reqs;
    /**
     * Конструктор.
     * @param buffer объект буфера.
     * @param reqs количество запросов к очереди.
     */
    Producer(IBoundedBuffer buffer, int reqs) {
        this.buffer = buffer;
        this.id = counter++;
        this.reqs = reqs;
        this.setName(String.format("Producer-%d", this.id));
    }
    /**
     * Производит данные.
     * @return произведённые данные.
     */
    private Data produce() {
        return new Data(this.id, ++this.data);
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            while (this.reqs-- > 0) {
                this.buffer.add(this.produce());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }
}