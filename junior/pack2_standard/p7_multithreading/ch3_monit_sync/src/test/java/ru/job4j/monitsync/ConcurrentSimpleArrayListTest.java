package ru.job4j.monitsync;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс ConcurrentSimpleArrayListTest тестирует класс ConcurrentSimpleArrayList<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-26
 */
public class ConcurrentSimpleArrayListTest {
    /**
     * Количество тридов.
     */
    private final int threadCount = 1000;
    /**
     * Количество итераций.
     */
    private final int iterations = 1000;
    /**
     * Массив тридов.
     */
    private Thread[] threads;
    /**
     * Действия перед тестами.
     */
    @Before
    public void beforeTest() {
        this.threads = new Thread[this.threadCount];
    }
    /**
     * Тестирует add(E e).
     */
    @Test
    public void testAdd() {
        ConcurrentSimpleArrayList<String> csar = new ConcurrentSimpleArrayList<>();
        String[] expected = new String[this.threadCount * this.iterations];
        int length = Integer.toString(this.threadCount * this.iterations).length();
        String formatter = String.format("%%0%dd", length);
        for (int a = 0; a < this.threadCount; a++) {
            for (int b = 0; b < this.iterations; b++) {
                expected[a * this.iterations + b] = String.format(formatter, a * this.iterations + b);
            }
            this.threads[a] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int b = 0; b < iterations; b++) {
                            Thread t = Thread.currentThread();
                            csar.add(String.format(formatter, Integer.parseInt(t.getName()) * iterations + b));
                            t.sleep(1);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            this.threads[a].setName(Integer.toString(a));
        }
        try {
            for (int a = 0; a < this.threadCount; a++) {
                this.threads[a].start();
            }
            for (int a = 0; a < this.threadCount; a++) {
                this.threads[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        csar.sort();
        Object[] result = csar.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует add(int index, E e).
     */
    @Test
    public void testAddByIndex() {
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>();
        sar.add("Foo");
        sar.add(0, "newFoo");
    }
    /**
     * Тестирует increaseCapacity().
     */
    @Test
    public void testIncreaseCapacity() {
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>(2);
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        assertEquals(4, sar.capacity());
    }
    /**
     * Тестирует get(int index).
     */
    @Test
    public void testGet() {
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>();
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        String expected = new String("Bar");
        String result = sar.get(1);
        assertEquals(expected, result);
    }
    /**
     * Тестирует isEmpty().
     */
    @Test
    public void testIsEmpty() {
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>();
        sar.add("Foo");
        sar.remove("Foo");
        assertTrue(sar.isEmpty());
    }
    /**
     * Тестирует iterator().
     */
    @Test
    public void testIterator() {
        String[] expected = {"Foo", "Bar", "Baz"};
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>();
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        Iterator<String> sarIter = sar.iterator();
        String[] result = new String[3];
        int index = 0;
        while (sarIter.hasNext()) {
            result[index++] = sarIter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует remove(int index).
     */
    @Test
    public void testRemove() {
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>();
        String expected = new String("Baz");
        sar.add("Foo");
        sar.add("Bar");
        sar.add(expected);
        sar.remove(1);
        String result = sar.get(1);
        assertEquals(expected, result);
    }
    /**
     * Тестирует set(int index).
     */
    @Test
    public void testSet() {
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>();
        String expected = new String("Bar");
        sar.add("Foo");
        sar.add(expected);
        sar.add("Baz");
        String result = sar.set(1, new String("Jossa"));
        assertEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>();
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        assertEquals(3, sar.size());
    }
    /**
     * Тестирует toArray().
     */
    @Test
    public void testToArray() {
        ConcurrentSimpleArrayList<String> sar = new ConcurrentSimpleArrayList<>();
        sar.add("Foo");
        sar.add("Bar");
        sar.add("Baz");
        Object[] expected = {"Foo", "Bar", "Baz"};
        assertArrayEquals(expected, sar.toArray());
    }
}
