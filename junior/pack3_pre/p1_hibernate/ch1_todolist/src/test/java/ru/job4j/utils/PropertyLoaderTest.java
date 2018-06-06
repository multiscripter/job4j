package ru.job4j.utils;

import java.io.IOException;
import java.util.Properties;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
/**
 * Класс PropertyLoaderTest тестирует класс PropertyLoader.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-20
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
     * Тестирует public Properties getProperties().
     */
    @Test
    public void testGetProperties() {
        try {
            this.pl.load("junior.pack3.p1.ch1.task1.properties");
            Properties props = this.pl.getProperties();
            assertFalse(props.isEmpty());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void load(String localName) throws IOException.
     */
    @Test
    public void testLoad() {
        try {
            this.pl.load("junior.pack3.p1.ch1.task1.properties");
            assertFalse(this.pl.getProperties().isEmpty());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}