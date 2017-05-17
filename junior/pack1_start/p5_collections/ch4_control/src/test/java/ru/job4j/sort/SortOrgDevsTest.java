package ru.job4j.sort;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Class SortOrgDevsTest тестирует класс SortOrgDevs.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-17
 */
public class SortOrgDevsTest {
    /**
     * Объект SortOrgDevs.
     */
    private SortOrgDevs sod;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        String[] names = new String[]{"K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
        this.sod = new SortOrgDevs(names);
    }
    /**
     * Тестирует getNamesOrderAsc().
     */
    @Test
    public void testGetNamesOrderAsc() {
        String[] expected = new String[]{"K1", "K1\\SK1", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K1\\SK2", "K2", "K2\\SK1", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};
        String[] result = this.sod.getNamesOrderAsc();
        assertEquals(expected, result);
    }
    /**
     * Тестирует getNamesOrderDesc().
     */
    @Test
    public void testGetNamesOrderDesc() {
        String[] expected = new String[]{"K2", "K2\\SK1", "K2\\SK1\\SSK2", "K2\\SK1\\SSK1", "K1", "K1\\SK2", "K1\\SK1", "K1\\SK1\\SSK2", "K1\\SK1\\SSK1"};
        String[] result = this.sod.getNamesOrderDesc();
        assertEquals(expected, result);
    }
}