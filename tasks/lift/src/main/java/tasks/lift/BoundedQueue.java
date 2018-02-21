package tasks.lift;

import java.util.Iterator;
import java.util.LinkedList;
/**
 * Класс BoundedQueue реализует сущность Ограниченая очередь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-21
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
     * Добавляет элемент в конец очередь.
     * @param floor номер этажа.
     * @return true если элемент добавлен в очередь. Иначе false.
     * @throws BoundedQueueIsFullException очередь заполнена.
     */
    public synchronized boolean add(int floor) throws BoundedQueueIsFullException {
        if (this.queue.size() == this.bound) {
            throw new BoundedQueueIsFullException();
        }
        return this.queue.add(floor);
    }
    /**
     * Добавляет элемент в начало очереди.
     * @param floor номер этажа.
     * @return true если элемент добавлен в очередь.
     * @throws BoundedQueueIsFullException очередь заполнена.
     */
    public synchronized boolean addFirst(int floor) throws BoundedQueueIsFullException {
        if (this.queue.size() == this.bound) {
            throw new BoundedQueueIsFullException();
        }
        this.queue.addFirst(floor);
        return true;
    }
    /**
     * Проверяет наличие элемента в очереди.
     * @param o проверяемый элемент.
     * @return true если элемент содержится в очереди. Иначе false.
     */
    public boolean contains(Object o) {
        return this.queue.contains(o);
    }
    /**
     * Проверяет очередь на пустоту.
     * @return true если очередь пуста. Иначе false.
     */
    public boolean isEmpty() {
        return this.queue.size() == 0;
    }
    /**
     * Получает головной элемент очереди.
     * @return головной элемент очереди.
     */
    public Integer peek() {
        return this.queue.peek();
    }
    /**
     * Получает и удаляет головной элемент очереди.
     * @return головной элемент очереди.
     */
    public synchronized Integer remove() {
        return this.queue.remove();
    }
    /**
     * Возвращает размер очереди.
     * @return размер очереди.
     */
    public int size() {
        return this.queue.size();
    }
    /**
     * Возвращает строковое представление очереди.
     * @return строковое представление очереди.
     */
    @Override
    public synchronized String toString() {
        String result = "{";
        if (this.size() > 0) {
            Iterator<Integer> iter = this.queue.iterator();
            while (iter.hasNext()) {
                result += iter.next().toString();
                result += ", ";
            }
            result = result.substring(0, result.length() - 2);
        }
        result += "}";
        return result;
    }
}