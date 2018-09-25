package ru.job4j.list;

import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс DoubleStackQueueTest тестирует класс DoubleStackQueue<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-17
 * @since 2017-05-31
 */
public class DoubleStackQueueTest {
    /**
     * Тестирует public boolean add(E e).
     */
    @Test
    public void testAdd() {
        DoubleStackQueue<String> dsq = new DoubleStackQueue<>();
        assertTrue(dsq.add("Foo"));
    }
    /**
     * Тестирует public Iterator<E> iterator().
     */
    @Test
    public void testIterator() {
        DoubleStackQueue<String> dsq = new DoubleStackQueue<>();
        dsq.add("Foo");
        dsq.add("Bar");
        dsq.add("Baz");
        dsq.poll();
        dsq.add("test1");
        dsq.add("test2");
        dsq.poll();
        dsq.poll();
        Iterator<String> iter = dsq.iterator();
        String[] expected = { "test1", "test2" };
        String[] actual = new String[2];
        for (int a = 0; iter.hasNext(); a++) {
            actual[a] = iter.next();
        }
        assertArrayEquals(expected, actual);
    }
    /**
     * Тестирует public boolean offer(E e).
     */
    @Test
    public void testOffer() {
        DoubleStackQueue<String> dsq = new DoubleStackQueue<>();
        assertTrue(dsq.offer("testOffer"));
    }
    /**
     * Тестирует public boolean offer(E e).
     * Выбрасывает NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testOfferThrowsNullPointerException() {
        DoubleStackQueue<String> dsq = new DoubleStackQueue<>();
        dsq.offer(null);
    }
    /**
     * Тестирует public E peek().
     */
    @Test
    public void testPeek() {
        DoubleStackQueue<String> dsq = new DoubleStackQueue<>();
        dsq.add("Foo");
        dsq.add("Bar");
        dsq.add("Baz");
        dsq.peek();
        dsq.add("test1");
        dsq.add("test2");
        assertTrue("Foo".equals(dsq.peek()));
    }
    /**
     * Тестирует public E poll().
     */
    @Test
    public void testPoll() {
        DoubleStackQueue<String> dsq = new DoubleStackQueue<>();
        dsq.add("Foo");
        dsq.add("Bar");
        dsq.add("Baz");
        dsq.poll();
        dsq.add("test1");
        dsq.add("test2");
        dsq.poll();
        dsq.poll();
        dsq.poll();
        assertTrue("test2".equals(dsq.poll()));
    }
    /**
     * Тестирует public int size().
     */
    @Test
    public void testSize() {
        DoubleStackQueue<String> dsq = new DoubleStackQueue<>();
        dsq.add("Foo");
        dsq.add("Bar");
        dsq.add("Baz");
        dsq.poll();
        dsq.add("test1");
        dsq.add("test2");
        dsq.poll();
        dsq.poll();
        assertTrue(dsq.size() == 2);
    }
}
