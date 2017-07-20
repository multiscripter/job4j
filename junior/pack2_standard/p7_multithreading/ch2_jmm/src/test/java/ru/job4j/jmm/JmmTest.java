package ru.job4j.jmm;

import org.junit.Test;
/**
 * Класс JmmTest тестирует класс Jmm.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-20
 */
public class JmmTest {
    /**
     * Показывает проблему видимости.
     */
    @Test
    public void showJmmVisibilityProblem() {
        Jmm jmm = new Jmm(0);
        Thread t1 = new Thread() {
            @Override
            public void run() {
                int a = jmm.getVal();
                jmm.setVal(a + 100);
                System.out.println(jmm.getVal());
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                int a = jmm.getVal();
                jmm.setVal(a - 100);
                System.out.println(jmm.getVal());
            }
        };
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(jmm);
    }
}
