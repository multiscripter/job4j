package ru.job4j.streamapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс ConvertListTest тестирует класс ConvertList.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-28
 * @since 2018-11-28
 */
public class ConvertListTest {
    /**
     * Тестирует public List<List<Integer>> intArrArrToListListInteger(int[][] array).
     */
    @Test
    public void testIntArrArrToListListInteger() {
        int[][] arr = new int[][] {{1, 2, 3}, {4, 5, 6}};
        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(1, 2, 3));
        expected.add(Arrays.asList(4, 5, 6));
        ConvertList cl = new ConvertList();
        List<List<Integer>> actual = cl.intArrArrToListListInteger(arr);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public int[][] listListIntegerToIntArrArr(List<List<Integer>> list).
     */
    @Test
    public void testListListIntegerToIntArrArr() {
        int[][] expected = new int[][] {{1, 2, 3}, {4, 5, 6}};
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 2, 3));
        list.add(Arrays.asList(4, 5, 6));
        ConvertList cl = new ConvertList();
        int[][] actual = cl.listListIntegerToIntArrArr(list);
        assertArrayEquals(expected, actual);
    }
    /**
     * Тестирует public List<Integer> listIntArrToListInteger(List<int[]> list).
     */
    @Test
    public void testListIntArrToListInteger() {
        List<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        List<int[]> list = new ArrayList<>();
        list.add(new int[] {1, 2, 3});
        list.add(new int[] {4, 5, 6});
        ConvertList cl = new ConvertList();
        List<Integer> actual = cl.listIntArrToListInteger(list);
        assertEquals(expected, actual);
    }
}
