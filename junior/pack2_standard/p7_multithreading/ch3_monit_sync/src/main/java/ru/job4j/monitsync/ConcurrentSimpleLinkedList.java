package ru.job4j.monitsync;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс ConcurrentSimpleLinkedList реализует сущность Потокобезопасный связный список.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-27
 */
@ThreadSafe
class ConcurrentSimpleLinkedList<E> extends ConcurrentSimpleAbstractSequentialList<E> implements ISimpleList<E>, ISimpleDeque<E> {
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
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор без параметров.
     */
    ConcurrentSimpleLinkedList() {
       lock = this;
    }
    /**
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в список, иначе false.
     */
    @GuardedBy("lock")
    public boolean add(E e) {
        synchronized (lock) {
            Node<E> tmp = new Node(e);
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
    }
    /**
     * Добавляет элемент в начало списка.
     * @param e добавляемый элемент.
     */
    @GuardedBy("lock")
    public void addFirst(E e) {
        synchronized (lock) {
            Node<E> tmp = new Node(e);
            if (this.size == 0) {
                this.last = tmp;
            } else {
                tmp.setNext(this.first);
                this.first.setPrevious(tmp);
            }
            this.first = tmp;
            this.size++;
        }
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
    @GuardedBy("lock")
    public E element() {
        synchronized (lock) {
            if (this.first == null) {
                throw new NoSuchElementException();
            }
            return this.first.getObject();
        }
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
    @GuardedBy("lock")
    public E getLast() {
        synchronized (lock) {
            return this.last.getObject();
        }
    }
    /**
     * Возвращает объект списочного итератора.
     * @param index индекс начального элемента итератора.
     * @return объект списочного итератора.
     */
    @GuardedBy("lock")
    public ListIterator listIterator(int index) {
        synchronized (lock) {
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException();
            }
            return new SimpleListIterator(index);
        }
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
    @GuardedBy("lock")
    public E poll() {
        synchronized (lock) {
            Node<E> tmp = this.first;
            this.first = tmp.getNext();
            this.first.setPrevious(null);
            this.size--;
            return tmp.getObject();
        }
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
    @GuardedBy("lock")
    public E pollLast() {
        synchronized (lock) {
            Node<E> tmp = this.last;
            this.last = tmp.getPrevious();
            this.last.setNext(null);
            this.size--;
            return tmp.getObject();
        }
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
    @GuardedBy("lock")
    private void setSize(int size) {
        synchronized (lock) {
            this.size = size;
        }
    }
    /**
     * Возвращает коллекцию в виде массива.
     * @return коллекция в виде массива.
     */
    @GuardedBy("lock")
    public Object[] toArray() {
        synchronized (lock) {
            Object[] arr = new Object[this.size];
            Node<E> cur = first;
            for (int a = 0; cur != null; cur = cur.getNext()) {
                arr[a++] = cur.getObject();
            }
            return arr;
        }
    }
    /**
     * Возвращает коллекцию в виде массива.
     * @param <T> параметризированный тип.
     * @param parr массив для заполнения.
     * @return коллекция в виде массива.
     */
    @GuardedBy("lock")
    public <T> T[] toArray(T[] parr) {
        synchronized (lock) {
            if (parr.length < this.size) {
                parr = (T[]) java.lang.reflect.Array.newInstance(parr.getClass().getComponentType(), this.size);
            }
            Object[] arr = parr;
            Node<E> cur = first;
            for (int a = 0; cur != null; cur = cur.next) {
                arr[a++] = cur.getObject();
            }
            if (parr.length > this.size) {
                parr[this.size] = null;
            }
            return parr;
        }
    }
    /**
     * Класс SimpleListIterator реализует сущность Списочный итератор.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 2
     * @since 2017-05-27
     */
    @ThreadSafe
    private class SimpleListIterator implements ListIterator<E> {
        /**
         * Текущий индекс итератора.
         */
        private int index = 0;
        /**
         * Последний узел, полученный итератором.
         */
        private Node<E> cur;
        /**
         * Конструктор.
         * @param index начальный элемент итератора.
         */
        SimpleListIterator(int index) {
            if (index < 0 || index >= ConcurrentSimpleLinkedList.this.size()) {
                throw new IndexOutOfBoundsException();
            }
            if (index > (int) (ConcurrentSimpleLinkedList.this.size() / 2)) {
                this.cur = ConcurrentSimpleLinkedList.this.last;
                for (int a = ConcurrentSimpleLinkedList.this.size() - 1; a > index; a--) {
                    this.cur = this.cur.getPrevious();
                    if (a == index) {
                        break;
                    }
                }
            } else {
                this.cur = ConcurrentSimpleLinkedList.this.first;
                for (int a = 0; a < index; a++) {
                    this.cur = this.cur.getNext();
                }
            }
            this.index = index;
        }
        /**
         * Добавляет элемент в конец списка.
         * @param e добавляемый элемент.
         */
        @GuardedBy("lock")
        public void add(E e) {
            synchronized (lock) {
                Node<E> tmp = new Node(e);
                tmp.setPrevious(this.cur.getPrevious());
                this.cur.getPrevious().setNext(tmp);
                tmp.setNext(this.cur);
                this.cur.setPrevious(tmp);
                this.index++;
                ConcurrentSimpleLinkedList.this.setSize(ConcurrentSimpleLinkedList.this.size() + 1);
            }
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
        @GuardedBy("lock")
        public void set(E e) {
            synchronized (lock) {
                if (null == this.cur) {
                    ConcurrentSimpleLinkedList.this.last.setObject(e);
                } else {
                    this.cur.getPrevious().setObject(e);
                }
            }
        }
        /**
         * Удаляет текущий элемент из списка.
         */
        @GuardedBy("lock")
        public void remove() {
            synchronized (lock) {
                this.cur.setPrevious(this.cur.getPrevious().getPrevious());
                this.cur.getPrevious().setNext(this.cur);
                ConcurrentSimpleLinkedList.this.setSize(ConcurrentSimpleLinkedList.this.size() - 1);
            }
        }
    }
    /**
     * Класс Node реализует сущность Элемент списка.
     *
     * @param <E> параметризированный тип.
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-05-28
     */
    private class Node<E> {
        /**
         * Объект, сохраняемый в элементе списка.
         */
        private E object;
        /**
         * Ссылка на предыдущий элемент списка.
         */
        private Node<E> previous;
        /**
         * Ссылка на следующий элемент списка.
         */
        private Node<E> next;
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
        public Node<E> getPrevious() {
            return this.previous;
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
         * Устанавливает предыдущий элемент списка.
         * @param previous предыдущий элемент списка.
         */
        public void setPrevious(Node<E> previous) {
            this.previous = previous;
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
