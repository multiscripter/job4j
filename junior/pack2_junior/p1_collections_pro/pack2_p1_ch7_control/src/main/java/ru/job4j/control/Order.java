package ru.job4j.control;

import java.util.Objects;
/**
 * Класс Order реализует сущность Заказ.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-06-27
 */
class Order implements Comparable<Order> {
    /**
     * Операция (покупка|продажа).
     */
    private String operation;
    /**
     * Идентификатор заказа.
     */
    private final String orderId;
    /**
     * Цена книги.
     */
    private double price;
    /**
     * Количество книг в заказе.
     */
    private int volume;
    /**
     * Конструктор.
     * @param orderId идентификатор заказа.
     * @param operation операция (покупка|продажа).
     * @param price цена книги.
     * @param volume количество книг в заказе.
     */
    Order(String orderId, String operation, double price, int volume) {
        this.orderId = orderId;
        this.operation = operation;
        this.price = price;
        this.volume = volume;
    }
    /**
     * Сравнивает два объекта заказа.
     * @param obj объект заказа.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(Order obj) {
        return this.hashCode() - obj.hashCode();
    }
    /**
     * Сравнивает объекты заказов.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые. Иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Order order = (Order) obj;
        if (!this.operation.equals(order.getOperation()) || !this.orderId.equals(order.getOrderId()) || Double.compare(this.price, order.getPrice()) != 0 || this.volume != order.getVolume()) {
            return false;
        }
        return true;
    }
    /**
     * Получает операцию.
     * @return операция.
     */
    public String getOperation() {
        return this.operation;
    }
    /**
     * Получает идентификатор заказа.
     * @return идентификатор заказа.
     */
    public String getOrderId() {
        return this.orderId;
    }
    /**
     * Получает цену книги.
     * @return цена книги.
     */
    public double getPrice() {
        return this.price;
    }
    /**
     * Получает количество книг в заказе.
     * @return количество книг в заказе.
     */
    public int getVolume() {
        return this.volume;
    }
    /**
     * Возвращает хэш-код объекта.
     * @return хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.operation, this.orderId, this.price, this.volume);
    }
    /**
     * Устанавливает количество книг в заказе.
     * @param volume количество книг в заказе.
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }
    /**
     * Генерирует строковое представление объекта.
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("orderId{orderId: %s, operation: %s, price: %f, volume: %d}", this.orderId,  this.operation, this.price, this.volume);
    }
}

