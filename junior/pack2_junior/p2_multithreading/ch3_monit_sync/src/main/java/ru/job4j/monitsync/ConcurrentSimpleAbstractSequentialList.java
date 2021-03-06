package ru.job4j.monitsync;

import java.util.Iterator;
import java.util.ListIterator;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс ConcurrentSimpleAbstractSequentialList реализует сущность Потокобезопасный последовательный список.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-26
 */
@ThreadSafe
abstract class ConcurrentSimpleAbstractSequentialList<E> extends SimpleAdstractList<E> {
    /**
     * Добавляет элемент в список по индексу.
     * @param index индекс в списке.
     * @param e добавляемый элемент.
     */
    public synchronized void add(int index, E e) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> iter = listIterator(index);
        iter.add(e);
    }
    /**
     * Возвращает объект итератора.
     * @return объект итератора.
     */
    public synchronized Iterator<E> iterator() {
        return listIterator(0);
    }
    /**
     * Получает элемент из списка по индексу.
     * @param index индекс элемента.
     * @return элемент.
     */
    public synchronized E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> iter = listIterator(index);
        return iter.next();
    }
    /**
     * Возвращает объект списочного итератора.
     * @param index индекс начального элемента итератора.
     * @return объект списочного итератора.
     */
    public abstract ListIterator<E> listIterator(int index);
    /**
     * Удаляет элемент из списка по индексу и сдвигает элементы вычитанием 1 из их индексов.
     * @param index индекс элемента.
     * @return удалённый элемент.
     */
    public synchronized E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> iter = listIterator(index);
        E deleted = iter.next();
        iter.remove();
        return deleted;
    }
    /**
     * Заменяет элемент в список по индексу.
     * @param index индекс заменяемого элемента в списке.
     * @param e заменающий элемент.
     * @return удалённый элемент.
     */
    public synchronized E set(int index, E e) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> iter = listIterator(index);
        E replaced = iter.next();
        iter.set(e);
        return replaced;
    }
}
