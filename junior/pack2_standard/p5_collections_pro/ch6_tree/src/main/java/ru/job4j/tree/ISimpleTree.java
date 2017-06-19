package ru.job4j.tree;
/**
 * ISimpleTree объявляет интерфэйс Дерево.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-19
 */
public interface ISimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавяет элемент child в элемент parent.
     * Parent может иметь список child.
     * @param parent родительский элемент.
     * @param child дочерний элемент.
     * @return true если child добавлен в parent. Иначе false.
     */
    boolean add(E parent, E child);
}
