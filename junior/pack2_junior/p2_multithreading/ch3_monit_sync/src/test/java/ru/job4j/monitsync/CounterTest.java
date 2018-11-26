package ru.job4j.monitsync;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс CounterTest тестирует класс Counter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-25
 */
public class CounterTest {
    /**
     * Массив тридов.
     */
    private Thread[] threads;
    /**
     * Объект счётчика.
     */
    private Counter counter;
    /**
     * Действия перед тестами.
     */
    @Before
    public void beforeTest() {
        this.threads = new Thread[1000];
        this.counter = new Counter();
        for (int a = 0; a < this.threads.length; a++) {
            this.threads[a] = new Incrementer(this.counter, 1000);
        }
    }
    /**
     * Тестирует Counter.
     */
    @Test
    public void testCounter() {
        try {
            for (int a = 0; a < this.threads.length; a++) {
                this.threads[a].start();
            }
            for (int a = 0; a < this.threads.length; a++) {
                this.threads[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertEquals(1000000, this.counter.getVal());
    }
}