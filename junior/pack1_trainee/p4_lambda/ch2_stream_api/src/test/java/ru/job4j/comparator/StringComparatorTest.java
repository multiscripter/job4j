package ru.job4j.comparator;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
/**
 * Класс StringComparatorTest тестирует класс StringComparator.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-05
 * @since 2018-12-05
 */
public class StringComparatorTest {
    /**
     * Тестирует public int compare(String left, String right).
     * Строки равны.
     */
    @Test
    public void testCompareStringsAreEqual() {
        StringComparator sc = new StringComparator();
        assertTrue(sc.compare("Ivanov", "Ivanov") == 0);
    }
    /**
     * Тестирует public int compare(String left, String right).
     * Первая строка лексикографически меньше второй.
     */
    @Test
    public void testCompareStr1LessStr2() {
        StringComparator sc = new StringComparator();
        assertTrue(sc.compare("Ivanov", "Ivanova") < 0);
    }
    /**
     * Тестирует public int compare(String left, String right).
     * Первая строка лексикографически больше второй.
     */
    @Test
    public void testCompareStr1HasGreaterLength() {
        StringComparator sc = new StringComparator();
        assertTrue(sc.compare("Ivanova", "Ivanov") > 0);
    }
    /**
     * Тестирует public int compare(String left, String right).
     * Первая строка лексикографически больше второй.
     */
    @Test
    public void testCompareStr1GreaterStr2() {
        StringComparator sc = new StringComparator();
        assertTrue(sc.compare("Petrov", "Ivanova") > 0);
    }
    /**
     * Тестирует public int compare(String left, String right).
     * Строки различаются одной буквой.
     */
    @Test
    public void testCompareDifferentChar() {
        StringComparator sc = new StringComparator();
        assertTrue(sc.compare("Petrov", "Patrov") > 0);
    }
    /**
     * Тестирует public int compare(String left, String right).
     * Меньшая по длине строка имеет "большую" букву.
     */
    @Test
    public void testCompareLessStrHasGreaterChar() {
        StringComparator sc = new StringComparator();
        assertTrue(sc.compare("Patrova", "Petrov") < 0);
    }
}
