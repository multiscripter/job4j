package ru.job4j.monitsync;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс ParallelSearchTest тестирует класс ParallelSearch.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-11
 * @since 2017-07-31
 */
public class ParallelSearchTest  {
    /**
     * Тестирует работу класса ParallelSearch.
     */
    @Test
    public void testingParallelSearch() {
        String localFileName = "D:\\web";
        File dir = new File(localFileName);
        if (dir.isDirectory()) {
            ParallelSearch ps = new ParallelSearch(localFileName, "TPLURI", Arrays.asList(new String[]{"php", "js"}));

            List<String> result = ps.result();
            assertEquals(137, result.size());
        }
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
