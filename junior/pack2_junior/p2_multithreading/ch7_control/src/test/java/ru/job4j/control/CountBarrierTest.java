package ru.job4j.control;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс CountBarrierTest иллюстрирует работу класса CyclicBarrier.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-23
 */
public class CountBarrierTest {
    /**
     * Иллюстрирует работу класса CyclicBarrier.
     */
    @Test
    public void showCyclicBarrier() {
        int threads = 500;
        int requests = 1000;
        int barrier = 5;
        BalancerBarrier balancer = new BalancerBarrier(barrier);
        BarrierCounter bc = balancer.getBarrierCounter();
        UpperBarrier[] uppers = new UpperBarrier[threads];
        DownerBarrier[] downers = new DownerBarrier[threads];
        for (int a = 0; a < threads; a++) {
            uppers[a] = new UpperBarrier(balancer, requests);
            downers[a] = new DownerBarrier(balancer, requests);
        }
        for (int a = 0; a < threads; a++) {
            uppers[a].start();
            downers[a].start();
        }
        try {
            bc.join();
            for (int a = 0; a < threads; a++) {
                uppers[a].join();
                downers[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        bc.interrupt();
        assertEquals(0, balancer.get());
        int expected = threads * 2 / barrier;
        int result = bc.getCount();
        assertEquals(expected, result);
    }
}
