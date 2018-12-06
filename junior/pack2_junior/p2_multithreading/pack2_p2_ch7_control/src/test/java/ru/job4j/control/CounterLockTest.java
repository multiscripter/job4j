package ru.job4j.control;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс CounterLockTest иллюстрирует решение проблемы deadlock с помощью использования
 * объектов блокировок.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-30
 */
public class CounterLockTest {
    /**
     * Иллюстрирует решение проблемы deadlock с помощью использования объектов блокировок.
     */
    @Test
    public void showSolutionDeadlockProblem() {
        int iterations = 100000;
        CounterLock counter1 = new CounterLock();
        CounterLock counter2 = new CounterLock();
        Thread t1 = new Thread(new Runnable() {
            /**
             * Переопределёный метод.
             */
            public void run() {
                for (int a = 0; a < iterations; a++) {
                    if (!counter1.increase(counter2)) {
                        a--;
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            /**
             * Переопределёный метод.
             */
            public void run() {
                for (int a = 0; a < iterations; a++) {
                    if (!counter2.increase(counter1)) {
                        a--;
                    }
                }
            }
        });
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            assertEquals(0, counter1.getVal());
            assertEquals(0, counter2.getVal());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}