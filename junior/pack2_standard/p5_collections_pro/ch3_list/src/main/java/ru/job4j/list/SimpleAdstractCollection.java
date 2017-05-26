package ru.job4j.list;

import java.util.Iterator;
/**
 * Класс SimpleAdstractCollection реализует сущность Абстрактная коллекция.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-25
 */
abstract class SimpleAdstractCollection<E> extends Object implements ISimpleCollection<E> {
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
}