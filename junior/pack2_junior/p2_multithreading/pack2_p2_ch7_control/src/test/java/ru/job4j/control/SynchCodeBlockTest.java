package ru.job4j.control;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс SynchCodeBlockTest иллюстрирует использование синхронизированных блоков кода.
 * Синхронизированные блоки кода позволяют работать с объектом некоторого класса,
 * методы которого не синхронизированы, из множетсва потоков, не опасаясь возникновения
 * ситуации "Условие гонок". Для этого нужно передать целевой объект в параметре
 * синхронизированному блоку.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-30
 */
public class SynchCodeBlockTest {
    /**
     * Иллюстрирует использование синхронизированных блоков кода.
     */
    @Test
    public void showSynchronizedCodeBlock() {
        ICounter counter = new CounterDeadLock();
        int iterations = 100000;
        Thread t1 = new Thread(new Runnable() {
            /**
             * Переопределёный метод.
             */
            public void run() {
                for (int a = 0; a < iterations; a++) {
                    synchronized (counter) {
                        int val = counter.getVal();
                        val++;
                        counter.setVal(val);
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
                    synchronized (counter) {
                        int val = counter.getVal();
                        val--;
                        counter.setVal(val);
                    }
                }
            }
        });
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertEquals(0, counter.getVal());
    }
}