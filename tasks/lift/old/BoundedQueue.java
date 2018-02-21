package tasks.lift;

import java.util.LinkedList;
/**
 * Класс BoundedQueue реализует сущность Ограниченая очередь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-18
 * @since 2018-01-18
 */
class BoundedQueue {
    /**
     * Размер очереди.
     */
    private final int bound;
    /**
     * Очередь элементов.
     */
    private final LinkedList<Integer> queue;
    /**
     * Конструктор.
     * @param bound размер очереди.
     * @throws IllegalArgumentException неверный аргумент.
     */
    BoundedQueue(final int bound) throws IllegalArgumentException {
        if (bound < 1 || bound > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Incorrect parameter");
        }
        this.bound = bound;
        this.queue = new LinkedList<>();
    }
    /**
     * Добавляет элемент в очередь.
     * @param floor номер этажа.
     * @return true если элемент добавлен в очередь. Иначе false.
     * @throws BoundedQueueIsFullException очередь заполнена.
     */
    public boolean add(int floor) throws BoundedQueueIsFullException {
        if (this.queue.size() == this.bound) {
            throw new BoundedQueueIsFullException();
        }
        return this.queue.add(floor);
    }
}