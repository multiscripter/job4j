package ru.job4j.control;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс BalancerSemaphoreTest иллюстрирует работу класса java.util.concurrent.Semaphore.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-01
 */
public class BalancerSemaphoreTest {
    /**
     * Иллюстрирует работу класса Semaphore.
     */
    @Test
    public void showSemaphore() {
        int threads = 500;
        int requests = 1000;
        int semaforeCount = 5;
        BalancerSemaphore balancer = new BalancerSemaphore(semaforeCount);
        UpperSemaphore[] uppers = new UpperSemaphore[threads];
        DownerSemaphore[] downers = new DownerSemaphore[threads];
        for (int a = 0; a < threads; a++) {
            uppers[a] = new UpperSemaphore(balancer, requests);
            downers[a] = new DownerSemaphore(balancer, requests);
        }
        for (int a = 0; a < threads; a++) {
            uppers[a].start();
            downers[a].start();
        }
        try {
            for (int a = 0; a < threads; a++) {
                uppers[a].join();
                downers[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertEquals(0, balancer.get());
        assertEquals(threads * requests, balancer.getReqsDown());
        assertEquals(threads * requests, balancer.getReqsUp());
    }
}