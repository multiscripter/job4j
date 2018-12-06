package ru.job4j.merge;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс MergeTest тестирует методы класса Merge.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
 * @since 2017-04-13
 */
public class MergeTest {
	/**
     * Тестирует public int[] merge(int[] first, int[] second).
     */
	@Test
	public void testMergeIntArraysFirstLongerThenSecond() {
		Merge merge = new Merge();
		int[] first = {-5, -2, 0, 2, 4, 6};
		int[] second = {-4, -2, 1, 3, 7};
		int[] expected = {-5, -4, -2, -2, 0, 1, 2, 3, 4, 6, 7};
		int[] actual = merge.merge(first, second);
		assertArrayEquals(expected, actual);
	}
	/**
     * Тестирует public int[] merge(int[] first, int[] second).
     */
	@Test
	public void testMergeIntArraysSecondLongerThenFirst() {
		Merge merge = new Merge();
		int[] first = {-4, -2, 0, 4, 6};
		int[] second = {-5, -2, 1, 2, 3, 7};
		int[] expected = {-5, -4, -2, -2, 0, 1, 2, 3, 4, 6, 7};
		int[] actual = merge.merge(first, second);
		assertArrayEquals(expected, actual);
	}
    /**
     * Тестирует public int[] merge(int[] first, int[] second).
     * Оба массива одинаковой длины.
     */
	@Test
	public void testMergeIntArraysBothHaveSameLength() {
        Merge merge = new Merge();
        int[] first = {-4, -2, 0, 4, 6};
        int[] second = {-5, -2, 1, 2, 3};
        int[] expected = {-5, -4, -2, -2, 0, 1, 2, 3, 4, 6};
        int[] actual = merge.merge(first, second);
        assertArrayEquals(expected, actual);
    }
}