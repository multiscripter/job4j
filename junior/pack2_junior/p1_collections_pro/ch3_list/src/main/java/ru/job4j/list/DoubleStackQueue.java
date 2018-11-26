package ru.job4j.list;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Класс DoubleStackQueue реализует сущность Списочная очередь на стэках.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-17
 * @since 2018-09-15
 */
public class DoubleStackQueue<E> extends SimpleAbstractQueue<E> {
    /**
     * Стэк 1.
     */
    private SimpleLinkedStack<E> stack1 = new SimpleLinkedStack<>();
    /**
     * Стэк 2.
     */
    private SimpleLinkedStack<E> stack2 = new SimpleLinkedStack<>();
    /**
     * Добавляет элемент на вершину стэка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в список, иначе false.
     */
    public boolean add(E e) {
        if (this.stack1.add(e)) {
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }
    /**
     * Получает объект итератора.
     * @return объект итератора.
     */
    public Iterator<E> iterator() {
        return new SimpleIterator();
    }
    /**
     * Добавляет элемент в конец очереди.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в список, иначе false.
     */
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        return this.stack1.add(e);
    }
    /**
     * Получает головной элемент из списка.
     * @return элемент или null если список пуст.
     */
    public E peek() {
        this.shift();
        return this.stack2.peek();
    }
    /**
     * Получает и удаляет головной элемент из списка.
     * @return удалённый элемент или null если список пуст.
     */
    public E poll() {
        this.shift();
        return this.stack2.pop();
    }
    /**
     * Перекладывает элементы из первого стэка во второй стэк.
     */
    private void shift() {
        if (this.stack2.size() > 0) {
            return;
        }
        while (this.stack1.size() > 0) {
            this.stack2.push(this.stack1.pop());
        }
    }
    /**
     * Класс SimpleIterator реализует сущность Итератор.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 2018-09-15
     * @since 2018-09-15
     */
    private class SimpleIterator implements Iterator<E> {
        /**
         * Временный стэк.
         */
        private ListIterator<E> s = null;
        /**
         * Конструктор.
         */
        SimpleIterator() {
            shift();
            this.s = stack2.listIterator();
        }
        /**
         * Проверяет существование следующего элемента.
         * @return true если следующий элемент существует, иначе false.
         */
        public boolean hasNext() {
            return this.s.hasPrevious();
        }
        /**
         * Возвращает значение следующего элемента списка.
         * @return значение следующего элемента списка.
         */
        public E next() {
            try {
                return this.s.previous();
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException();
            }
        }
    }
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    public int size() {
        return this.stack1.size() + this.stack2.size();
    }
}
