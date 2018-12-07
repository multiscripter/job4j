package ru.job4j.control;

import java.util.LinkedList;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import ru.job4j.control.service.Property;
import ru.job4j.control.service.PropertyLoader;
/**
 * Класс PropertyLoaderTest тестирует класс PropertyLoader.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-10
 * @since 2018-01-10
 */
public class PropertyLoaderTest {
    /**
     * Загрузчик свойств.
     */
    private PropertyLoader pl;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.pl = new PropertyLoader();
    }
    /**
     * Тестирует public Properties getProperties().
     */
    @Test
    public void testGetPropertiesOnEmptyLoader() {
        assertNotNull(this.pl.getProperties());
    }
    /**
     * Тестирует public LinkedList<Property> getPropertiesList() throws UnsupportedEncodingException.
     */
    @Test
    public void testGetPropertiesList() {
        try {
            LinkedList<Property> expected = new LinkedList<>();
            expected.add(new Property("db", "jpack2p9ch9task1"));
            expected.add(new Property("pass", "postgresrootpass"));
            expected.add(new Property("port", "5432"));
            expected.add(new Property("protocol", "postgresql"));
            expected.add(new Property("src", "localhost"));
            expected.add(new Property("tbl_addrs", "addresses"));
            expected.add(new Property("tbl_musictypes", "musictypes"));
            expected.add(new Property("tbl_roles", "roles"));
            expected.add(new Property("tbl_users", "users"));
            expected.add(new Property("tbl_users_musictypes", "users_musictypes"));
            expected.add(new Property("user", "postgres"));
            this.pl.load("junior.pack2.p9.ch9.task1.properties");
            LinkedList<Property> actual = this.pl.getPropertiesList();
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void load(String localName) throws IOException.
     */
    @Test
    public void testLoad() {
        try {
            this.pl.load("junior.pack2.p9.ch9.task1.properties");
            assertFalse(this.pl.getProperties().isEmpty());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}