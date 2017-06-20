package ru.job4j.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
/**
 * Класс SimpleTree реализует сущность Простое дерево.
 *
 * @param <E> обобщённый тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-20
 */
class SimpleTree<E extends Comparable<E>> implements ISimpleTree<E> {
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
    SimpleTree() {
    }
    /**
     * Конструктор.
     * @param root корень дерева.
     */
    SimpleTree(E root) {
        this.root = new Node(null, root);
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
        Node<E> node = this.find(this.root, parent);
        if (node != null) {
            result = node.add(child);
            if (result) {
                this.size++;
            }
        }
        return result;
    }
    /**
     * Проверяет содержится ли элемент в дереве.
     * @param e элемент поиска.
     * @return true если элемент e содержится в дереве. Иначе false.
     */
    public boolean contains(E e) {
        return null != this.find(this.root, e) ? true : false;
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
        } else if (node.hasChildren()) {
            Iterator<Node<E>> iter = node.iterator();
            while (iter.hasNext()) {
                result = this.find(iter.next(), e);
                if (result != null) {
                    break;
                }
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
     * Добавляет элемент e в корень.
     * @param e элемент, добавляемый в корень.
     */
    public void setRoot(E e) {
        this.root = new Node(null, e);
        this.size = 1;
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
         * Направление итератора.
         */
        private boolean down;
        /**
         * Конструктор.
         */
        SimpleIterator() {
            if (SimpleTree.this.root != null) {
                this.cur = SimpleTree.this.root;
                this.prev = null;
                this.down = true;
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
         * Получает следующий узел дерева.
         * @param node текущий узел.
         * @return следующий узел.
         */
        private Node<E> getNext(Node<E> node) {
            Node<E> next = null;
            if (node.hasChildren() && this.down) {
                next = node.getChildren().get(0);
            } else {
                int index = node.getParent().getChildren().indexOf(node);
                try {
                    this.down = true;
                    ListIterator<Node<E>> iter = node.getParent().getChildren().listIterator(index);
                    next = iter.next();
                    next = iter.next();
                } catch (NoSuchElementException nsee) {
                    try {
                        this.down = false;
                        next = this.getNext(node.getParent());
                    } catch (NullPointerException npe) {
                        next = null;
                    }
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
            this.cur = SimpleTree.this.size() > 1 ? this.getNext(this.cur) : null;
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
            if (this.prev.equals(SimpleTree.this.root)) {
                SimpleTree.this.root = null;
            } else {
                int index = this.prev.getParent().getChildren().indexOf(this.prev);
                this.prev.getParent().getChildren().remove(index);
            }
            this.prev = null;
            this.index--;
            SimpleTree.this.setSize(SimpleTree.this.size() - 1);
        }
    }
}
