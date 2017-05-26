package ru.job4j.list;
/**
 * ISimpleList объявляет интерфэйс Список.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-25
 */
public interface ISimpleList<E> extends ISimpleCollection<E> {
    /**
     * Добавляет элемент в список.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в конец списка, иначе false.
     */
    boolean add(E e);
    /**
     * Добавляет элемент в список по индексу.
     * @param index индекс в списке.
     * @param e добавляемый элемент.
     */
    void add(int index, E e);
    /**
     * Получает элемент из списка по индексу.
     * @param index индекс элемента.
     * @return элемент.
     */
    E get(int index);
    /**
     * Удаляет элемент из списка по индексу и сдвигает элементы вычитанием 1 из их индексов.
     * @param index индекс элемента.
     * @return удалённый элемент.
     */
    E remove(int index);
    /**
     * Заменяет элемент в список по индексу.
     * @param index индекс заменяемого элемента в списке.
     * @param e заменающий элемент.
     * @return удалённый элемент.
     */
    E set(int index, E e);
}