package ru.job4j.control;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс BoundedBufferABQTest иллюстрирует работу locks.Condition
 * на примере шаблона "Producer–consumer" с использованием буфера на основе ArrayBlockingQueue.
 * Producer генерирует данные и кладёт их в буфер.
 * Сonsumer забирает данные, удаляя их из буфера.
 * Проблема состоит в следующем:
 * Producer не должен пытаться добавлить данные если буфер заполнен.
 * Сonsumer не должен пытаться удалить данные если буфер пустой.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-31
 */
public class BoundedBufferABQTest {
    /**
     * Иллюстрирует работу locks.Condition.
     */
    @Test
    public void showHowLockConditionWorks() {
        int prodsThreads = 100;
        int consThreads = 200;
        Collector collector = new Collector(prodsThreads);
        int prodsRequests = prodsThreads * consThreads;
        int[] expected = new int[prodsThreads];
        int sum = 0;
        for (int a = 1; a < prodsRequests + 1; a++) {
            sum += a;
        }
        Arrays.fill(expected, sum);
        BoundedBufferABQ<Data> buffer = new BoundedBufferABQ<>(10);
        Producer[] prods = new Producer[prodsThreads];
        Consumer[] cons = new Consumer[consThreads];
        for (int a = 0; a < prodsThreads; a++) {
            prods[a] = new Producer(buffer, prodsRequests);
            prods[a].start();
        }
        for (int a = 0; a < consThreads; a++) {
            cons[a] = new Consumer(buffer, (int) (prodsThreads * prodsRequests / consThreads), collector);
            cons[a].start();
        }
        try {
            for (int a = 0; a < prodsThreads; a++) {
                prods[a].join();
            }
            for (int a = 0; a < consThreads; a++) {
                cons[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        int[] result = collector.getCollector();
        assertArrayEquals(expected, result);
    }
}