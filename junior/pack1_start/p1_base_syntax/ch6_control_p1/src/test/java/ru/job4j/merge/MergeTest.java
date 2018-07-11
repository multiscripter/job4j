package ru.job4j.merge;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class MergeTest тестирует методы класса Merge.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-13
 */
public class MergeTest {
	/**
     * Тестирует метод int[] merge(int[] first, int[] second).
     */
	@Test
	public void testMergeIntArraysFirstLongerSecond() {
		Merge merge = new Merge();
		int[] first = {-5, -2, 0, 2, 4, 6};
		int[] second = {-4, -2, 1, 3, 7};
		int[] expected = {-5, -4, -2, -2, 0, 1, 2, 3, 4, 6, 7};
		int[] result = merge.merge(first, second);
		assertThat(result, is(expected));
	}
	/**
     * Тестирует метод int[] merge(int[] first, int[] second).
     */
	@Test
	public void testMergeIntArraysSecondLongerFirst() {
		Merge merge = new Merge();
		int[] first = {-4, -2, 0, 4, 6};
		int[] second = {-5, -2, 1, 2, 3, 7};
		int[] expected = {-5, -4, -2, -2, 0, 1, 2, 3, 4, 6, 7};
		int[] result = merge.merge(first, second);
		assertThat(result, is(expected));
	}
}