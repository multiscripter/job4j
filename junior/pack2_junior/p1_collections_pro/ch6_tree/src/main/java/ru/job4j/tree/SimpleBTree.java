package ru.job4j.tree;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
/**
 * Класс SimpleBTree реализует сущность Простое бинарное дерево.
 *
 * @param <E> обобщённый тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-20
 */
class SimpleBTree<E extends Comparable<E>> implements ISimpleTree<E> {
    /**
     * Корневой элемент дерева.
     */
    private Node<E> root;
    /**
     * Количество узлов дерева.
     */
    private int size = 0;
    /**
     * Конструктор без параметров.
     */
    SimpleBTree() {
    }
    /**
     * Конструктор.
     * @param e корневой элемент дерева.
     */
    SimpleBTree(E e) {
        this.root = new Node(null, e);
        this.size = 1;
    }
    /**
     * Добавляет элемент в дерево.
     * @param e добавляемый элемент.
     * @return true если элемент e добавлен. Иначе false.
     */
    public boolean add(E e) {
        boolean result = false;
        if (this.root == null) {
            this.root = new Node(null, e);
            result = true;
            this.size++;
        } else {
            Node<E> node = this.find(this.root, e);
            int cmp = node.compareTo(e);
            if (cmp > 0) {
                node.setLeft(e);
                result = true;
                this.size++;
            } else if (cmp < 0) {
                node.setRight(e);
                result = true;
                this.size++;
            }
        }
        return result;
    }
    /**
     * Добавляет элементы в дерево.
     * @param c коллекция добавляемых элементов.
     */
    public void addAll(Collection<? extends E> c) {
        Iterator iter = c.iterator();
        while (iter.hasNext()) {
            this.add((E) iter.next());
        }
    }
    /**
     * Добавляет элемент child в элемент parent.
     * @param parent родительский элемент.
     * @param child дочерний элемент.
     * @return false.
     */
    @Override
    public boolean add(E parent, E child) {
        return false;
    }
    /**
     * Проверяет содержится ли элемент в дереве.
     * @param e элемент поиска.
     * @return true если элемент e содержится в дереве. Иначе false.
     */
    public boolean contains(E e) {
        return this.size > 0 && this.find(this.root, e).compareTo(e) == 0 ? true : false;
    }
    /**
     * Ищет узел с элементом.
     * @param node узел, в котором производится поиск.
     * @param e элемент поиска.
     * @return найденный узел или null.
     */
    private Node<E> find(Node<E> node, E e) {
        Node<E> result = node;
        int cmp = node.compareTo(e);
        if (cmp > 0 && node.hasLeft()) {
            result = this.find(node.getLeft(), e);
        } else if (cmp < 0 && node.hasRight()) {
            result = this.find(node.getRight(), e);
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
     * Переворачивает дерево. Алгоритм Depth-first search.
     */
    public void mirrorDFS() {
        if (this.root == null || (!this.root.hasLeft() && !this.root.hasRight())) {
            return;
        }
        Node<E> cur = this.root;
        Node<E> left = null;
        Node<E> right = null;
        SimpleIterator iter = new SimpleIterator();
        while (cur != null) {
            if (cur.hasLeft() || cur.hasRight()) {
                if (cur.hasLeft()) {
                    left = cur.getLeft();
                }
                if (cur.hasRight()) {
                    right = cur.getRight();
                }
                cur.setLeft(right);
                cur.setRight(left);
                left = null;
                right = null;
            }
            cur = iter.getNext(cur);
        }
    }
    /**
     * Переворачивает дерево. Алгоритм breadth-first search.
     */
    public void mirrorBFS() {
        if (this.root == null || (!this.root.hasLeft() && !this.root.hasRight())) {
            return;
        }
        Node<E> cur = null;
        Node<E> left = null;
        Node<E> right = null;
        LinkedList<Node<E>> q = new LinkedList<>();
        q.add(this.root);
        while (!q.isEmpty()) {
            cur = q.pollFirst();
            if (cur == null) {
                break;
            }
            if (cur.hasLeft() || cur.hasRight()) {
                if (cur.hasLeft()) {
                    left = cur.getLeft();
                    q.add(left);
                }
                if (cur.hasRight()) {
                    right = cur.getRight();
                    q.add(right);
                }
                cur.setLeft(right);
                cur.setRight(left);
                left = null;
                right = null;
            }
        }
    }
    /**
     * Удаляет элемент из дерева.
     * @param e удаляемый элемент.
     * @return true если элемент e удалён из дерева. Иначе false.
     */
    public boolean remove(E e) {
        boolean result = false;
        Node<E> node = this.find(this.root, e);
        if (node.getValue().equals(e)) {
            Node<E> parent = node.getParent();
            // Узел без потомков.
            if (!node.hasLeft() && !node.hasRight()) {
                if (parent.hasLeft() && parent.getLeft().equals(node)) {
                    parent.removeLeft();
                } else {
                    parent.removeRight();
                }
            // Узел имеет одного потомка.
            } else if (!node.hasLeft() || !node.hasRight()) {
                Node<E> child = node.hasLeft() ? node.getLeft() : node.getRight();
                if (parent.hasLeft() && parent.getLeft().equals(node)) {
                    parent.setLeft(child);
                } else {
                    parent.setRight(child);
                }
                child.setParent(node);
            // Узел имеет двух потомков.
            } else {
                Node<E> child = node.getRight();
                while (child.hasLeft()) {
                    child = child.getLeft();
                }
                if (child.getParent().hasLeft() && child.getParent().getLeft().equals(child)) {
                    child.getParent().removeLeft();
                } else if (child.getParent().hasRight() && child.getParent().getRight().equals(child)) {
                    child.getParent().removeRight();
                }
                child.setLeft(node.getLeft());
                node.getLeft().setParent(child);
                if (node.hasRight() && !node.getRight().equals(child)) {
                    if (child.hasRight()) {
                        Node<E> right = child.getRight();
                        while (right.hasRight()) {
                            right = right.getRight();
                        }
                        right.setRight(node.getRight());
                        node.getRight().setParent(right);
                    } else {
                        child.setRight(node.getRight());
                        node.getRight().setParent(child);
                    }
                }
                child.setParent(parent);
                if (parent.hasLeft() && parent.getLeft().equals(node)) {
                    parent.setLeft(child);
                } else {
                    parent.setRight(child);
                }
            }
            this.size--;
            result = true;
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
     * Класс Node реализует сущность Узел дерева.
     *
     * @param <E> обобщённый тип.
     */
    class Node<E> implements Comparable<E> {
        /**
         * Родительский узел.
         */
        private Node<E> parent;
        /**
         * Левый дочерний узел.
         */
        private Node<E> left = null;
        /**
         * Правый дочерний узел.
         */
        private Node<E> right = null;
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
            this.parent = parent;
            this.value = value;
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
            if (!this.value.equals(node.getValue())) {
                return false;
            }
            return true;
        }
        /**
         * Возвращает левый узел.
         * @return левый узел.
         */
        public Node<E> getLeft() {
            return this.left;
        }
        /**
         * Возвращает родительский узел.
         * @return родительский узел.
         */
        public Node<E> getParent() {
            return this.parent;
        }
        /**
         * Возвращает правый узел.
         * @return правый узел.
         */
        public Node<E> getRight() {
            return this.right;
        }
        /**
         * Возвращает значение узла.
         * @return значение узла.
         */
        public E getValue() {
            return this.value;
        }
        /**
         * Возвращает хэш-код узла.
         * @return хэш-код узла.
         */
        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }
        /**
         * Проверяет существование левого узла.
         * @return true если левый узел есть. Иначе false.
         */
        public boolean hasLeft() {
            return this.left != null;
        }
        /**
         * Проверяет существование правого узла.
         * @return true если правый узел есть. Иначе false.
         */
        public boolean hasRight() {
            return this.right != null;
        }
        /**
         * Удаляет значение левого узела.
         */
        public void removeLeft() {
            this.left = null;
        }
        /**
         * Удаляет значение правого узела.
         */
        public void removeRight() {
            this.right = null;
        }
        /**
         * Устанавливает значение в левый узел.
         * @param e значение, добавляемое в левый узел.
         */
        public void setLeft(E e) {
            if (e == null) {
                this.left = null;
            } else {
                this.left = new Node(this, e);
            }
        }
        /**
         * Устанавливает узел в левый узел.
         * @param n добавляемый узел.
         */
        public void setLeft(Node<E> n) {
            this.left = n;
        }
        /**
         * Устанавливает родительский узел.
         * @param n добавляемый узел.
         */
        public void setParent(Node<E> n) {
            this.parent = n;
        }
        /**
         * Устанавливает значение в правый узел.
         * @param e значение, добавляемое в правый узел.
         */
        public void setRight(E e) {
            if (e == null) {
                this.right = null;
            } else {
                this.right = new Node(this, e);
            }
        }
        /**
         * Устанавливает узел в левый узел.
         * @param n добавляемый узел.
         */
        private void setRight(Node<E> n) {
            this.right = n;
        }
    }
    /**
     * Класс SimpleIterator реализует сущность Итератор.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-06-20
     */
    private class SimpleIterator implements Iterator<E> {
        /**
         * Текущий индекс итератора.
         */
        private int index = 0;
        /**
         * Текущий узел, полученный итератором.
         */
        private Node<E> cur;
        /**
         * Предпоследний узел, полученный итератором.
         */
        private Node<E> prev;
        /**
         * Посещённый левый потомок.
         */
        private Node<E> left;
        /**
         * Посещённый правый потомок.
         */
        private Node<E> right;
        /**
         * Направление движения итератора.
         */
        private boolean down = true;
        /**
         * Конструктор без параметров.
         */
        SimpleIterator() {
            if (SimpleBTree.this.root != null) {
                this.cur = SimpleBTree.this.root;
                this.prev = null;
                this.left = null;
                this.right = null;
            }
        }
        /**
         * Конструктор.
         * @param e первый элемент итератора.
         */
        SimpleIterator(E e) {
            if (SimpleBTree.this.root != null) {
                this.cur = SimpleBTree.this.find(SimpleBTree.this.root, e);
                this.prev = null;
                this.left = null;
                this.right = null;
            }
        }
        /**
         * Проверяет существование следующего элемента.
         * @return true если следующий элемент существует, иначе false.
         */
        public boolean hasNext() {
            return this.cur != null;
        }
        /**
         * Получает следующий узел дерева.
         * @param node текущий узел.
         * @return следующий узел.
         */
        public Node<E> getNext(Node<E> node) {
            Node<E> next = null;
            if (this.down && node.hasLeft() && !node.getLeft().equals(this.left)) {
                next = node.getLeft();
            } else if (node.hasRight() && !node.getRight().equals(this.right)) {
                this.down = true;
                next = node.getRight();
            } else {
                this.down = false;
                next = null;
                if (!node.equals(SimpleBTree.this.root)) {
                    Node<E> parent = node.getParent();
                    if (parent.hasLeft() && parent.getLeft().equals(node)) {
                        this.left = node;
                    } else if (parent.hasRight() && parent.getRight().equals(node)) {
                        this.right = node;
                    }
                    next = this.getNext(parent);
                }
            }
            return next;
        }
        /**
         * Возвращает значение следующего элемента списка.
         * @return значение следующего элемента списка.
         */
        public E next() {
            if (this.cur == null) {
                throw new NoSuchElementException();
            }
            E tmp = this.cur.getValue();
            this.prev = this.cur;
            this.cur = SimpleBTree.this.size() > 1 ? this.getNext(this.cur) : null;
            this.index++;
            return tmp;
        }
        /**
         * Удаляет текущий элемент из списка.
         */
        public void remove() {
            if (this.prev == null) {
                throw new IllegalStateException();
            }
            if (this.prev.equals(SimpleBTree.this.root)) {
                SimpleBTree.this.root = null;
                SimpleBTree.this.size = 0;
                this.cur = null;
                this.index = 0;
            } else {
                SimpleBTree.this.remove(this.prev.getValue());
                this.index--;
            }
            this.prev = null;
        }
    }
}
