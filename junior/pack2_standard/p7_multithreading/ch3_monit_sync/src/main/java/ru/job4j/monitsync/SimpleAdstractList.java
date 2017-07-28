package ru.job4j.monitsync;

import java.util.Iterator;
import java.util.ListIterator;
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
     * Заменяет элемент в списке по индексу.
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
            return this.index < size();
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
         * Удаляет текущий элемент из списка.
         */
        public void remove() {
            SimpleAdstractList.this.remove(this.index--);
        }
    }
    /**
     * Класс SimpleListIterator реализует сущность Списочный итератор.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-05-27
     */
    private class SimpleListIterator extends SimpleIterator<E> implements ListIterator<E> {
        /**
         * Текущий индекс итератора.
         */
        private int index = 0;
        /**
         * Конструктор.
         * @param index начальный элемент итератора.
         */
        SimpleListIterator(int index) {
            this.index = index;
        }
        /**
         * Добавляет элемент в конец списка.
         * @param e добавляемый элемент.
         */
        public void add(E e) {
            add(e);
            this.index++;
        }
        /**
         * Проверяет существование предыдущего элемента.
         * @return true если предыдущий элемент существует, иначе false.
         */
        public boolean hasPrevious() {
            return this.index != 0;
        }
        /**
         * Возвращает индекс значения следующего элемента списка.
         * @return индекс значения следующего элемента списка.
         */
        public int nextIndex() {
            return this.index;
        }
        /**
         * Возвращает значение предыдущего элемента списка.
         * @return значение предыдущего элемента списка.
         */
        public E previous() {
            try {
                return (E) get(this.index--);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException();
            }
        }
        /**
         * Возвращает индекс значения предыдущего элемента списка.
         * @return индекс значения предыдущего элемента списка.
         */
        public int previousIndex() {
            return this.index - 1;
        }
        /**
         * Заменяет текущий элемент в списке.
         * @param e заменающий элемент.
         */
        public void set(E e) {
            SimpleAdstractList.this.set(this.index, (E) e);
        }
        /**
         * Удаляет текущий элемент из списка.
         */
        public void remove() {
            SimpleAdstractList.this.remove(this.index--);
        }
    }
}
