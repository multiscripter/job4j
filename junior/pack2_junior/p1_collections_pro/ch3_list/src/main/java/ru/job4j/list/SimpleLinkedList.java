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
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в список, иначе false.
     */
    public boolean add(E e) {
        Node tmp = new Node(e);
        if (this.size == 0) {
            this.first = tmp;
        } else {
            tmp.setPrevious(this.last);
            this.last.setNext(tmp);
        }
        this.last = tmp;
        this.size++;
        return true;
    }
    /**
     * Добавляет элемент в начало списка.
     * @param e добавляемый элемент.
     */
    public void addFirst(E e) {
        Node tmp = new Node(e);
        if (this.size == 0) {
            this.last = tmp;
        } else {
            tmp.setNext(this.first);
            this.first.setPrevious(tmp);
        }
        this.first = tmp;
        this.size++;
    }
    /**
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     */
    public void addLast(E e) {
        this.add(e);
    }
    /**
     * Получает головной элемент из списка.
     * @return элемент или бросает NoSuchElementException если список пуста.
     */
    public E element() {
        if (this.first == null) {
            throw new NoSuchElementException();
        }
        return this.first.getObject();
    }
    /**
     * Получает первый элемент в списке.
     * @return элемент.
     */
    public E getFirst() {
        return this.element();
    }
    /**
     * Получает последний элемент в списке.
     * @return элемент.
     */
    public E getLast() {
        return this.last.getObject();
    }
    /**
     * Возвращает объект списочного итератора.
     * @param index индекс начального элемента итератора.
     * @return объект списочного итератора.
     */
    public ListIterator listIterator(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return new SimpleListIterator(index);
    }
    /**
     * Добавляет элемент в список если есть место.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в конец списка.
     */
    public boolean offer(E e) {
        return this.add(e);
    }
    /**
     * Добавляет элемент в начало списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в начало списка.
     */
    public boolean offerFirst(E e) {
        this.addFirst(e);
        return true;
    }
    /**
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в конец списка.
     */
    public boolean offerLast(E e) {
        return this.add(e);
    }
    /**
     * Получает головной элемент из списка.
     * @return элемент или null если список пуст.
     */
    public E peek() {
        return this.first.getObject();
    }
    /**
     * Получает первый элемент из списка.
     * @return элемент или null если список пуст.
     */
    public E peekFirst() {
        return this.first.getObject();
    }
    /**
     * Получает последний элемент из списка.
     * @return элемент или null если список пуст.
     */
    public E peekLast() {
        return this.last.getObject();
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
     * Получает и удаляет первый элемент в списке.
     * @return удалённый элемент или null если список пуст.
     */
    public E pollFirst() {
        return this.poll();
    }
    /**
     * Получает и удаляет последний элемент в списке.
     * @return удалённый элемент или null если список пуст.
     */
    public E pollLast() {
        Node tmp = this.last;
        this.last = tmp.getPrevious();
        this.last.setNext(null);
        this.size--;
        return tmp.getObject();
    }
    /**
     * Добавляет элемент в начало списка. Эквивалент addFirst(E e).
     * @param e добавляемый элемент.
     */
    public void push(E e) {
        this.addFirst(e);
    }
    /**
     * Получает и удаляет первый элемент в списке. Эквивалент removeFirst().
     * @return элемент или null если список пуст.
     */
    public E pop() {
        E tmp = this.poll();
        if (tmp == null) {
            throw new NoSuchElementException();
        }
        return tmp;
    }
    /**
     * Получает и удаляет первый элемент в списке.
     * @return элемент или null если список пуст.
     */
    public E remove() {
        E tmp = this.poll();
        if (tmp == null) {
            throw new NoSuchElementException();
        }
        return tmp;
    }
    /**
     * Получает и удаляет первый элемент в списке.
     * @return элемент или null если список пуст.
     */
    public E removeFirst() {
        E tmp = this.poll();
        if (tmp == null) {
            throw new NoSuchElementException();
        }
        return tmp;
    }
    /**
     * Получает и удаляет последний элемент в списке.
     * @return элемент или null если список пуст.
     */
    public E removeLast() {
        E tmp = this.pollLast();
        if (tmp == null) {
            throw new NoSuchElementException();
        }
        return tmp;
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
         * Последний узел, полученный итератором.
         */
        private Node cur;
        /**
         * Конструктор.
         * @param index начальный элемент итератора.
         */
        SimpleListIterator(int index) {
            if (index < 0 || index >= SimpleLinkedList.this.size()) {
                throw new IndexOutOfBoundsException();
            }
            if (index > SimpleLinkedList.this.size() / 2) {
                this.cur = SimpleLinkedList.this.last;
                for (int a = SimpleLinkedList.this.size() - 1, end = index - 1; a > end; a--) {
                    if (a == index) {
                        break;
                    }
                    this.cur = this.cur.getPrevious();
                }
            } else {
                this.cur = SimpleLinkedList.this.first;
                for (int a = 0, end = index + 1; a < end; a++) {
                    if (a == index) {
                        break;
                    }
                    this.cur = this.cur.getNext();
                }
            }
            this.index = index;
        }
        /**
         * Добавляет элемент в конец списка.
         * @param e добавляемый элемент.
         */
        public void add(E e) {
            Node tmp = new Node(e);
            tmp.setPrevious(this.cur.getPrevious());
            this.cur.getPrevious().setNext(tmp);
            tmp.setNext(this.cur);
            this.cur.setPrevious(tmp);
            this.index++;
            SimpleLinkedList.this.setSize(SimpleLinkedList.this.size() + 1);
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
            return this.index > -1;
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
                E tmp = this.cur.getObject();
                this.cur = this.cur.getPrevious();
                this.index--;
                return tmp;
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
            this.cur.getPrevious().setObject(e);
        }
        /**
         * Удаляет текущий элемент из списка.
         */
        public void remove() {
            this.cur.setPrevious(this.cur.getPrevious().getPrevious());
            this.cur.getPrevious().setNext(this.cur);
            SimpleLinkedList.this.setSize(SimpleLinkedList.this.size() - 1);
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
