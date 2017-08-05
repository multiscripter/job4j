package ru.job4j.nonblocking;

import java.util.Comparator;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс NonBlockingCacheTest тестирует работу NonBlockingCache.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-04
 */
public class NonBlockingCacheTest {
    /**
     * Количество трэдов.
     */
    private int threadCount = 100;
    /**
     * Количество запросов.
     */
    private int requestCount = 100;
    /**
     * Результирующий список.
     */
    private ConcurrentLinkedList<String> collector = new ConcurrentLinkedList<>();
    /**
     * Тестирует работу неблокирующего кэша.
     */
    @Test
    public void testingNonBlockingCache() {
        NonBlockingCache nbc = new NonBlockingCache();
        Task task0 = new Task("Task 1 name", "Task 1 initial description");
        nbc.add(task0.getId(), task0);
        this.collector.add(task0.toString());
        TaskChanger[] threads = new TaskChanger[threadCount];
        for (int a = 0; a < threads.length; a++) {
            threads[a] = new TaskChanger(nbc, requestCount);
        }
        try {
            for (int a = 0; a < threads.length; a++) {
                threads[a].start();
            }
            for (int a = 0; a < threads.length; a++) {
                threads[a].join();
            }
            String[] result = nbc.get(1).getRevisions();
            LinkedList<String> collector = this.collector.getList();
            collector.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            String[] expected = collector.toArray(new String[collector.size()]);
            assertArrayEquals(expected, result);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Класс ConcurrentLinkedList реализует сущность "Потоконезависимый связный список".
     *
     * @param <E> Параметризированный тип.
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-08-04
     */
    class ConcurrentLinkedList<E> {
        /**
         * Список.
         */
        private LinkedList<E> list;
        /**
         * Конструктор.
         */
        ConcurrentLinkedList() {
            this.list = new LinkedList<>();
        }
        /**
         * Добавляет элемент в список.
         * @param e добавляемый элемент.
         * @return true если элемент добавлен в список. Иначе false.
         */
        public boolean add(E e) {
            synchronized (this) {
                return this.list.add(e);
            }
        }
        /**
         * Получает список.
         * @return список.
         */
        public LinkedList<E> getList() {
            return this.list;
        }
    }
    /**
     * Класс TaskChanger реализует сущность "Изменятель задачи".
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-08-04
     */
    class TaskChanger extends Thread {
        /**
         * Объект неблокирующего кэша.
         */
        private NonBlockingCache nbc;
        /**
         * Количество запросов к очереди.
         */
        private int reqs;
        /**
         * Конструктор.
         * @param nbc объект неблокирующего кэша.
         * @param reqs количество запросов к очереди.
         */
        TaskChanger(NonBlockingCache nbc, int reqs) {
            this.nbc = nbc;
            this.reqs = reqs;
        }
        /**
         * Переопределённый метод.
         */
        @Override
        public void run() {
            Task task;
            String desc;
            for (int a = 0; a < this.reqs; a++) {
                try {
                    Object result;
                    desc = String.format("%s: %d", this.getName(), a);
                    task = this.nbc.get(1);
                    task.setDescription(desc);
                    result = this.nbc.update(1, task);
                    NonBlockingCacheTest.this.collector.add(result.toString());
                } catch (OplimisticException ex) {
                    a--;
                }
            }
            this.interrupt();
        }
    }
}
