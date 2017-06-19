package ru.job4j.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
/**
 * Класс SimpleTree реализует сущность Простое дерево.
 *
 * @param <E> обобщённый тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-19
 */
class SimpleTree<E extends Comparable<E>> implements ISimpleTree<E> {
    /**
     * Корневой элемент дерева.
     */
    private List<Node<E>> root;
    /**
     * Количество узлов дерева.
     */
    private int size = 0;
    /**
     * Конструктор без параметров.
     */
    SimpleTree() {
        this.root = new LinkedList<>();
    }
    /**
     * Конструктор.
     * @param root корень дерева.
     */
    SimpleTree(E root) {
        this.add(root);
    }
    /**
     * Добавляет элемент e в корень.
     * @param e элемент, добавляемый в корень.
     */
    public void add(E e) {
        this.root = new LinkedList<>();
        this.root.add(new Node(null, e));
        this.size = 1;
    }
    /**
     * Добавляет элемент child в элемент parent.
     * @param parent родительский элемент.
     * @param child дочерний элемент.
     * @return true если элемент child добавлен в элемент parent. Иначе false.
     */
    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Node<E> node = this.find(this.root.get(0), parent);
        if (node != null) {
            result = node.add(child);
            if (result) {
                this.size++;
            }
        }
        return result;
    }
    /**
     * Ищет узед с элементом.
     * @param node узел, в котором производится поиск.
     * @param e элемент поиска.
     * @return найденный узел или null.
     */
    private Node<E> find(Node<E> node, E e) {
        Node<E> result = null;
        if (node.compareTo(e) == 0) {
            result = node;
        }
        if (node.hasChildren()) {
            Iterator<Node<E>> iter = node.iterator();
            while (iter.hasNext()) {
                result = this.find(iter.next(), e);
            }
        }
        return result;
    }
    /**
     * Получает объект итератора.
     * @return объект итератора.
     */
    @Override
    public Iterator<E> iterator() {
        return new SimpleIterator();
    }
    /**
     * Устанавливает размер списка.
     * @param size размер списка.
     */
    private void setSize(int size) {
        this.size = size;
    }
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    public int size() {
        return this.size > Integer.MAX_VALUE ? Integer.MAX_VALUE : this.size;
    }
    /**
     * Класс Node реализует сущность Узел дерева.
     *
     * @param <E> обобщённый тип.
     */
    class Node<E> implements Comparable<E> {
        /**
         * Список дочерних узлов.
         */
        private List<Node<E>> children;
        /**
         * Родительский узел.
         */
        private Node<E> parent;
        /**
         * Значение узла.
         */
        private E value;
        /**
         * Конструктор.
         * @param parent родительский узел.
         * @param value значение узла.
         */
        Node(Node<E> parent, E value) {
            this.children = new LinkedList<>();
            this.parent = parent;
            this.value = value;
        }
        /**
         * Добавляет узел в список дочерних узлов.
         * @param e добавляемый элемент.
         * @return true если элемент добавлен. Иначе false.
         */
        public boolean add(E e) {
            return this.children.add(new Node(this, e));
        }
        /**
         * Сравнивает два узла.
         * @param o узел.
         * @return результат сравнения.
         */
        @Override
        public int compareTo(E o) {
            return this.value.hashCode() - o.hashCode();
        }
        /**
         * Проверяет равенство узлов.
         * @param o целевой объект, с которым сравнивается текущий объект.
         * @return true если объекты одинаковые. Иначе false.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Node<E> node = (Node<E>) o;
            if (!this.value.equals(node.getValue()) || !this.children.equals(node.getChildren())) {
                return false;
            }
            return true;
        }
        /**
         * Возвращает список дочерних узлов.
         * @return список дочерних узлов.
         */
        public List<Node<E>> getChildren() {
            return this.children;
        }
        /**
         * Возвращает родительский узел.
         * @return родительский узел.
         */
        public Node<E> getParent() {
            return this.parent;
        }
        /**
         * Возвращает значение узла.
         * @return значение узла.
         */
        public E getValue() {
            return this.value;
        }
        /**
         * Проверяет есть ли дочерние узлы.
         * @return true если дочерние узлы есть. Иначе false.
         */
        public boolean hasChildren() {
            return !this.children.isEmpty();
        }
        /**
         * Возвращает хэш-код узла.
         * @return хэш-код узла.
         */
        @Override
        public int hashCode() {
            return Objects.hash(this.value, this.children);
        }
        /**
         * Возвращает итератор по дочерним узлам.
         * @return итератор по дочерним узлам.
         */
        public Iterator<Node<E>> iterator() {
            return this.children.iterator();
        }
    }
    /**
     * Класс SimpleIterator реализует сущность Итератор.
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
        private Node<E> cur;
        /**
         * Конструктор.
         */
        SimpleIterator() {
            this.cur = SimpleTree.this.root.get(0);
        }
        /**
         * Проверяет существование следующего элемента.
         * @return true если следующий элемент существует, иначе false.
         */
        public boolean hasNext() {
            return this.index < size();
        }
        /**
         * Получает следующий узел дерева.
         * @param node текущий узел.
         * @return следующий узел.
         */
        private Node<E> getNext(Node<E> node) {
            Node<E> next = null;
            if (node.hasChildren()) {
                next = this.getNext(node.iterator().next());
            }
            try {
                int index = node.getParent().getChildren().indexOf(node);
                next = node.getParent().getChildren().listIterator(index).next();
            } catch (NoSuchElementException nsee) {
                try {
                    next = node.getParent();
                } catch (NullPointerException npe) {
                    throw new NoSuchElementException();
                }
            }
            return next;
        }
        /**
         * Возвращает значение следующего элемента списка.
         * @return значение следующего элемента списка.
         */
        public E next() {
            try {
                E tmp = this.cur.getValue();
                this.cur = this.getNext(this.cur);
                this.index++;
                return tmp;
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException();
            }
        }
        /**
         * Удаляет текущий элемент из списка.
         *
        public void remove() {
            this.cur.setPrevious(this.cur.getPrevious().getPrevious());
            this.cur.getPrevious().setNext(this.cur);
            SimpleLinkedPriorityQueue.this.setSize(SimpleLinkedPriorityQueue.this.size() - 1);
        }*/
    }
}
