package ru.job4j.list;
/**
 * ISimpleCollection объявляет интерфэйс Коллекция.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-25
 */
public interface ISimpleCollection<E> extends Iterable<E> {
    /**
     * Добавляет элемент в коллекцию.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в коллекцию.
     */
    boolean add(E e);
}