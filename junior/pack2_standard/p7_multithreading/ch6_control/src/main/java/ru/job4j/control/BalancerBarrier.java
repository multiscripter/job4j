package ru.job4j.control;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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
class BalancerBarrier {
    /**
     * Баланс.
     */
    private int balance = 0;
    /**
     * Объект блокировки.
     */
    private ReentrantLock lock = new ReentrantLock();
    /**
     * Объект барьера.
     */
    private CyclicBarrier barrier;
    /**
     * Объект счётчика барьера.
     */
    private BarrierCounter bc;
    /**
     * Конструктор без параметров.
     */
    BalancerBarrier() {
        this.bc = new BarrierCounter();
        this.barrier = new CyclicBarrier(1, this.bc);
    }
    /**
     * Конструктор.
     * @param barrierCount значение счётчика барьера.
     */
    BalancerBarrier(int barrierCount) {
        this.bc = new BarrierCounter();
        this.barrier = new CyclicBarrier(barrierCount < 2 ? 1 : barrierCount, this.bc);
    }
    /**
     * Получает баланс.
     * @return текущий баланс.
     */
    public int get() {
        return this.balance;
    }
    /**
     * Получает барьер.
     * @return барьер.
     */
    public CyclicBarrier getBarrier() {
        return this.barrier;
    }
    /**
     * Получает счётчик барьера.
     * @return счётчик барьера.
     */
    public BarrierCounter getBarrierCounter() {
        return this.bc;
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
                this.balance++;
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
                this.balance--;
                result = true;
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
}
/**
 * Класс BarrierCounter реализует сущность Счётчик барьера.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-23
 */
class BarrierCounter extends Thread {
    /**
     * Количество срабатываний барьера.
     */
    private int count = 0;
    /**
     * Возвращает счёт барьера.
     * @return счёт барьера.
     */
    public int getCount() {
        return this.count;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        this.count++;
    }
}
/**
 * Класс DownerBarrier реализует сущность Понижатель баланса.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-23
 */
class DownerBarrier extends Thread {
    /**
     * Объект балансера.
     */
    private BalancerBarrier balancer;
    /**
     * Количество запросов.
     */
    private int reqs;
    /**
     * Объект барьера.
     */
    private CyclicBarrier barrier;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     * @param reqs количество запросов к очереди.
     */
    DownerBarrier(BalancerBarrier balancer, int reqs) {
        this.balancer = balancer;
        this.reqs = reqs;
        this.barrier = balancer.getBarrier();
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            this.barrier.await();
            while (this.reqs > 0) {
                if (this.balancer.reduce()) {
                    this.reqs--;
                } else {
                    this.sleep(1);
                }
            }
        } catch (InterruptedException | BrokenBarrierException ex) {
            ex.printStackTrace();
        }
    }
}
/**
 * Класс UpperBarrier реализует сущность Повышатель баланса.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-23
 */
class UpperBarrier extends Thread {
    /**
     * Объект балансера.
     */
    private BalancerBarrier balancer;
    /**
     * Количество запросов.
     */
    private int reqs;
    /**
     * Объект барьера.
     */
    private CyclicBarrier barrier;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     * @param reqs количество запросов к очереди.
     */
    UpperBarrier(BalancerBarrier balancer, int reqs) {
        this.balancer = balancer;
        this.reqs = reqs;
        this.barrier = balancer.getBarrier();
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            this.barrier.await();
            while (this.reqs > 0) {
                if (this.balancer.produce()) {
                    this.reqs--;
                } else {
                    this.sleep(1);
                }
            }
            this.interrupt();
        } catch (InterruptedException | BrokenBarrierException ex) {
            ex.printStackTrace();
        }
    }
}
