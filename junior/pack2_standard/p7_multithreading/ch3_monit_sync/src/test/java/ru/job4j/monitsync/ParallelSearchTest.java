package ru.job4j.monitsync;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс ParallelSearchTest тестирует класс ParallelSearch.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-31
 */
public class ParallelSearchTest  {
    /**
     * Тестирует работу класса ParallelSearch.
     */
    @Test
    public void testingParallelSearch() {
        ParallelSearch ps = new ParallelSearch("/var/www/site.name/wp-content/themes", "TPLURI", Arrays.asList(new String[]{"php", "js"}));
        List<String> result = ps.result();
        assertEquals(4, result.size());
    }
    /**
     * Тестирует исключение PathIsNotDirException.
     */
    @Test(expected = PathIsNotDirException.class)
    public void testPathIsNotDirException() {
        new ParallelSearch("^&%^&$%", "ololo", Arrays.asList(new String[]{"php", "xml"}));
    }
    /**
     * Тестирует исключение SearchStringIsEmptyException.
     */
    @Test(expected = SearchStringIsEmptyException.class)
    public void testSearchStringIsEmptyException() {
        new ParallelSearch("/", "    ", Arrays.asList(new String[]{"php", "xml"}));
    }
}
