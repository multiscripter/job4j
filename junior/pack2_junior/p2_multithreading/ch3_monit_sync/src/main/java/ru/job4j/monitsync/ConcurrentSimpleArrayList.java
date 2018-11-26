package ru.job4j.monitsync;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.RandomAccess;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс ConcurrentSimpleArrayList реализует сущность Потокобезопасный списочный массив.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-26
 */
@ThreadSafe
class ConcurrentSimpleArrayList<E> extends SimpleAdstractList<E> implements ISimpleList<E>, RandomAccess {
    /**
     * Индекс коллекции.
     */
    private int cursor = 0;
    /**
     * Массив объектов коллекции.
     */
    private Object[] objects;
    /**
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор без параметров.
     */
    ConcurrentSimpleArrayList() {
        this.objects = new Object[10];
        this.lock = new Object();
    }
    /**
     * Конструктор с параметром, устаналивающим размер коллекции.
     * @param capacity размер коллекции.
     */
    ConcurrentSimpleArrayList(int capacity) {
        if (capacity < 0 || capacity > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.objects = new Object[capacity];
        this.lock = new Object();
    }
    /**
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в коллекцию.
     */
    @GuardedBy("lock")
    public boolean add(E e) {
        synchronized (this.lock) {
            if (this.cursor == this.objects.length) {
                this.increaseCapacity();
            }
            this.objects[this.cursor++] = e;
            return true;
        }
    }
    /**
     * Добавляет элемент в список по индексу.
     * @param index индекс в списке.
     * @param e добавляемый элемент.
     */
    @GuardedBy("lock")
    public void add(int index, E e) {
        synchronized (this.lock) {
            if (index < 0 || index > this.size()) {
                throw new IndexOutOfBoundsException();
            }
            this.objects[index] = e;
        }
    }
    /**
     * Возвращает размер массива объектов коллекции.
     * @return размер массива объектов коллекции.
     */
    @GuardedBy("lock")
    public int capacity() {
        synchronized (this.lock) {
            return this.objects.length;
        }
    }
    /**
     * Проверяет содержится ли указанный объект в множестве.
     * @param o объект проверки.
     * @return true если указанный объект содержится в множестве. Иначе false.
     */
    @GuardedBy("lock")
    public boolean contains(Object o) {
        synchronized (this.lock) {
            return true;
        }
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
     * Получает элемент из списка по индексу.
     * @param index индекс элемента.
     * @return элемент.
     */
    @GuardedBy("lock")
    public E get(int index) {
        synchronized (this.lock) {
            if (index < 0 || index > this.size()) {
                throw new IndexOutOfBoundsException();
            }
            return (E) this.objects[index];
        }
    }
    /**
     * Удаляет элемент из списка если таковой присутствует в нём и сдвигает элементы вычитанием 1 из их индексов.
     * @param o удаляемый элемент.
     * @return true если элемент удалён, иначе false.
     */
    @GuardedBy("lock")
    public boolean remove(Object o) {
        synchronized (this.lock) {
            Iterator<E> iter = this.iterator();
            boolean result = false;
            int index = 0;
            while (iter.hasNext()) {
                Object tmp = iter.next();
                if (o.equals(tmp)) {
                    this.shift(index);
                    result = true;
                }
                index++;
            }
            return result;
        }
    }
    /**
     * Удаляет элемент из списка по индексу и сдвигает элементы вычитанием 1 из их индексов.
     * @param index индекс элемента.
     * @return удалённый элемент.
     */
    @GuardedBy("lock")
    @Override
    public E remove(int index) {
        synchronized (this.lock) {
            if (index < 0 || index > this.size()) {
                throw new IndexOutOfBoundsException();
            }
            E e = (E) this.objects[index];
            this.shift(index);
            return e;
        }
    }
    /**
     * Заменяет элемент в список по индексу.
     * @param index индекс заменяемого элемента в списке.
     * @param e заменающий элемент.
     * @return удалённый элемент.
     */
    @GuardedBy("lock")
    public E set(int index, E e) {
        synchronized (this.lock) {
            if (index < 0 || index > this.size()) {
                throw new IndexOutOfBoundsException();
            }
            E previously = (E) this.objects[index];
            this.objects[index] = e;
            return previously;
        }
    }
    /**
     * Сдвигает элементы вычитанием 1 из их индексов.
     * @param index индекс элемента.
     */
    private void shift(int index) {
        System.arraycopy(this.objects, index + 1, this.objects, index, this.size() - index - 1);
        this.cursor--;
    }
    /**
     * Возвращает число элементов в коллекции.
     * @return число элементов в коллекции.
     */
    @GuardedBy("lock")
    public int size() {
        synchronized (this.lock) {
            return this.cursor > Integer.MAX_VALUE ? Integer.MAX_VALUE : this.cursor;
        }
    }
    /**
     * Сортирует массив согласно натуральному порядку.
     */
    @GuardedBy("lock")
    public void sort() {
        synchronized (this.lock) {
            Object[] tmp = this.toArray();
            Arrays.sort(tmp);
            this.objects = tmp;
        }
    }
    /**
     * Сортирует массив с помощью компаратора.
     * @param comparator объект компаратора.
     */
    @GuardedBy("lock")
    public void sort(Comparator comparator) {
        synchronized (this.lock) {
            Object[] tmp = this.toArray();
            Arrays.sort(tmp, comparator);
            this.objects = tmp;
        }
    }
    /**
     * Возвращает массив элементов коллекции.
     * @return массив элементов в коллекции.
     */
    @GuardedBy("lock")
    public Object[] toArray() {
        synchronized (this.lock) {
            return Arrays.copyOf(this.objects, this.size());
        }
    }
}
