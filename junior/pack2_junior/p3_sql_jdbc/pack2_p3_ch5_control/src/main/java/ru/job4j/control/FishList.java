package ru.job4j.control;

import java.util.concurrent.locks.ReentrantLock;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс FishList реализует сущность Коллекция рыб.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-10
 */
@ThreadSafe
class FishList implements Iterable {
    /**
     * Ёмкость списка.
     */
    private final int capacity;
    /**
     * Список.
     */
    private final LinkedList<Fish> list;
    /**
     * Объект блокировки (монитора).
     */
    private final ReentrantLock lock;
    /**
     * Конструктор.
     * @param capacity ёмкость списка.
     */
    FishList(int capacity) {
        this.capacity = capacity;
        this.list = new LinkedList<>();
        this.lock = new ReentrantLock();
    }
    /**
     * Добавляет элемент в конец списка.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в список, иначе false.
     */
    @GuardedBy("lock")
    public boolean add(Fish e) {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                if (this.list.size() == this.capacity) {
                    throw new FishListIsFullException();
                }
                result = this.list.add(e);
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Добавляет элементы из коллекции в конец списка.
     * @param list коллекция добавляемых элементов.
     * @return список добавленных элементов. Иначе false.
     */
    @GuardedBy("lock")
    public List<Fish> addAll(List<Fish> list) {
        List<Fish> result = null;
        if (this.lock.tryLock()) {
            try {
                if (this.list.size() == this.capacity) {
                    throw new FishListIsFullException();
                }
                int empty = this.capacity - this.list.size();
                if (empty > 0) {
                    if (empty < list.size()) {
                        list = list.subList(0, empty);
                    }
                    //System.out.println("-----------------empty; " + empty);
                    //System.out.println("-----------------list.size(); " + list.size());
                    if (this.list.addAll(list)) {
                        result = list;
                    }
                    //System.out.println("-----------------this.list.size(); " + this.list.size());
                }
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Получает элемент по индексу.
     * @param index индекс.
     * @return элемент.
     */
    @GuardedBy("lock")
    public Fish get(int index) {
        Fish result = null;
        if (this.lock.tryLock()) {
            try {
                result = this.list.get(index);
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Получает список элементов.
     * @return список элементов.
     */
    @GuardedBy("lock")
    public List<Fish> getList() {
        List<Fish> result = null;
        if (this.lock.tryLock()) {
            try {
                result = this.list;
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Возвращает итератор.
     * @return итератор.
     */
    @GuardedBy("lock")
    public Iterator<Fish> iterator() {
        Iterator<Fish> result = null;
        if (this.lock.tryLock()) {
            try {
                result = this.list.iterator();
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Получает и удаляет элемент по индексу.
     * @param o удаляемый объект.
     * @return true если объект удалён. Иначе false.
     */
    @GuardedBy("lock")
    public boolean remove(Object o) {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                result = this.list.remove(o);
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Получает размер списка.
     * @return размер списка.
     */
    @GuardedBy("lock")
    public int size() {
        int result = -1;
        if (this.lock.tryLock()) {
            try {
                return this.list.size();
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
}