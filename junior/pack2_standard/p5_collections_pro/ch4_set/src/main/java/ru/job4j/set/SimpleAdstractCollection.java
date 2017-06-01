package ru.job4j.set;

import java.util.Iterator;
/**
 * Класс SimpleAdstractCollection реализует сущность Абстрактная коллекция.
 *
 * @param <E> обобщённый тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-06-01
 */
abstract class SimpleAdstractCollection<E> extends Object implements ISimpleCollection<E> {
    /**
     * Добавляет элемент в коллекцию.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в коллекцию.
     */
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }
    /**
     * Проверяет коллекцию на пустоту.
     * @return true если коллекция не содержит элементов, иначе false.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }
    /**
     * Возвращает итератор по элементам, содержащимся в коллекции.
     * @return итератор по элементам, содержащимся в коллекции.
     */
    public abstract Iterator<E> iterator();
    /**
     * Возвращает размер коллекции.
     * @return размер коллекции.
     */
    public abstract int size();
    /**
     * Возвращает массив объектов коллекции обобщёного типа.
     * @param arr массив, в который будут помещены объекты стэка.
     * @return массив объектов коллекции обобщёного типа.
     */
    public E[] toArray(E[] arr) {
        int length = this.size() < arr.length ? this.size() : arr.length;
        Iterator<E> iter = this.iterator();
        for (int a = 0; a < length; a++) {
            arr[a] = iter.next();
        }
        return arr;
    }
}
