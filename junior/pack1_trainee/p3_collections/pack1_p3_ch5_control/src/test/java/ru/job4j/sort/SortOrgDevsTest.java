package ru.job4j.sort;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс SortOrgDevsTest тестирует класс SortOrgDevs.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
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
        String[] actual = this.sod.getNamesOrderAsc();
        assertArrayEquals(expected, actual);
    }
    /**
     * Тестирует getNamesOrderDesc().
     */
    @Test
    public void testGetNamesOrderDesc() {
        String[] expected = new String[]{"K2", "K2\\SK1", "K2\\SK1\\SSK2", "K2\\SK1\\SSK1", "K1", "K1\\SK2", "K1\\SK1", "K1\\SK1\\SSK2", "K1\\SK1\\SSK1"};
        String[] actual = this.sod.getNamesOrderDesc();
        assertArrayEquals(expected, actual);
    }
    /**
     * Тестирует getSubDepsNames().
     */
    @Test
    public void testGetSubDepsNames() {
        String[] expected = new String[]{"K1\\SK1\\SSK1", "K1\\SK1\\SSK2"};
        List<String> subDepsNames = this.sod.getDepByFullName("K1\\SK1").getSubDepsNames();
        String[] actual = subDepsNames.toArray(new String[subDepsNames.size()]);
        assertArrayEquals(expected, actual);
    }
}
