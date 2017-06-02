package ru.job4j.set;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс SimpleArraySet реализует сущность Множество на массиве.
 *
 * @param <E> обобщённый тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-01
 */
class SimpleArraySet<E> extends SimpleAbstractSet<E> implements ISimpleSet<E> {
    /**
     * Ёмкость массива множества по умолчанию.
     */
    private final int initialCapacity = 10;
    /**
     * Массив объектов множества.
     */
    private Object[] objects;
    /**
     * Размер множества.
     */
    private int size = 0;
    /**
     * Конструктор без парамеров.
     */
    SimpleArraySet() {
        this.objects = new Object[initialCapacity];
    }
    /**
     * Конструктор с параметром, устаналивающим размер коллекции.
     * @param capacity размер коллекции.
     */
    SimpleArraySet(int capacity) {
        if (capacity < 0 || capacity > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.objects = new Object[capacity];
    }
    /**
     * Добавляет объект в множество если его там ещё нет.
     * @param e добавляемый объект.
     * @return true если объект добавлен в множество. Иначе false.
     */
    public boolean add(E e) {
        if (this.size == this.objects.length) {
            this.increaseCapacity();
        }
        boolean result = false;
        if (!this.contains(e)) {
            this.objects[this.size++] = e;
            result = true;
        }
        return result;
    }
    /**
     * Очищает множество.
     */
    public void clear() {
        this.objects = new Object[initialCapacity];
        this.size = 0;
    }
    /**
     * Проверяет содержится ли указанный объект в множестве.
     * @param o проверяемый объект.
     * @return true если объект содержится в множестве. Иначе false.
     */
    public boolean contains(Object o) {
        boolean result = false;
        Iterator iter = this.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(o)) {
                result = true;
                break;
            }
        }
        return result;
    }
    /**
     * Увеличивает размер массива объектов коллекции.
     */
    private void increaseCapacity() {
        Object[] newObjects = new Object[this.objects.length * 2];
        System.arraycopy(this.objects, 0, newObjects, 0, this.size());
        this.objects = newObjects;
    }
    /**
     * Проверяет содержится ли указанный объект в множестве.
     * @return true если объект содержится в множестве. Иначе false.
     */
    public Iterator<E> iterator() {
        return new SimpleIterator();
    }
    /**
     * Удаляет элемент из множества если таковой присутствует в нём и сдвигает элементы вычитанием 1 из их индексов.
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
                this.shift(index);
                result = true;
                break;
            }
            index++;
        }
        return result;
    }
    /**
     * Сдвигает элементы вычитанием 1 из их индексов.
     * @param index индекс элемента.
     */
    private void shift(int index) {
        System.arraycopy(this.objects, index + 1, this.objects, index, this.size() - index - 1);
        this.size--;
    }
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    public int size() {
        return this.size > Integer.MAX_VALUE ? Integer.MAX_VALUE : this.size;
    }
    /**
     * Возвращает массив элементов коллекции.
     * @return массив элементов в коллекции.
     */
    public Object[] toArray() {
        return Arrays.copyOf(this.objects, this.size());
    }
    /**
     * Класс SimpleIterator реализует сущность Итератор.
     *
     * @param <E> параметризированный тип.
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-06-01
     */
    private class SimpleIterator<E> implements Iterator<E> {
        /**
         * Текущий индекс итератора.
         */
        private int index = 0;
        /**
         * Текущий объект в итераторе.
         */
        private Object current;
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
                this.current = SimpleArraySet.this.objects[this.index++];
                return (E) this.current;
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException();
            }
        }
        /**
         * Удаляет текущий элемент из множества.
         */
        public void remove() {
            SimpleArraySet.this.remove(this.current);
            this.index--;
        }
    }
}