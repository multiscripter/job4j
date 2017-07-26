package ru.job4j.monitsync;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс UserStorageTest тестирует класс UserStorage.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-25
 */
public class UserStorageTest {
    /**
     * Магическая константа.
     */
    private final int mc = 1000;
    /**
     * Начальное количество на счету.
     */
    private final int startAmount = 100;
    /**
     * Массив тридов.
     */
    private Thread[] threads;
    /**
     * Объект хранилища пользователей.
     */
    private UserStorage us;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.us = new UserStorage();
        this.threads = new Thread[this.mc];
        for (int a = 0; a < this.mc; a++) {
            this.us.add(new User(a, this.startAmount));
            this.threads[a] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int from = (int) (us.size() * Math.random());
                        int to = (int) (us.size() * Math.random());
                        int amount = (int) (startAmount * Math.random());
                        if (from != to) {
                            us.transfer(from, to, amount);
                        }
                        Thread.currentThread().sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    /**
     * Тестирует работу класса UserStorage.
     */
    @Test
    public void testingUserStorage() {
        try {
            for (int a = 0; a < this.mc; a++) {
                this.threads[a].start();
            }
            for (int a = 0; a < this.mc; a++) {
                this.threads[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertEquals(this.mc * this.startAmount, this.us.getTotalAmount());
    }
}