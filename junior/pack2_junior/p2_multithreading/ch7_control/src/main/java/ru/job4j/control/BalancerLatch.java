package ru.job4j.control;

import java.util.concurrent.CountDownLatch;
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
class BalancerLatch {
    /**
     * Баланс.
     */
    private int balance = 0;
    /**
     * Объект блокировки.
     */
    private ReentrantLock lock = new ReentrantLock();
    /**
     * Объект защёлки. Защёлка срабатывает только 1 раз.
     */
    private CountDownLatch latch;
    /**
     * Конструктор без параметров.
     */
    BalancerLatch() {
        this.latch = new CountDownLatch(1);
    }
    /**
     * Конструктор без параметров.
     * @param latchCount значение счётчика защёлки.
     */
    BalancerLatch(int latchCount) {
        this.latch = new CountDownLatch(latchCount < 2 ? 1 : latchCount);
    }
    /**
     * Получает баланс.
     * @return текущий баланс.
     */
    public int get() {
        return this.balance;
    }
    /**
     * Получает защёлку.
     * @return защёлка.
     */
    public CountDownLatch getLatch() {
        return this.latch;
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
 * Класс LatchChecker реализует сущность Проверятель защёлки.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-23
 */
class LatchChecker extends Thread {
    /**
     * Количество срабатываний защёлки.
     */
    private boolean latchOpened = false;
    /**
     * Объект защёлки.
     */
    private CountDownLatch latch;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     */
    LatchChecker(BalancerLatch balancer) {
        this.latch = balancer.getLatch();
    }
    /**
     * Проверяет открыта ли защёлка.
     * @return true если защёлка открыта. Иначе false.
     */
    public boolean isLatchOpened() {
        return this.latchOpened;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            this.latch.countDown();
            this.latch.await();
            this.latchOpened = true;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
/**
 * Класс DownerLatch реализует сущность Понижатель баланса.
 * Использует CountDownLatch.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-23
 */
class DownerLatch extends Thread {
    /**
     * Объект балансера.
     */
    private BalancerLatch balancer;
    /**
     * Количество запросов.
     */
    private int reqs;
    /**
     * Объект защёлки.
     */
    private CountDownLatch latch;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     * @param reqs количество запросов к очереди.
     */
    DownerLatch(BalancerLatch balancer, int reqs) {
        this.balancer = balancer;
        this.reqs = reqs;
        this.latch = balancer.getLatch();
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            while (this.reqs > 0) {
                this.latch.countDown();
                this.latch.await();
                if (this.balancer.reduce()) {
                    this.reqs--;
                } else {
                    this.sleep(1);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
/**
 * Класс UpperLatch реализует сущность Повышатель баланса.
 * Использует CountDownLatch.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-23
 */
class UpperLatch extends Thread {
    /**
     * Объект балансера.
     */
    private BalancerLatch balancer;
    /**
     * Количество запросов.
     */
    private int reqs;
    /**
     * Объект защёлки.
     */
    private CountDownLatch latch;
    /**
     * Конструктор.
     * @param balancer объект балансера.
     * @param reqs количество запросов к очереди.
     */
    UpperLatch(BalancerLatch balancer, int reqs) {
        this.balancer = balancer;
        this.reqs = reqs;
        this.latch = balancer.getLatch();
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            while (this.reqs > 0) {
                this.latch.countDown();
                this.latch.await();
                if (this.balancer.produce()) {
                    this.reqs--;
                } else {
                    this.sleep(1);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
