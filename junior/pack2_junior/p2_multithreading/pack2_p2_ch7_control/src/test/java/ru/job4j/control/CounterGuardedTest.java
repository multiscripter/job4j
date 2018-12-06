package ru.job4j.control;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс CounterGuardedTest иллюстрирует работу Охранных блоков.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-30
 */
public class CounterGuardedTest {
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
        this.counter = new CounterGuarded();
        this.threadsInc = new Thread[this.threadsCount];
        this.threadsDec = new Thread[this.threadsCount];
        for (int a = 0; a < this.threadsCount; a++) {
            this.threadsInc[a] = new Incrementer(this.counter, this.requestsCount);
            this.threadsDec[a] = new Decrementer(this.counter, this.requestsCount);
        }
    }
    /**
     * Иллюстрирует работу Охранных блоков.
     */
    @Test
    public void showGuardedBlockUsage() {
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