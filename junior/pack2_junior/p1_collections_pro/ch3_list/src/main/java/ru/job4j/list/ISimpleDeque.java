package ru.job4j.list;
/**
 * ISimpleDeque объявляет интерфэйс "Двухконцовая очередь".
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-27
 */
public interface ISimpleDeque<E> extends ISimpleQueue<E> {
    /**
     * Добавляет элемент в начало очереди.
     * @param e добавляемый элемент.
     */
    void addFirst(E e);
    /**
     * Добавляет элемент в конец очереди.
     * @param e добавляемый элемент.
     */
    void addLast(E e);
    /**
     * Получает первый элемент в очереди.
     * @return элемент.
     */
    E getFirst();
    /**
     * Получает последний элемент в очереди.
     * @return элемент.
     */
    E getLast();
    /**
     * Добавляет элемент в начало очереди.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в начало очереди, иначе false.
     */
    boolean offerFirst(E e);
    /**
     * Добавляет элемент в конец очереди.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в конец очереди, иначе false.
     */
    boolean offerLast(E e);
    /**
     * Получает первый элемент из очереди.
     * @return элемент или null если очередь пуста.
     */
    E peekFirst();
    /**
     * Получает последний элемент из очереди.
     * @return элемент или null если очередь пуста.
     */
    E peekLast();
    /**
     * Получает и удаляет первый элемент в очереди.
     * @return удалённый элемент или null если очередь пуста.
     */
    E pollFirst();
    /**
     * Получает и удаляет последний элемент в очереди.
     * @return удалённый элемент или null если очередь пуста.
     */
    E pollLast();
    /**
     * Добавляет элемент в начало очереди. Эквивалент addFirst(E e).
     * @param e добавляемый элемент.
     */
    void push(E e);
    /**
     * Получает и удаляет первый элемент в очереди. Эквивалент removeFirst().
     * @return элемент или null если очередь пуста.
     */
    E pop();
    /**
     * Получает и удаляет первый элемент в очереди.
     * @return элемент или null если очередь пуста.
     */
    E removeFirst();
    /**
     * Получает и удаляет последний элемент в очереди.
     * @return элемент или null если очередь пуста.
     */
    E removeLast();
}