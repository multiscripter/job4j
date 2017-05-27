package ru.job4j.list;

import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Класс SimpleLinkedList реализует сущность Связный список.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-26
 */
class SimpleLinkedList<E> extends SimpleAbstractSequentialList<E> implements ISimpleList<E>, ISimpleDeque<E> {
    /**
     * Добавляет элемент в начало очереди.
     * @param e добавляемый элемент.
     */
    public void addFirst(E e) {
    }
    /**
     * Добавляет элемент в конец очереди.
     * @param e добавляемый элемент.
     */
    public void addLast(E e) {
    }
    /**
     * Получает головной элемент из очереди.
     * @return элемент или бросает NoSuchElementException если очередь пуста.
     */
    public E element() {
        return (E) new Object();
    }
    /**
     * Получает первый элемент в очереди.
     * @return элемент.
     */
    public E getFirst() {
        return (E) new Object();
    }
    /**
     * Получает последний элемент в очереди.
     * @return элемент.
     */
    public E getLast() {
        return (E) new Object();
    }
    /**
     * Возвращает объект списочного итератора.
     * @param index индекс начального элемента итератора.
     * @return объект списочного итератора.
     */
    public SimpleListIterator listIterator(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return new SimpleListIterator(index);
    }
    /**
     * Добавляет элемент в очередь если есть место.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в конец очереди, иначе false.
     */
    public boolean offer(E e) {
        return true;
    }
    /**
     * Добавляет элемент в начало очереди.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в начало очереди, иначе false.
     */
    public boolean offerFirst(E e) {
        return true;
    }
    /**
     * Добавляет элемент в конец очереди.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в конец очереди, иначе false.
     */
    public boolean offerLast(E e) {
        return true;
    }
    /**
     * Получает головной элемент из очереди.
     * @return элемент или null если очередь пуста.
     */
    public E peek() {
        return (E) new Object();
    }
    /**
     * Получает первый элемент из очереди.
     * @return элемент или null если очередь пуста.
     */
    public E peekFirst() {
        return (E) new Object();
    }
    /**
     * Получает последний элемент из очереди.
     * @return элемент или null если очередь пуста.
     */
    public E peekLast() {
        return (E) new Object();
    }
    /**
     * Получает и удаляет головной элемент из очереди.
     * @return удалённый элемент или null если очередь пуста.
     */
    public E poll() {
        return (E) new Object();
    }
    /**
     * Получает и удаляет первый элемент в очереди.
     * @return удалённый элемент или null если очередь пуста.
     */
    public E pollFirst() {
        return (E) new Object();
    }
    /**
     * Получает и удаляет последний элемент в очереди.
     * @return удалённый элемент или null если очередь пуста.
     */
    public E pollLast() {
        return (E) new Object();
    }
    /**
     * Добавляет элемент в начало очереди. Эквивалент addFirst(E e).
     * @param e добавляемый элемент.
     */
    public void push(E e) {
    }
    /**
     * Получает и удаляет первый элемент в очереди. Эквивалент removeFirst().
     * @return элемент или null если очередь пуста.
     */
    public E pop() {
        return (E) new Object();
    }
    /**
     * Получает и удаляет первый элемент в очереди.
     * @return элемент.
     */
    public E remove() {
        return (E) new Object();
    }
    /**
     * Получает и удаляет первый элемент в очереди.
     * @return элемент или null если очередь пуста.
     */
    public E removeFirst() {
        return (E) new Object();
    }
    /**
     * Получает и удаляет последний элемент в очереди.
     * @return элемент или null если очередь пуста.
     */
    public E removeLast() {
        return (E) new Object();
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
        //E previously = (E) this.objects[index];
        //this.objects[index] = e;
        //return previously;
        return (E) new Object();
    }
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    public int size() {
        return -1;
        //return this.cursor > Integer.MAX_VALUE ? Integer.MAX_VALUE : this.cursor;
    }
    /**
     * Класс SimpleListIterator реализует сущность Списочный итератор.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-05-27
     */
    private class SimpleListIterator implements ListIterator<E> {
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
         * Проверяет существование следующего элемента.
         * @return true если следующий элемент существует, иначе false.
         */
        public boolean hasNext() {
            return this.index < size();
        }
        /**
         * Проверяет существование предыдущего элемента.
         * @return true если предыдущий элемент существует, иначе false.
         */
        public boolean hasPrevious() {
            return this.index != 0;
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
            SimpleLinkedList.this.set(this.index, e);
        }
        /**
         * Удаляет текущий элемент из списка.
         */
        public void remove() {
            SimpleLinkedList.this.remove();
        }
    }
}
