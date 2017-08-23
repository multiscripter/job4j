package ru.job4j.control;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс CountDownLatchTest иллюстрирует работу класса CountDownLatch.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-23
 */
public class CountDownLatchTest {
    /**
     * Иллюстрирует работу класса CountDownLatch.
     */
    @Test
    public void showCountDownLatch() {
        int threadsCount = 600;
        int requests = 1000;
        int latch = 6;
        BalancerLatch balancer = new BalancerLatch(latch);
        UpperLatch[] uppers = new UpperLatch[threadsCount];
        DownerLatch[] downers = new DownerLatch[threadsCount];
        LatchChecker lc = new LatchChecker(balancer);
        for (int a = 0; a < threadsCount; a++) {
            uppers[a] = new UpperLatch(balancer, requests);
            downers[a] = new DownerLatch(balancer, requests);
        }
        lc.start();
        for (int a = 0; a < threadsCount; a++) {
            uppers[a].start();
            downers[a].start();
        }
        try {
            for (int a = 0; a < threadsCount; a++) {
                uppers[a].join();
                downers[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertEquals(0, balancer.get());
        assertTrue(lc.isLatchOpened());
    }
}
