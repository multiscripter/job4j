package ru.job4j.analize;

import org.junit.BeforeClass;
import org.junit.Test;
/**
 * Класс AnalizyTest тестирует класс Analizy.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-04-22
 * @since 2019-04-22
 */
public class AnalizyTest {
    /**
     * Путь к папке с файлами логов.
     */
    private static String path;
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        String p = AnalizyTest.class.getClassLoader().getResource(".").getPath();
        path = p.replaceFirst("^/(.:/)", "$1");
    }
    /**
     * Тестирует public void unavailable(String source, String target) throws Exception.
     * @throws Exception исключение.
     */
    @Test
    public void testUnavailable() throws Exception {
        Analizy anal = new Analizy();
        anal.unavailable(String.format("%s%s", path, "log1.serverlog"), String.format("%s%s", path, "log1.csv"));
    }
    /**
     * Тестирует public void unavailable(String source, String target) throws Exception.
     * Пустой лог-файл сервера.
     * @throws Exception исключение.
     */
    @Test
    public void testUnavailableServerLogContainsEmptyStrings() throws Exception {
        Analizy anal = new Analizy();
        anal.unavailable(String.format("%s%s", path, "log2.serverlog"), String.format("%s%s", path, "log2.csv"));
    }
    /**
     * Тестирует public void unavailable(String source, String target) throws Exception.
     * Пустой лог-файл сервера.
     * @throws Exception исключение.
     */
    @Test
    public void testUnavailableServerLogEndsWithStatus500() throws Exception {
        Analizy anal = new Analizy();
        anal.unavailable(String.format("%s%s", path, "log3.serverlog"), String.format("%s%s", path, "log3.csv"));
    }
}