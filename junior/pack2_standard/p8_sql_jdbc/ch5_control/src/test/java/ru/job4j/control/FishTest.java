package ru.job4j.control;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс FishTest тестирует класс Fish.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-09
 */
public class FishTest {
    /**
     * Рыба.
     */
    private Fish fish;
    /**
     * Локальное имя файла свойств вида.
     */
    private String plfn;
    /**
     * Свойства вида.
     */
    private Properties props;
    /**
     * Рандомизатор.
     */
    private Random random;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.plfn = "tuna.properties";
            String path = this.getClass().getResource(".").getPath().replaceFirst("^/(.:/)", "$1");
            path = String.format("%s../../../../../src/main/resources/", path);
            Path fName = Paths.get(path + this.plfn);
            InputStream is = Files.newInputStream(fName);
            this.props = new Properties();
            this.props.load(is);
            this.random = new Random();
            this.fish = new Fish(new Aquarium(10), this.random.nextInt(2), this.getLifeTime(), this.props);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Получает продолжительность жизни.
     * @return продолжительность жизни.
     */
    private int getLifeTime() {
        return Integer.parseInt(this.props.getProperty("lifeMin")) + this.random.nextInt(Integer.parseInt(this.props.getProperty("lifeMax")) - Integer.parseInt(this.props.getProperty("lifeMin")));
    }
    /**
     * Тестирует int getAdult().
     */
    @Test
    public void testGetAdult() {
        assertEquals(Integer.parseInt(this.props.getProperty("adult")), this.fish.getAdult());
    }
    /**
     * Тестирует int getIdentity().
     */
    @Test
    public void testGetIdentity() {
        try {
            Fish fish = new Fish(new Aquarium(10), this.random.nextInt(2), this.getLifeTime(), this.props);
            assertEquals(Fish.getLastId(), fish.getIdentity());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует String getType().
     */
    @Test
    public void testGetType() {
        assertEquals(this.props.getProperty("type"), this.fish.getType());
    }
    /**
     * Тестирует boolean isAdult(). Returns false.
     */
    @Ignore@Test
    public void testIsAdultReturnsFalse() {
        try {
            this.fish.start();
            this.fish.join();
            Thread.currentThread().sleep((Integer.parseInt(this.props.getProperty("adult")) - 2) * 1000);
            assertFalse(this.fish.isAdult());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует boolean isAdult(). Returns true.
     */
    @Ignore@Test
    public void testIsAdultReturnsTrue() {
        try {
            this.fish.start();
            this.fish.join();
            Thread.currentThread().sleep((Integer.parseInt(this.props.getProperty("adult")) + 2) * 1000);
            assertTrue(this.fish.isAdult());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует String toString().
     */
    @Test
    public void testToString() {
        try {
            Fish fish = new Fish(new Aquarium(10), this.random.nextInt(2), this.getLifeTime(), this.props);
            assertEquals(String.format("%s#%d", this.props.getProperty("type"), Fish.getLastId()), fish.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}