package ru.job4j.convertlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/**
 * Класс ConvertListTest тестирует класс ConvertList.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
 * @since 2017-05-10
 */
public class ConvertListTest {
    /**
     * Тестирует public List<Integer> toList(int[][] array).
     */
    @Test
    public void testToList() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 0, 0));
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
        ConvertList cl = new ConvertList();
        List actual = cl.toList(array);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public int[][] toArray(List<Integer> list, int rows).
     */
    @Test
    public void testToArray() {
        int[][] expected = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        ConvertList cl = new ConvertList();
        int[][] actual = cl.toArray(list, 3);
        assertArrayEquals(expected, actual);
    }
    /**
     * Тестирует public List<Integer> convert(List<int[]> list).
     */
    @Test
    public void testConvert() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 2});
        list.add(new int[]{3, 4, 5, 6});
        ConvertList cl = new ConvertList();
        List<Integer> actual = cl.convert(list);
        assertEquals(expected, actual);
    }
}