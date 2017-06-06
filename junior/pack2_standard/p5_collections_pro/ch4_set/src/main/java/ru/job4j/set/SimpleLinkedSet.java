package ru.job4j.set;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
/**
 * Класс SimpleLinkedSet реализует сущность Множество на связном множестве.
 *
 * @param <E> обобщённый тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-03
 */
class SimpleLinkedSet<E> extends SimpleAbstractSet<E> implements ISimpleSet<E> {
    /**
     * Первый элемент в множестве.
     */
    private Node first;
    /**
     * Средний элемент в множестве.
     */
    private Node middle;
    /**
     * Индекс среднего элемента в множестве.
     */
    private int midindex;
    /**
     * Последний элемент в множестве.
     */
    private Node last;
    /**
     * Размер множества.
     */
    private int size = 0;
    /**
     * Добавляет элемент в конец множества.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в множеств, иначе false.
     */
    public boolean add(E e) {
        boolean result = false;
        if (!this.contains(e)) {
            Node tmp = new Node(e);
            if (this.size == 0) {
                this.first = tmp;
                this.middle = tmp;
                this.midindex = 0;
            } else {
                tmp.setPrevious(this.last);
                this.last.setNext(tmp);
            }
            this.last = tmp;
            this.size++;
            if (this.size > 2 && this.size % 2 != 0) {
                this.middle = this.middle.getNext();
                this.midindex++;
            }
            result = true;
        }
        return result;
    }
    /**
     * Очищает множество.
     */
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    /**
     * Проверяет содержится ли указанный объект в множестве.
     * @param o проверяемый объект.
     * @return true если объект содержится в множестве. Иначе false.
     */
    public boolean contains(Object o) {
        boolean result = false;
        int iters = 0;
        if (this.size() > 0) {
            System.out.println("this.size(): " + this.size());
            System.out.println("o: " + o);
            int lIndex = 0;
            int uIndex = this.size;
            Node cur = this.middle;
            System.out.println("this.middle: " + this.middle.getObject());
            int curIndex = this.midindex;
            while (true) {
                if (cur.getObject().equals((E) o)) {
                    result = true;
                    break;
                } else if (uIndex <= lIndex) {
                    break;
                }
                if (cur.compareTo((E) o) < 0) {
                    lIndex = ++curIndex;
                } else {
                    uIndex = --curIndex;
                }
                int mIndex = (lIndex + uIndex) / 2;
                System.out.println("curIndex: " + curIndex);
                System.out.println("mIndex: " + mIndex);
                while (curIndex < mIndex) {
                    cur = curIndex < mIndex ? cur.getNext() : cur.getPrevious();
                    System.out.println("cur.getObject(): " + cur.getObject());
                    curIndex = curIndex < mIndex ? curIndex + 1 : curIndex - 1;
                }
                System.out.println("curIndex: " + curIndex);
                iters++;
            }
        }
        System.out.println("result: " + result);
        System.out.println(iters);
        return result;
    }
    /**
     * Проверяет содержится ли указанный объект в множестве.
     * @return true если объект содержится в множестве. Иначе false.
     */
    public Iterator<E> iterator() {
        return new SimpleIterator();
    }
    /**
     * Удаляет элемент из множества если таковой присутствует в нём.
     * @param o удаляемый элемент.
     * @return true если элемент удалён, иначе false.
     */
    public boolean remove(Object o) {
        Iterator iter = this.iterator();
        boolean result = false;
        int index = 0;
        while (iter.hasNext()) {
            Object tmp = iter.next();
            if (o.equals(tmp)) {
                iter.remove();
                result = true;
                if (this.size % 2 != 0) {
                    this.middle = this.middle.getPrevious();
                    this.midindex--;
                }
                break;
            }
            index++;
        }
        return result;
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
     * Возвращает массив элементов коллекции.
     * @return массив элементов в коллекции.
     */
    public Object[] toArray() {
        Object[] arr = new Object[this.size()];
        Iterator iter = this.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            arr[a] = iter.next();
        }
        return arr;
    }
    /**
     * Класс SimpleIterator реализует сущность Итератор.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 2
     * @since 2017-06-03
     */
    private class SimpleIterator implements Iterator<E> {
        /**
         * Текущий индекс итератора.
         */
        private int index = 0;
        /**
         * Текущий объект в итераторе.
         */
        private Node cur;
        /**
         * Конструктор.
         */
        SimpleIterator() {
            this.cur = SimpleLinkedSet.this.first;
        }
        /**
         * Проверяет существование следующего элемента.
         * @return true если следующий элемент существует, иначе false.
         */
        public boolean hasNext() {
            return this.index < size();
        }
        /**
         * Возвращает значение следующего элемента множества.
         * @return значение следующего элемента множества.
         */
        public E next() {
            try {
                this.cur = this.index == 0 ? SimpleLinkedSet.this.first : this.cur.getNext();
                this.index++;
                return this.cur.getObject();
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException();
            }
        }
        /**
         * Удаляет текущий элемент из множества.
         */
        public void remove() {
            if (SimpleLinkedSet.this.size() == 1) {
                SimpleLinkedSet.this.first = null;
                SimpleLinkedSet.this.last = null;
            } else if (SimpleLinkedSet.this.first.getObject().equals(this.cur.getObject())) {
                SimpleLinkedSet.this.first = this.cur.getNext();
                this.cur.setPrevious();
            } else if (SimpleLinkedSet.this.last.getObject().equals(this.cur.getObject())) {
                SimpleLinkedSet.this.last = this.cur.getPrevious();
                this.cur.setNext();
            } else {
                this.cur = this.cur.getNext();
                this.cur.setPrevious(this.cur.getPrevious().getPrevious());
                this.cur.getPrevious().setNext(this.cur);
            }
            setSize(size() - 1);
        }
    }
    /**
     * Класс Node реализует сущность Элемент множества.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-05-28
     */
    private class Node implements Comparable<E> {
        /**
         * Объект, сохраняемый в элементе множества.
         */
        private E object;
        /**
         * Ссылка на предыдущий элемент множества.
         */
        private Node previous;
        /**
         * Ссылка на следующий элемент множества.
         */
        private Node next;
        /**
         * Конструктор.
         * @param e Объект, сохраняемый в элементе множества.
         */
        Node(E e) {
            this.object = e;
        }
        /**
         * Сравнивает два объекта пользователя.
         * @param o объект сравнения.
         * @return результат сравнения.
         */
        @Override
        public int compareTo(E o) {
            return this.object.hashCode() - o.hashCode();
        }
        /**
         * Получает объект, сохраняемый в элементе множества.
         * @return объект, сохраняемый в элементе множества.
         */
        public E getObject() {
            return this.object;
        }
        /**
         * Получает предыдущий элемент множества.
         * @return предыдущий элемент множества.
         */
        public Node getPrevious() {
            return this.previous;
        }
        /**
         * Получает следующий элемент множества.
         * @return следующий элемент множества.
         */
        public Node getNext() {
            return this.next;
        }
        /**
         * Возвращает хэш-код объекта пользователя.
         * @return хэш-код объекта пользователя.
         */
        @Override
        public int hashCode() {
            return Objects.hash(this.object);
        }
        /**
         * Устанавливает объект, сохраняемый в элементе множества.
         * @param object объект, сохраняемый в элементе множества.
         */
        public void setObject(E object) {
            this.object = object;
        }
        /**
         * Устанавливает предыдущий элемент множества.
         * @param previous предыдущий элемент множества.
         */
        public void setPrevious(Node previous) {
            this.previous = previous;
        }
        /**
         * Устанавливает предыдущий элемент множества.
         */
        public void setPrevious() {
            this.previous = null;
        }
        /**
         * Устанавливает следующий элемент множества.
         * @param next следующий элемент множества.
         */
        public void setNext(Node next) {
            this.next = next;
        }
        /**
         * Устанавливает следующий элемент множества.
         */
        public void setNext() {
            this.next = null;
        }
    }
}
