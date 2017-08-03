package ru.job4j.waitnotify;

import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс Balancer реализует сущность Балансер.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-03
 */
@ThreadSafe
class Balancer {
    /**
     * Баланс.
     */
    private int balance;
    /**
     * Объект блокировки.
     */
    private ReentrantLock lock;
    /**
     * Конструктор без параметров.
     */
    Balancer() {
        this.balance = 0;
        this.lock = new ReentrantLock();
    }
    /**
     * Получает баланс.
     * @return текущий баланс.
     */
    public int get() {
        return this.balance;
    }
    /**
     * Повышает баланс.
     * @return true если успех. Иначе false.
     */
    @GuardedBy("lock")
    public boolean produce() {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                this.balance = this.get() + 1;
                result = true;
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Снижает баланс.
     * @return true если успех. Иначе false.
     */
    @GuardedBy("lock")
    public boolean reduce() {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                this.balance = this.get() - 1;
                result = true;
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
}
/**
 * Класс Bull реализует сущность Повышатель баланса.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-03
 */
class Bull extends Thread {
    /**
     * Объект балансера.
     */
    private Balancer balancer;
    /**
     * Количество запросов.
     */
    private int reqs;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     * @param reqs количество запросов к очереди.
     */
    Bull(Balancer balancer, int reqs) {
        this.balancer = balancer;
        this.reqs = reqs;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            while (this.reqs > 0) {
                if (this.balancer.produce()) {
                    this.reqs--;
                } else {
                    this.sleep(1);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.interrupt();
    }
}
/**
 * Класс Bear реализует сущность Понижатель баланса.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-03
 */
class Bear extends Thread {
    /**
     * Объект балансера.
     */
    private Balancer balancer;
    /**
     * Количество запросов.
     */
    private int reqs;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     * @param reqs количество запросов к очереди.
     */
    Bear(Balancer balancer, int reqs) {
        this.balancer = balancer;
        this.reqs = reqs;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            while (this.reqs > 0) {
                if (this.balancer.reduce()) {
                    this.reqs--;
                } else {
                    this.sleep(1);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.interrupt();
    }
}
