package ru.job4j.list;

import java.util.RandomAccess;
/**
 * Класс SimpleArrayList реализует сущность Списочный массив.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-25
 */
class SimpleArrayList<E> extends SimpleAdstractList<E> implements ISimpleList<E>, RandomAccess {
    /**
     * Индекс коллекции.
     */
    private int index = 0;
    /**
     * Массив объектов коллекции.
     */
    private Object[] objects;
    /**
     * Размер массива элементов.
     */
    private int size;
    /**
     * Добавляет элемент в список по индексу.
     * @param index индекс в списке.
     * @param e добавляемый элемент.
     */
    public void add(int index, E e) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.objects[index] = e;
    }
    /**
     * Получает элемент из списка по индексу.
     * @param index индекс элемента.
     * @return элемент.
     */
    public E get(int index) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        return (E) this.objects[index];
    }
    /**
     * Удаляет элемент из списка по индексу и сдвигает элементы вычитанием 1 из их индексов.
     * @param index индекс элемента.
     * @return удалённый элемент.
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        E e = (E) this.objects[index];
        for (int size = this.index - 1; index < size;) {
            this.objects[index] = this.objects[++index];
        }
        this.size--;
        this.index--;
        return e;
    }
    /**
     * Заменяет элемент в список по индексу.
     * @param index индекс заменяемого элемента в списке.
     * @param e заменающий элемент.
     * @return удалённый элемент.
     */
    public E set(int index, E e) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        E previously = (E) this.objects[index];
        this.objects[index] = e;
        return previously;
    }
    /**
     * Возвращает размер коллекции.
     * @return размер коллекции.
     */
    public int size() {
        return this.index > Integer.MAX_VALUE ? Integer.MAX_VALUE : this.index;
    }
}