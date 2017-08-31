package ru.job4j.control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс ExecutorServiceTest иллюстрирует работу ExecutorService.
 * Используется FixedThreadPool.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-31
 */
public class ExecutorServiceTest {
    /**
     * Количество тридов.
     */
    private int threadsCount = 500;
    /**
     * Количество запросов.
     */
    private int requestsCount = 1000;
    /**
     * Размер пула потоков.
     */
    private int poolSize = 100;
    /**
     * Объект счётчика.
     */
    private ICounter counter;
    /**
     * Объект исполнителя.
     */
    private ExecutorService executor;
    /**
     * Действия перед тестами.
     */
    @Before
    public void beforeTest() {
        this.executor = Executors.newFixedThreadPool(poolSize);
        this.counter = new CounterSyncBlocks();
        for (int a = 0; a < this.threadsCount; a++) {
            this.executor.execute(new Incrementer(this.counter, this.requestsCount));
            this.executor.execute(new Decrementer(this.counter, this.requestsCount));
        }
    }
    /**
     * Иллюстрирует работу ExecutorService.
     */
    @Test
    public void showExecutorService() {
        this.executor.shutdown();
        try {
            while (!this.executor.isTerminated()) {
                Thread.currentThread().sleep(10);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertEquals(0, this.counter.getVal());
    }
}