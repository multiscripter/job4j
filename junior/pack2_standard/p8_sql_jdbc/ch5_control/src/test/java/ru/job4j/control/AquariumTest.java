package ru.job4j.control;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс AquariumTest тестирует класс Aquarium.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-10
 */
public class AquariumTest {
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
     * Тестирует int getPopulationSize().
     */
    @Test
    public void testGetPopulationSize() {
        try {
            Aquarium aq = new Aquarium(100);
            LinkedList<Fish> fishes = new LinkedList<>();
            fishes.add(new Fish(aq, 0, this.getLifeTime(), this.props));
            fishes.add(new Fish(aq, 1, this.getLifeTime(), this.props));
            fishes.add(new Fish(aq, 0, this.getLifeTime(), this.props));
            fishes.add(new Fish(aq, 1, this.getLifeTime(), this.props));
            aq.addAll(fishes);
            aq.live(fishes);
            assertEquals(fishes.size(), aq.getPopulationSize());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Симулируем нелёгкую жысь в аквариуме.
     */
    @Test
    public void semulateAquariumLife() {
        try {
            Aquarium aq = new Aquarium(100);
            LinkedList<Fish> fishes = new LinkedList<>();
            fishes.add(new Fish(aq, 0, this.getLifeTime(), this.props));
            fishes.add(new Fish(aq, 1, this.getLifeTime(), this.props));
            aq.addAll(fishes);
            aq.start();
            aq.join();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}