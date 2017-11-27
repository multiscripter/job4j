package ru.job4j.control;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashSet;
import java.util.Iterator;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс JobOfferList реализует сущность Коллекция ссылок на страницы объявлений вакансий.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-23
 */
@ThreadSafe
class JobOfferList implements Iterable {
    /**
     * Коллекция ссылок на страницы объявлений вакансий.
     */
    private final HashSet<JobOffer> offers;
    /**
     * Объект блокировки (монитора).
     */
    private final ReentrantLock lock;
    /**
     * Конструктор без параметров.
     */
    JobOfferList() {
        this.offers = new HashSet<>();
        this.lock = new ReentrantLock();
    }
    /**
     * Добавляет элемент в коллекцию.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в коллекцию. Иначе false.
     */
    @GuardedBy("lock")
    public boolean add(JobOffer e) {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                result = this.offers.add(e);
                result = true;
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
    public boolean addAll(Collection<JobOffer> c) {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                result = this.offers.addAll(c);
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
    public Iterator<JobOffer> iterator() {
        Iterator<JobOffer> result = null;
        if (this.lock.tryLock()) {
            try {
                result = this.offers.iterator();
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
                result = this.offers.remove(o);
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Удаляет из обоих коллекций одинаковые элементы.
     * @param jol коллекция объявлений.
     */
    @GuardedBy("lock")
    public void removeEquals(JobOfferList jol) {
        if (this.lock.tryLock()) {
            try {
                Iterator<JobOffer> iter = jol.iterator();
                while (iter.hasNext()) {
                    JobOffer cur = iter.next();
                    if (this.offers.remove(cur)) {
                        iter.remove();
                    }
                }
            } finally {
                this.lock.unlock();
            }
        }
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
                result = this.offers.size();
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
}