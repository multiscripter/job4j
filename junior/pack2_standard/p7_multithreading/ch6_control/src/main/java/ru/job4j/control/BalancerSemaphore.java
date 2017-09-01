package ru.job4j.control;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс BalancerSemaphore реализует сущность Балансер на базе семафора.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-01
 */
@ThreadSafe
class BalancerSemaphore {
    /**
     * Баланс.
     */
    private int balance = 0;
    /**
     * Объект блокировки.
     */
    private ReentrantLock lock = new ReentrantLock();
    /**
     * Количество запросов на понижение баланса.
     */
    private int reqsDown = 0;
    /**
     * Количество запросов на повышение баланса.
     */
    private int reqsUp = 0;
    /**
     * Семафор.
     */
    private final Semaphore semafore;
    /**
     * Конструктор без параметров.
     */
    BalancerSemaphore() {
        this.semafore = new Semaphore(1);
    }
    /**
     * Конструктор.
     * @param semaforeCount значение счётчика семафора.
     */
    BalancerSemaphore(int semaforeCount) {
        this.semafore = new Semaphore(semaforeCount < 2 || semaforeCount > Integer.MAX_VALUE ? 1 : semaforeCount);
    }
    /**
     * Получает баланс.
     * @return текущий баланс.
     */
    public int get() {
        return this.balance;
    }
    /**
     * Получает количество запросов на понижение баланса.
     * @return количество запросов на понижение баланса.
     */
    public int getReqsDown() {
        return this.reqsDown;
    }
    /**
     * Получает количество запросов на повышение баланса.
     * @return количество запросов на повышение баланса.
     */
    public int getReqsUp() {
        return this.reqsUp;
    }
    /**
     * Получает семафор.
     * @return семафор.
     */
    public Semaphore getSemaphore() {
        return this.semafore;
    }
    /**
     * Повышает баланс.
     * @return true в случае успеха.
     */
    @GuardedBy("lock")
    public boolean produce() {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                this.balance++;
                this.reqsUp++;
                result = true;
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Снижает баланс.
     * @return true в случае успеха.
     */
    @GuardedBy("lock")
    public boolean reduce() {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                this.balance--;
                this.reqsDown++;
                result = true;
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
}
/**
 * Класс DownerSemaphore реализует сущность Понижатель баланса.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-01
 */
class DownerSemaphore extends Thread {
    /**
     * Объект балансера.
     */
    private BalancerSemaphore balancer;
    /**
     * Количество запросов.
     */
    private int reqs;
    /**
     * Объект семафора.
     */
    private Semaphore semafore;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     * @param reqs количество запросов к очереди.
     */
    DownerSemaphore(BalancerSemaphore balancer, int reqs) {
        this.balancer = balancer;
        this.reqs = reqs;
        this.semafore = balancer.getSemaphore();
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            this.semafore.acquire();
            while (this.reqs > 0) {
                if (this.balancer.reduce()) {
                    this.reqs--;
                } else {
                    this.sleep(1);
                }
            }
            this.semafore.release();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
/**
 * Класс UpperSemaphore реализует сущность Повышатель баланса.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-01
 */
class UpperSemaphore extends Thread {
    /**
     * Объект балансера.
     */
    private BalancerSemaphore balancer;
    /**
     * Количество запросов.
     */
    private int reqs;
    /**
     * Объект семафора.
     */
    private Semaphore semafore;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     * @param reqs количество запросов к очереди.
     */
    UpperSemaphore(BalancerSemaphore balancer, int reqs) {
        this.balancer = balancer;
        this.reqs = reqs;
        this.semafore = balancer.getSemaphore();
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            this.semafore.acquire();
            while (this.reqs > 0) {
                if (this.balancer.produce()) {
                    this.reqs--;
                } else {
                    this.sleep(1);
                }
            }
            this.semafore.release();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
