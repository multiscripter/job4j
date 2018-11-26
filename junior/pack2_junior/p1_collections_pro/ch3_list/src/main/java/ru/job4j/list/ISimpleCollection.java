package ru.job4j.list;

import java.util.Iterator;
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
    /**
     * Возвращает итератор коллекции.
     * @return итератор коллекции.
     */
    Iterator<E> iterator();
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    int size();
}
