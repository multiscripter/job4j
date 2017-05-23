package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс IterOfIterToIterTest тестирует класс IterOfIterToIter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-23
 */
public class IterOfIterToIterTest {
    /**
     * Тестирует hasNext().
     */
    @Test
    public void testHasNextTrue() {
        ArrayList<Integer> aL1 = new ArrayList<>();
        aL1.addAll(Arrays.asList(1, 2, 3, 4));
        Iterator<Integer> aL1Iter = aL1.iterator();
        ArrayList<Integer> aL2 = new ArrayList<>();
        aL2.addAll(Arrays.asList(6, 7, 8, 9, 10));
        Iterator<Integer> aL2Iter = aL2.iterator();
        ArrayList<Iterator<Integer>> alIterI = new ArrayList<>();
        alIterI.add(aL1Iter);
        alIterI.add(aL2Iter);
        IterOfIterToIter ioiti = new IterOfIterToIter();
        Iterator<Integer> iter = ioiti.convert(alIterI.iterator());
        assertTrue(iter.hasNext());
    }
    /**
     * Тестирует hasNext().
     */
    @Test
    public void testHasNextFalse() {
        ArrayList<Integer> aL1 = new ArrayList<>();
        aL1.addAll(Arrays.asList());
        Iterator<Integer> aL1Iter = aL1.iterator();
        ArrayList<Integer> aL2 = new ArrayList<>();
        aL2.addAll(Arrays.asList());
        Iterator<Integer> aL2Iter = aL2.iterator();
        ArrayList<Iterator<Integer>> alIterI = new ArrayList<>();
        alIterI.add(aL1Iter);
        alIterI.add(aL2Iter);
        IterOfIterToIter ioiti = new IterOfIterToIter();
        Iterator<Integer> iter = ioiti.convert(alIterI.iterator());
        assertFalse(iter.hasNext());
    }
    /**
     * Тестирует next().
     */
    @Test
    public void testNext() {
        ArrayList<Integer> aL1 = new ArrayList<>();
        aL1.addAll(Arrays.asList(1, 2, 3, 4));
        Iterator<Integer> aL1Iter = aL1.iterator();
        ArrayList<Integer> aL2 = new ArrayList<>();
        aL2.addAll(Arrays.asList(6, 7, 8, 9, 10));
        Iterator<Integer> aL2Iter = aL2.iterator();
        ArrayList<Iterator<Integer>> alIterI = new ArrayList<>();
        alIterI.add(aL1Iter);
        alIterI.add(aL2Iter);
        IterOfIterToIter ioiti = new IterOfIterToIter();
        Iterator<Integer> iter = ioiti.convert(alIterI.iterator());
        int[] expected = new int[]{1, 2, 3, 4, 6, 7, 8, 9, 10};
        ArrayList<Integer> tmp = new ArrayList<>();
        while (iter.hasNext()) {
            tmp.add(iter.next());
        }
        int[] result = tmp.stream().mapToInt(i->i).toArray();
        assertArrayEquals(expected, result);
    }
}