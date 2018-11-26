package ru.job4j.list;

import java.util.NoSuchElementException;
/**
 * Класс SimpleAbstractQueue реализует сущность Абстрактная очередь.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-30
 */
public abstract class SimpleAbstractQueue<E> extends SimpleAdstractCollection<E> implements ISimpleQueue<E> {
    /**
     * Добавляет элемент в очередь.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен, иначе бросает исключение (очередь заполнена).
     */
    public boolean add(E e) {
        if (offer(e)) {
            return true;
        } else {
            throw new IllegalStateException("Queue full");
        }
    }
    /**
     * Получает первый элемент из очереди, инача бросает исключение (очередь пуста).
     * @return удалённый элемент.
     */
    public E element() {
        E x = peek();
        if (x != null) {
            return x;
        } else {
            throw new NoSuchElementException();
        }
    }
    /**
     * Удаляет и возвращает первый элемент из очереди, иначе бросает исключение (очередь пуста).
     * @return удалённый элемент.
     */
    public E remove() {
        E x = poll();
        if (x != null) {
            return x;
        } else {
            throw new NoSuchElementException();
        }
    }
}