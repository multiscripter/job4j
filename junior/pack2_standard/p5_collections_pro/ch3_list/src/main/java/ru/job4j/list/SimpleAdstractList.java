package ru.job4j.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс SimpleAdstractList реализует сущность Абстрактный список.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-25
 */
abstract class SimpleAdstractList<E> extends SimpleAdstractCollection<E> implements ISimpleList<E> {
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
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в коллекцию.
     */
    public boolean add(E e) {
        this.objects[this.index++] = e;
        return true;
    }
    /**
     * Добавляет элемент в список по индексу.
     * @param index индекс в списке.
     * @param e добавляемый элемент.
     */
    public void add(int index, E e) {
        throw new UnsupportedOperationException();
    }
    /**
     * Получает элемент из списка по индексу.
     * @param index индекс элемента.
     * @return элемент.
     */
    public abstract E get(int index);
    /**
     * Возвращает объект итератора.
     * @return объект итератора.
     */
    public Iterator<E> iterator() {
        return new SimpleIterator();
    }
    /**
     * Удаляет элемент из списка по индексу и сдвигает элементы вычитанием 1 из их индексов.
     * @param index индекс элемента.
     * @return удалённый элемент.
     */
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }
    /**
     * Заменяет элемент в список по индексу.
     * @param index индекс заменяемого элемента в списке.
     * @param e заменающий элемент.
     * @return удалённый элемент.
     */
    public E set(int index, E e) {
        throw new UnsupportedOperationException();
    }
    /**
     * Класс SimpleIterator реализует сущность Итератор.
     *
     * @param <E> параметризированный тип.
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-05-25
     */
    private class SimpleIterator<E> implements Iterator<E> {
        /**
         * Текущий индекс итератора.
         */
        private int index = 0;
        /**
         * Проверяет существование следующего элемента.
         * @return true если следующий элемент существует, иначе false.
         */
        public boolean hasNext() {
            return this.index < index;
        }
        /**
         * Возвращает значение следующего элемента списка.
         * @return значение следующего элемента списка.
         */
        public E next() {
            try {
                return (E) get(this.index++);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException();
            }
        }
        /**
         * Удаляет элемента массива.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}