package ru.job4j.list;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс SimpleLinkedPriorityQueue реализует сущность Очередь с приоритетом.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-30
 */
public class SimpleLinkedPriorityQueue<E> extends SimpleAbstractQueue<E> {
    /**
     * Первый элемент в списке.
     */
    private Node first;
    /**
     * Последний элемент в списке.
     */
    private Node last;
    /**
     * Размер списка.
     */
    private int size = 0;
    /**
     * Компаратор по умолчанию.
     */
    private final Comparator<? super E> comparator;
    /**
     * Конструктор без параметров.
     */
    SimpleLinkedPriorityQueue() {
        this.comparator = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return Integer.compare(o1.hashCode(), o2.hashCode());
            }
        };
    }
    /**
     * Конструктор.
     * @param comparator объект компаратора.
     */
    SimpleLinkedPriorityQueue(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }
    /**
     * Очищает очередь удалением всех элементов.
     */
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    /**
     * Проверяет содержит ли очередь заданный элемент.
     * @param object объект поиска.
     * @return true если очередь содержит заданный элемент, иначе false;
     */
    public boolean contains(Object object) {
        boolean result = false;
        Iterator<E> iter = this.iterator();
        for (int a = 0; a < this.size; a++) {
            if (iter.next().equals((E) object)) {
                result = true;
                break;
            }
        }
        return result;
    }
    /**
     * Получает объект компаратора.
     * @return объект компаратора.
     */
    public Comparator<? super E> comparator() {
        return this.comparator;
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
        Node tmp = new Node(e);
        if (this.size == 0) {
            this.first = tmp;
            this.last = tmp;
        } else {
            Node cur = this.first;
            int a = this.size;
            while (a > 0) {
                if (this.comparator.compare(e, cur.getObject()) <= 0) {
                    tmp.setPrevious(cur.getPrevious());
                    if (a < this.size) {
                        cur.getPrevious().setNext(tmp);
                    }
                    cur.setPrevious(tmp);
                    tmp.setNext(cur);
                    break;
                }
                cur = cur.getNext();
                a--;
            }
            if (a == this.size) {
                this.first = tmp;
            } else if (a == 0) {
                this.last.setNext(tmp);
                tmp.setPrevious(this.last);
                this.last = tmp;
            }
        }
        this.size++;
        return true;
    }
    /**
     * Получает головной элемент из списка.
     * @return элемент или null если список пуст.
     */
    public E peek() {
        return this.first.getObject();
    }
    /**
     * Получает и удаляет головной элемент из списка.
     * @return удалённый элемент или null если список пуст.
     */
    public E poll() {
        Node tmp = this.first;
        this.first = tmp.getNext();
        this.first.setPrevious(null);
        this.size--;
        return tmp.getObject();
    }
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    public int size() {
        return this.size > Integer.MAX_VALUE ? Integer.MAX_VALUE : this.size;
    }
    /**
     * Устанавливает размер списка.
     * @param size размер списка.
     */
    private void setSize(int size) {
        this.size = size;
    }
    /**
     * Возвращает массив объектов очереди.
     * @return массив объектов очереди.
     */
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        Iterator<E> iter = this.iterator();
        for (int a = 0; a < this.size; a++) {
            arr[a] = (Object) iter.next();
        }
        return arr;
    }
    /**
     * Возвращает массив объектов очереди параметризированного типа.
     * @param arr массив, в который будут помещены объекты очереди.
     * @return массив объектов очереди параметризированного типа.
     */
    public E[] toArray(E[] arr) {
        int length = this.size < arr.length ? this.size : arr.length;
        Iterator<E> iter = this.iterator();
        for (int a = 0; a < length; a++) {
            arr[a] = iter.next();
        }
        return arr;
    }
    /**
     * Класс SimpleListIterator реализует сущность Итератор.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-05-30
     */
    private class SimpleIterator implements Iterator<E> {
        /**
         * Текущий индекс итератора.
         */
        private int index = 0;
        /**
         * Последний узел, полученный итератором.
         */
        private Node cur;
        /**
         * Конструктор.
         */
        SimpleIterator() {
            this.cur = SimpleLinkedPriorityQueue.this.first;
        }
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
                E tmp = this.cur.getObject();
                this.cur = this.cur.getNext();
                this.index++;
                return tmp;
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException();
            }
        }
        /**
         * Удаляет текущий элемент из списка.
         */
        public void remove() {
            this.cur.setPrevious(this.cur.getPrevious().getPrevious());
            this.cur.getPrevious().setNext(this.cur);
            SimpleLinkedPriorityQueue.this.setSize(SimpleLinkedPriorityQueue.this.size() - 1);
        }
    }
    /**
     * Класс Node реализует сущность Элемент списка.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-05-28
     */
    private class Node {
        /**
         * Объект, сохраняемый в элементе списка.
         */
        private E object;
        /**
         * Ссылка на предыдущий элемент списка.
         */
        private Node previous;
        /**
         * Ссылка на следующий элемент списка.
         */
        private Node next;
        /**
         * Конструктор.
         * @param e Объект, сохраняемый в элементе списка.
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
         * Получает предыдущий элемент списка.
         * @return предыдущий элемент списка.
         */
        public Node getPrevious() {
            return this.previous;
        }
        /**
         * Получает следующий элемент списка.
         * @return следующий элемент списка.
         */
        public Node getNext() {
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
         * Устанавливает предыдущий элемент списка.
         * @param previous предыдущий элемент списка.
         */
        public void setPrevious(Node previous) {
            this.previous = previous;
        }
        /**
         * Устанавливает следующий элемент списка.
         * @param next следующий элемент списка.
         */
        public void setNext(Node next) {
            this.next = next;
        }
    }
}