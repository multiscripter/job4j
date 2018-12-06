package ru.job4j.control;

import org.junit.Test;
/**
 * Класс CounterDeadLockTest иллюстрирует проблему Deadlock.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-30
 */
public class CounterDeadLockTest {
    /**
     * Иллюстрирует проблему Deadlock.
     */
    @Test
    public void showDeadlockProblem() {
        int iterations = 100000;
        CounterDeadLock d1 = new CounterDeadLock();
        CounterDeadLock d2 = new CounterDeadLock();
        Thread t1 = new Thread(new Runnable() {
            /**
             * Переопределёный метод.
             */
            public void run() {
                for (int a = 0; a < iterations; a++) {
                    d1.increase(d2);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            /**
             * Переопределёный метод.
             */
            public void run() {
                for (int a = 0; a < iterations; a++) {
                    d2.increase(d1);
                }
            }
        });
        /* Раскомменитируйте чтобы увидеть deadlock.
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.format("%s count: %d\n", t1.getName(), d1.getVal());
            System.out.format("%s count: %d\n", t2.getName(), d2.getVal());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }*/
    }
}