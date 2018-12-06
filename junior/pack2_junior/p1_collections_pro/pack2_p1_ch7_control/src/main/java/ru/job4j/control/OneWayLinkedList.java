package ru.job4j.control;

import java.util.Collection;
import java.util.NoSuchElementException;
/**
 * Класс OneWayLinkedList реализует сущность Односторонний связный список.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-24
 */
class OneWayLinkedList<E> {
    /**
     * Первый элемент в списке.
     */
    private Node<E> first;
    /**
     * Последний элемент в списке.
     */
    private Node<E> last;
    /**
     * Размер списка.
     */
    private int size = 0;
    /**
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в список, иначе false.
     */
    public boolean add(E e) {
        Node<E> tmp = new Node(e);
        if (this.size == 0) {
            this.first = tmp;
        } else {
            this.last.setNext(tmp);
        }
        this.last = tmp;
        this.size++;
        return true;
    }
    /**
     * Добавляет элементы в конец списка.
     * @param c добавляемая коллекция элементов.
     * @return true если элементы добавлены в список, иначе false.
     */
    public boolean addAll(Collection<? extends E> c) {
        Object[] objs = c.toArray();
        for (int a = 0; a < objs.length; a++) {
            this.add((E) objs[a]);
        }
        return true;
    }
    /**
     * Получает первый элемент в списке.
     * @return элемент или бросает NoSuchElementException если список пуст.
     */
    public E getFirst() {
        if (this.first == null) {
            throw new NoSuchElementException();
        }
        return this.first.getObject();
    }
    /**
     * Получает последний элемент в списке.
     * @return элемент или бросает NoSuchElementException если список пуст.
     */
    public E getLast() {
        if (this.last == null) {
            throw new NoSuchElementException();
        }
        return this.last.getObject();
    }
    /**
     * Переворачивает список.
     */
    public void reverse() {
        if (this.size > 1) {
            Node<E> cursor = this.first;
            Node<E> tmp = null;
            for (int a = 1; a < size; a++) {
                tmp = cursor.getNext();
                if (null != tmp.getNext()) {
                    cursor.setNext(tmp.getNext());
                }
                tmp.setNext(this.first);
                this.first = tmp;
            }
            cursor.setNext(null);
            this.last = cursor;
        }
    }
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    public int size() {
        return this.size;
    }
    /**
     * Возвращает массив элементов коллекции.
     * @return массив элементов в коллекции.
     */
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        int a = 0;
        for (Node<E> tmp = this.first; tmp != null; tmp = tmp.getNext()) {
            arr[a++] = tmp.getObject();
        }
        return arr;
    }
    /**
     * Возвращает массив элементов коллекции.
     * @param <T> параметризированный тип.
     * @param arr пустой массив.
     * @return массив элементов в коллекции.
     */
     public <T> T[] toArray(T[] arr) {
         int a = 0;
         for (Node<E> tmp = this.first; tmp != null; tmp = tmp.getNext()) {
             arr[a++] = (T) tmp.getObject();
         }
         return arr;
     }
    /**
     * Класс Node реализует сущность Элемент списка.
     *
     * @param <E> параметризированный тип.
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-08-24
     */
    class Node<E> {
        /**
         * Ссылка на следующий элемент списка.
         */
        private Node<E> next;
        /**
         * Объект, сохраняемый в элементе списка.
         */
        private E object;
        /**
         * Конструктор.
         * @param e объект, сохраняемый в элементе списка.
         */
        Node(E e) {
            this.object = e;
        }
        /**
         * Получает объект, сохраняемый в элементе списка.
         * @return объект, сохраняемый в элементе списка.
         */
        public E getObject() {
            return this.object;
        }
        /**
         * Получает следующий элемент списка.
         * @return следующий элемент списка.
         */
        public Node<E> getNext() {
            return this.next;
        }
        /**
         * Устанавливает объект, сохраняемый в элементе списка.
         * @param object объект, сохраняемый в элементе списка.
         */
        public void setObject(E object) {
            this.object = object;
        }
        /**
         * Устанавливает следующий элемент списка.
         * @param next следующий элемент списка.
         */
        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
}