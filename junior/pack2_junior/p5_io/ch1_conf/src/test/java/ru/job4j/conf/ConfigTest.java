package ru.job4j.conf;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Класс ConfigTest тестирует класс Config.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-04-08
 * @since 2019-04-08
 */
public class ConfigTest {
    /**
     * Имя файла свойств.
     */
    private static String propsFileName;
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        String path = ConfigTest.class.getClassLoader().getResource(".").getPath();
        path = path.replaceFirst("^/(.:/)", "$1");
        propsFileName = String.format("%s%s", path, "HyperSQL.properties");
    }
    /**
     * Тестирует public void load() throws Exception.
     * @throws Exception исключение.
     */
    @Test
    public void testLoad() throws Exception {
        Config conf = new Config(propsFileName);
        conf.load();
        assertTrue(conf.size() > 0);
    }
    /**
     * Тестирует public void load() throws Exception.
     * Выброс исключения.
     * @throws Exception исключение.
     */
    @Test(expected = Exception.class)
    public void testLoadThrowsException() throws Exception {
        new Config("fakeFileName").load();
    }
    /**
     * Тестирует public String toString().
     * @throws Exception исключение.
     */
    @Test
    public void testToString() throws Exception {
        StringBuilder expected = new StringBuilder();
        expected.append("# Qualified dbms driver class name.");
        expected.append(System.lineSeparator());
        expected.append("driver = org.hsqldb.jdbc.JDBCDriver");
        expected.append(System.lineSeparator());
        expected.append(System.lineSeparator());
        expected.append("!DBMS protocol.");
        expected.append(System.lineSeparator());
        expected.append("protocol=hsqldb");
        expected.append(System.lineSeparator());
        expected.append("src=mem");
        expected.append(System.lineSeparator());
        expected.append(System.lineSeparator());
        expected.append("port=");
        expected.append(System.lineSeparator());
        expected.append(System.lineSeparator());
        expected.append("db=jpack3p2ch1task2");
        expected.append(System.lineSeparator());
        expected.append("user=SA");
        expected.append(System.lineSeparator());
        expected.append("pass=");
        expected.append(System.lineSeparator());
        expected.append("url1=jdbc:%1$s:%2$s:%4$s");
        expected.append(System.lineSeparator());
        expected.append("params=get_column_name=false");
        Config conf = new Config(propsFileName);
        conf.load();
        String actual = conf.toString();
        assertEquals(expected.toString(), actual);
    }
    /**
     * Тестирует public String value(String key).
     * @throws Exception исключение.
     */
    @Test
    public void testValue() throws Exception {
        Config conf = new Config(propsFileName);
        conf.load();
        assertEquals(conf.value("driver"), "org.hsqldb.jdbc.JDBCDriver");
    }
}
