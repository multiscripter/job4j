package ru.job4j.control;

/**
 * Класс Data реализует сущность "Данные".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-02
 */
class Data {
    /**
     * Идентификатор производителя.
     */
    private int producerId;
    /**
     * Данные.
     */
    private int data;
    /**
     * Конструктор.
     * @param producerId идентификатор производителя.
     * @param data данные.
     */
    Data(int producerId, int data) {
        this.producerId = producerId;
        this.data = data;
    }
    /**
     * Получает идентификатор производителя.
     * @return идентификатор производителя.
     */
    public int getProducerId() {
        return this.producerId;
    }
    /**
     * Получает данные производителя.
     * @return данные.
     */
    public int getData() {
        return this.data;
    }
    /**
     * Возвращает строковое представление объекта данных.
     * @return строковое представление объекта данных.
     */
    @Override
    public String toString() {
        return String.format("Producer-%d. Data: %d", this.producerId, this.data);
    }
}