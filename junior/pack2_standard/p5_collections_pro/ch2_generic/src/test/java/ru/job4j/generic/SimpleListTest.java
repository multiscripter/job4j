package ru.job4j.generic;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/**
 * Класс SimpleListTest тестирует класс SimpleList.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-24
 */
public class SimpleListTest {
    /**
     * Объект SimpleList<Integer>.
     */
    private SimpleList<Integer> sl;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        int size = 11;
        this.sl = new SimpleList<>(size);
        for (int a = 0; a < size; a++) {
            this.sl.add(a *  100);
        }
    }
    /**
     * Тестирует add().
     */
    @Test
    public void testAdd() {
        this.sl.add(11);
    }
    /**
     * Тестирует count().
     */
    @Test
    public void testCount() {
        this.sl.add(200);
        this.sl.add(100);
        int result = this.sl.count();
        assertEquals(13, result);
    }
    /**
     * Тестирует delete().
     */
    @Test
    public void testDelete() {
        Integer[] expected = new Integer[]{0, 100, 200, 300, 400, 600, 700, 800, 900, 1000};
        this.sl.delete(5);
        Integer[] result = Arrays.copyOfRange(this.sl.toArray(), 0, this.sl.count(), Integer[].class);
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует get().
     */
    @Test
    public void testGet() {
        Integer expected = Integer.valueOf(400);
        Integer result = this.sl.get(4);
        assertEquals(expected, result);
    }
    /**
     * Тестирует index().
     */
    @Test
    public void testIndex() {
        assertEquals(11, this.sl.index());
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        this.sl.add(211);
        int result = this.sl.size();
        assertEquals(22, result);
    }
    /**
     * Тестирует update().
     */
    @Test
    public void testUpdate() {
        int index = 4;
        Integer expected = Integer.valueOf(222);
        this.sl.update(index, expected);
        Integer result = this.sl.get(index);
        assertEquals(expected, result);
    }
}