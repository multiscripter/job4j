package ru.job4j.search;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
/**
 * Класс PriorityQueueTest тестирует класс PriorityQueue.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-29
 * @since 2018-11-29
 */
public class PriorityQueueTest {
    /**
     * Тестирует public void put(Task task).
     */
    @Test
    public void testPut() {
        PriorityQueue queue = new PriorityQueue();
        queue.put(new Task("low", 5));
        queue.put(new Task("urgent", 1));
        queue.put(new Task("middle", 3));
        assertTrue("urgent".equals(queue.take().getDesc()));
    }
    /**
     * Тестирует public void put(Task task).
     */
    @Test
    public void testPut2() {
        PriorityQueue queue = new PriorityQueue();
        queue.put(new Task("middle", 3));
        queue.put(new Task("low", 5));
        queue.put(new Task("urgent", 1));
        assertTrue("urgent".equals(queue.take().getDesc()));
    }
}
