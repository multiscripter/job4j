package ru.job4j.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
/**
 * Класс CarTest тестирует класс Car.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-30
 * @since 2018-04-26
 */
public class CarTest {
    /**
     * Автомобиль.
     */
    private Car car;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.car = new Car(0, "Vesta", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Car expected = new Car(0, "Vesta", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertEquals(expected, this.car);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Car obj = this.car;
        assertEquals(obj, this.car);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        Car car = null;
        assertFalse(this.car.equals(car));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.car.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        Car expected = new Car(100500, "Vesta", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertFalse(expected.equals(this.car));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        Car expected = new Car(0, "NeVesta", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertFalse(expected.equals(this.car));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные bodies.
     */
    @Test
    public void testEqualsDifferentBodies() {
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body(1, "sedan"));
        bodies.add(new Body(2, "hatchback"));
        Car expected = new Car(0, "NeVesta", bodies, new ArrayList<>(), new ArrayList<>());
        assertFalse(expected.equals(this.car));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные bodies.
     */
    @Test
    public void testEqualsDifferentEngines() {
        List<Engine> engines = new ArrayList<>();
        engines.add(new Engine(1, "VAZ-11186"));
        engines.add(new Engine(2, "VAZ-11183"));
        Car expected = new Car(0, "NeVesta", new ArrayList<>(), engines, new ArrayList<>());
        assertFalse(expected.equals(this.car));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные bodies.
     */
    @Test
    public void testEqualsDifferentGearboxes() {
        List<Gearbox> gearboxes = new ArrayList<>();
        gearboxes.add(new Gearbox(1, "manual"));
        gearboxes.add(new Gearbox(2, "auto"));
        Car expected = new Car(0, "NeVesta", new ArrayList<>(), new ArrayList<>(), gearboxes);
        assertFalse(expected.equals(this.car));
    }
    /**
     * Тестирует public List<Body> getBodies().
     */
    @Test
    public void testGetBodies() {
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body(1, "sedan"));
        bodies.add(new Body(2, "hatchback"));
        this.car.setBodies(bodies);
        assertEquals("hatchback", this.car.getBodies().get(1).getName());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = Objects.hash(0, "Vesta", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        int actual = this.car.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body(1, "sedan"));
        List<Engine> engines = new ArrayList<>();
        engines.add(new Engine(1, "VAZ-11186"));
        List<Gearbox> gearboxes = new ArrayList<>();
        gearboxes.add(new Gearbox(1, "manual"));
        Car car = new Car();
        car.setId(0);
        car.setName("Vesta");
        car.setBodies(bodies);
        car.setEngines(engines);
        car.setGearboxes(gearboxes);
        assertEquals("Car{id: 0, name: Vesta, bodies:[Body[id: 1, name: sedan]], engines:[Engine[id: 1, name: VAZ-11186]], gearboxes:[Gearbox[id: 1, name: manual]]}", car.toString());
    }
}