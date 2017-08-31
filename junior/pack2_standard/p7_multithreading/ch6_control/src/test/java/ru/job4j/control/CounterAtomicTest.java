package ru.job4j.control;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс CounterAtomicTest тестирует класс CounterAtomic.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-31
 * @see http://docs.oracle.com/javase/tutorial/essential/concurrency/atomicvars.html
 * @see http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-frame.html
 */
public class CounterAtomicTest {
    /**
     * Количество тридов.
     */
    private int threadsCount = 500;
    /**
     * Количество запросов.
     */
    private int requestsCount = 1000;
    /**
     * Массив Инкрементирующих тридов.
     */
    private Thread[] threadsInc;
    /**
     * Массив декрементирующих тридов.
     */
    private Thread[] threadsDec;
    /**
     * Объект счётчика.
     */
    private ICounter counter;
    /**
     * Действия перед тестами.
     */
    @Before
    public void beforeTest() {
        this.counter = new CounterAtomic();
        this.threadsInc = new Thread[this.threadsCount];
        this.threadsDec = new Thread[this.threadsCount];
        for (int a = 0; a < this.threadsCount; a++) {
            this.threadsInc[a] = new Incrementer(this.counter, this.requestsCount);
            this.threadsDec[a] = new Decrementer(this.counter, this.requestsCount);
        }
    }
    /**
     * Тестирует Counter.
     */
    @Test
    public void testCounter() {
        try {
            for (int a = 0; a < this.threadsCount; a++) {
                this.threadsInc[a].start();
                this.threadsDec[a].start();
            }
            for (int a = 0; a < this.threadsCount; a++) {
                this.threadsInc[a].join();
                this.threadsDec[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertEquals(0, this.counter.getVal());
    }
}