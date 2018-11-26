package ru.job4j.control;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashSet;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс JobsRefsList реализует сущность Коллекция ссылок на страницы объявлений вакансий.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-23
 */
@ThreadSafe
class JobsRefsList implements Iterable {
    /**
     * Коллекция ссылок на страницы объявлений вакансий.
     */
    private final HashSet<String> refs;
    /**
     * Объект блокировки (монитора).
     */
    private final ReentrantLock lock;
    /**
     * Конструктор без параметров.
     */
    JobsRefsList() {
        this.refs = new HashSet<>();
        this.lock = new ReentrantLock();
    }
    /**
     * Добавляет элемент в коллекцию.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в коллекцию. Иначе false.
     */
    @GuardedBy("lock")
    public boolean add(String e) {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                result = this.refs.add(e);
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Добавляет элементы в коллекцию.
     * @param c коллекция добавляемых элементов.
     * @return true если элементы добавлены в коллекцию. Иначе false.
     */
    @GuardedBy("lock")
    public boolean addAll(Collection<String> c) {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                result = this.refs.addAll(c);
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
    public Iterator<String> iterator() {
        Iterator<String> result = null;
        if (this.lock.tryLock()) {
            try {
                result = this.refs.iterator();
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Получает и удаляет элемент.
     * @param o удаляемый объект.
     * @return true если объект удалён. Иначе false.
     */
    @GuardedBy("lock")
    public boolean remove(Object o) {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                result = this.refs.remove(o);
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Получает размер коллекции.
     * @return размер коллекции.
     */
    @GuardedBy("lock")
    public int size() {
        int result = -1;
        if (this.lock.tryLock()) {
            try {
                result = this.refs.size();
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
}