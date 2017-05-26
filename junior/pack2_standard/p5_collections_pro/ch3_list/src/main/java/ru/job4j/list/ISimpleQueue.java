package ru.job4j.list;
/**
 * ISimpleQueue объявляет интерфэйс Очередь.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-25
 */
public interface ISimpleQueue<E> extends ISimpleCollection<E> {
    /**
     * Добавляет элемент в очередь если есть место.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в конец очереди, иначе бросает IllegalStateException.
     */
    boolean add(E e);
    /**
     * Получает головной элемент из очереди.
     * @return элемент или бросает NoSuchElementException если очередь пуста.
     */
    E element();
    /**
     * Добавляет элемент в очередь если есть место.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в конец очереди, иначе false.
     */
    boolean offer(E e);
    /**
     * Получает головной элемент из очереди.
     * @return элемент или null если очередь пуста.
     */
    E peek();
    /**
     * Получает и удаляет головной элемент из очереди.
     * @return удалённый элемент или null если очередь пуста.
     */
    E poll();
    /**
     * Получает и удаляет головной элемент из очереди.
     * @return элемент или null если очередь пуста.
     */
    E remove();
}