package ru.job4j.list;

import java.util.Arrays;
import java.util.Iterator;
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
    private int cursor = 0;
    /**
     * Массив объектов коллекции.
     */
    private Object[] objects;
    /**
     * Конструктор без параметров.
     */
    SimpleArrayList() {
        this.objects = new Object[10];
    }
    /**
     * Конструктор с параметром, устаналивающим размер коллекции.
     * @param capacity размер коллекции.
     */
    SimpleArrayList(int capacity) {
        if (capacity < 0 || capacity > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.objects = new Object[capacity];
    }
    /**
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в коллекцию.
     */
    public boolean add(E e) {
        if (this.cursor == this.objects.length) {
            this.increaseCapacity();
        }
        this.objects[this.cursor++] = e;
        return true;
    }
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
     * Возвращает размер массива объектов коллекции.
     * @return размер массива объектов коллекции.
     */
    public int capacity() {
        return this.objects.length;
    }
    /**
     * Увеличивает размер массива объектов коллекции.
     */
    private void increaseCapacity() {
        Object[] newObjects = new Object[this.objects.length * 2];
        System.arraycopy(this.objects, 0, newObjects, 0, this.size());
        this.objects = newObjects;
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
     * Удаляет элемент из списка если таковой присутствует в нём и сдвигает элементы вычитанием 1 из их индексов.
     * @param o удаляемый элемент.
     * @return true если элемент удалён, иначе false.
     */
    public boolean remove(Object o) {
        Iterator<E> iter = this.iterator();
        boolean result = false;
        int index = 0;
        while (iter.hasNext()) {
            Object tmp = iter.next();
            if (o.equals(tmp)) {
                this.shift(index);
                result = true;
            }
            index++;
        }
        return result;
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
        this.shift(index);
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
     * Сдвигает элементы вычитанием 1 из их индексов.
     * @param index индекс элемента.
     */
    private void shift(int index) {
        System.arraycopy(this.objects, index + 1, this.objects, index, this.size() - index - 1);
        this.cursor--;
    }
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    public int size() {
        return this.cursor > Integer.MAX_VALUE ? Integer.MAX_VALUE : this.cursor;
    }
    /**
     * Возвращает массив элементов коллекции.
     * @return массив элементов в коллекции.
     */
    public Object[] toArray() {
        return Arrays.copyOf(this.objects, this.size());
    }
}
