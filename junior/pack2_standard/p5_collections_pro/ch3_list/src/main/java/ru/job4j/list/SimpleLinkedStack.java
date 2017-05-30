package ru.job4j.list;

//import java.util.Iterator;
import java.util.ListIterator;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
/**
 * Класс SimpleLinkedStack реализует сущность Списочный стэк.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-30
 */
public class SimpleLinkedStack<E> extends SimpleAbstractSequentialList<E> {
    /**
     * Первый элемент в стэке.
     */
    private Node first;
    /**
     * Последний элемент в стэке.
     */
    private Node last;
    /**
     * Размер списка.
     */
    private int size = 0;
    /**
     * Добавляет элемент на вершину стэка.
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
     * Очищает стэк удалением всех элементов.
     */
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    /**
     * Проверяет коллекцию на пустоту.
     * @return true если коллекция не содержит элементов, иначе false.
     */
    public boolean empty() {
        return this.first == null && this.last == null;
    }
    /**
     * Возвращает объект списочного итератора.
     * @return объект списочного итератора.
     */
    public ListIterator listIterator() {
        return new SimpleListIterator();
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
     * Получает элемент вершины стэка.
     * @return элемент или бросает исключение "Пустой стэк".
     */
    public E peek() {
        if (this.size == 0) {
            throw new EmptyStackException();
        }
        return this.last.getObject();
    }
    /**
     * Получает и удаляет головной элемент из списка.
     * @return удалённый элемент или null если список пуст.
     */
    public E pop() {
        if (this.size == 0) {
            throw new EmptyStackException();
        }
        Node tmp = this.last;
        if (this.size == 1) {
            this.first = null;
            this.last = null;
        } else {
            this.last = tmp.getPrevious();
            this.last.setNext(null);
            tmp.setPrevious(null);
        }
        this.size--;
        return tmp.getObject();
    }
    /**
     * Добавляет элемент на вершину стэка.
     * @param item добавляемый элемент.
     * @return item добавленный элемент.
     */
    public E push(E item) {
        this.add(item);
        return item;
    }
    /**
     * Производит поиск элемента в стэке и возвращает расстояние от вершины стэка до элемента.
     * @param o искомый элемент.
     * @return расстояние от вершины стэка до элемента.
     */
    public int search(Object o) {
        int shift = 0;
        ListIterator iter = new SimpleListIterator();
        while (iter.hasPrevious()) {
            if (o.equals((Object) iter.previous())) {
                break;
            }
            shift++;
        }
        return shift == this.size ? -1 : shift;
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
         * Конструктор без араметров.
         */
        SimpleListIterator() {
            this(0);
        }
        /**
         * Конструктор.
         * @param index начальный элемент итератора.
         */
        SimpleListIterator(int index) {
            if (index < 0 || index >= SimpleLinkedStack.this.size()) {
                throw new IndexOutOfBoundsException();
            }
            if (index < SimpleLinkedStack.this.size() / 2) {
                this.cur = SimpleLinkedStack.this.last;
                for (int a = 0, end = index + 1; a < end; a++) {
                    if (a == index) {
                        break;
                    }
                    this.cur = this.cur.getPrevious();
                }
            } else {
                this.cur = SimpleLinkedStack.this.first;
                for (int a = SimpleLinkedStack.this.size() - 1, end = index - 1; a > end; a--) {
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
            SimpleLinkedStack.this.setSize(SimpleLinkedStack.this.size() + 1);
        }
        /**
         * Проверяет существование следующего элемента.
         * @return true если следующий элемент существует, иначе false.
         */
        public boolean hasNext() {
            return this.index > -1;
        }
        /**
         * Проверяет существование предыдущего элемента.
         * @return true если предыдущий элемент существует, иначе false.
         */
        public boolean hasPrevious() {
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
                this.index--;
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
                this.index++;
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
            SimpleLinkedStack.this.setSize(SimpleLinkedStack.this.size() - 1);
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
