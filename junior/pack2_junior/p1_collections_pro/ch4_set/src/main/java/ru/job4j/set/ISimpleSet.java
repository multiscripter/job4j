package ru.job4j.set;
/**
 * ISimpleSet объявляет интерфэйс Множество.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-25
 */
public interface ISimpleSet<E> extends ISimpleCollection<E> {
    /**
     * Добавляет элемент в множество.
     * @param e добавляемый элемент.
     * @return true если множество не содержит добавляемый элемент, иначе false.
     */
    boolean add(E e);
}