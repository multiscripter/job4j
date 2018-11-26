package ru.job4j.list;

/**
 * Класс Node реализует сущность Узел списка.
 *
 * @param <T> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-31
 */
class Node<T> {
    /**
     * Значение узла.
     */
    private T value;
    /**
     * Ссылка на следующий узел списка.
     */
    private Node<T> next;
    /**
     * Конструктор.
     * @param value значение узла.
     */
    Node(T value) {
        this.value = value;
    }
    /**
     * Конструктор.
     * @param value значение узла.
     * @param next cсылка на следующий узел списка.
     */
    Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }
    /**
     * Получает cсылку на следующий узел списка.
     * @return cсылка на следующий узел списка.
     */
    public Node<T> getNext() {
        return this.next;
    }
    /**
     * Получает значение узла.
     * @return значение узла.
     */
    public T getValue() {
        return this.value;
    }
    /**
     * Устанавливает cсылку на следующий узел списка.
     * @param next cсылка на следующий узел списка.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
    /**
     * Устанавливает значение узла.
     * @param value значение узла.
     */
    public void setNext(T value) {
        this.value = value;
    }
}
