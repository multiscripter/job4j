package ru.job4j.convertlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Класс ConvertListTest тестирует класс ConvertList.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-10
 */
public class ConvertListTest {
    /**
     * Тестирует toList().
     */
    @Test
    public void testToList() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 0, 0));
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
        ConvertList cl = new ConvertList();
        List result = cl.toList(array);
        assertEquals(expected, result);
    }
    /**
     * Тестирует toArray().
     */
    @Test
    public void testToArray() {
        int[][] expected = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
        int[] vals = new int[]{1, 2, 3, 4, 5, 6, 7};
        List<Integer> list = new ArrayList<>();
        for (int a = 0; a < vals.length; a++) {
            list.add(vals[a]);
        }
        ConvertList cl = new ConvertList();
        int[][] result = cl.toArray(list, 3);
        assertEquals(expected, result);
    }
}