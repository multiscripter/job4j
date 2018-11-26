package ru.job4j.nonblocking;

import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;
/**
 * Класс CheckTest тестирует работу Check.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-05
 */
public class CheckTest {
    /**
     * Кэш.
     */
    private ConcurrentHashMap<Integer, SimpleBean> cache;
    /**
     * Тестирует работу Check.
     */
    @Test
    public void testCheck() {
        try {
            this.cache = new ConcurrentHashMap<>();
            this.cache.put(1, new SimpleBean(0));
            int threadsCount = 1000;
            Thread[] threads = new Thread[threadsCount];
            for (int a = 0; a < threadsCount; a++) {
                threads[a] = new Thread(new Runnable() {
                    /**
                     * Переопределёный метод.
                     */
                    @Override
                    public void run() {
                        try {
                        Thread t = Thread.currentThread();
                            t.sleep(100);
                            SimpleBean csb = CheckTest.this.cache.get(1);
                            for (int a = 0; a < 1000; a++) {
                                csb.setField();
                            }
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            Thread t2 = new Thread(new Runnable() {
                /**
                 * Переопределёный метод.
                 */
                @Override
                public void run() {
                    try {
                        SimpleBean csb = CheckTest.this.cache.get(1);
                        Thread.currentThread().sleep(1000);
                        System.out.println(csb.getField());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            t2.start();
            for (int a = 0; a < threadsCount; a++) {
                threads[a].start();
            }
            for (int a = 0; a < threadsCount; a++) {
                threads[a].join();
            }
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Класс SimpleBean реализует сущность "Простой боб".
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-08-05
     */
    class SimpleBean {
        /**
         * Поле.
         */
        private int field;
        /**
         * Объект блокировки.
         */
        private Object lock;
        /**
         * Конструктор.
         * @param str строка.
         */
        SimpleBean(int str) {
            this.field = str;
            this.lock = this;
        }
        /**
         * Получает значение поля.
         * @return значение поля.
         */
        public int getField() {
            return this.field;
        }
        /**
         * Устанавливает значение поля.
         */
        public void setField() {
            synchronized (this.lock) {
                this.field++;
            }
        }
        /**
         * Возвращает строковое объекта.
         * @return строковое объекта.
         */
        @Override
        public String toString() {
            return String.format("SimpleBean{field: %d}", this.field);
        }
    }
}
