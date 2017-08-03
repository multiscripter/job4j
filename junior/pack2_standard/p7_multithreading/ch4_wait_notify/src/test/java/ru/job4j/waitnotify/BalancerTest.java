package ru.job4j.waitnotify;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс BalancerTest тестирует работу классов Balancer, Bull и Bear.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-03
 */
public class BalancerTest {
    /**
     * Класс BalancerTest тестирует работу классов Balancer, Bull и Bear.
     */
    @Test
    public void testingBalancer() {
        int threadsCount = 500;
        int requests = 1000;
        Balancer balanser = new Balancer();
        Bull[] bulls = new Bull[threadsCount];
        Bear[] bears = new Bear[threadsCount];
        for (int a = 0; a < threadsCount; a++) {
            bulls[a] = new Bull(balanser, requests);
            bears[a] = new Bear(balanser, requests);
        }
        for (int a = 0; a < threadsCount; a++) {
            bulls[a].start();
            bears[a].start();
        }
        try {
            for (int a = 0; a < threadsCount; a++) {
                bulls[a].join();
                bears[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertEquals(0, balanser.get());
    }
}
