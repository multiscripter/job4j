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
 * @version 2018-05-14
 * @since 2018-04-26
 */
public class CarTest {
    /**
     * Брэнд.
     *
    private Brand brand;*/
    /**
     * Автомобиль.
     */
    private Car car;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        //Founder f = new Founder(0, "ЦК КПСС", "Совет министров СССР");
        //this.brand = new Brand(0, "ВАЗ", f);
        this.car = new Car(0, "Vesta", new ArrayList<>()/*, this.brand*/);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Car expected = new Car(0, "Vesta", new ArrayList<>()/*, this.brand*/);
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
        Car expected = new Car(100500, "Vesta", new ArrayList<>()/*, this.brand*/);
        assertFalse(expected.equals(this.car));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные name.
     */
    @Test
    public void testEqualsDifferentNames() {
        Car expected = new Car(0, "NeVesta", new ArrayList<>()/*, this.brand*/);
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
        Car expected = new Car(0, "NeVesta", bodies/*, this.brand*/);
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
        int expected = Objects.hash(0, "Vesta", new ArrayList<>()/*, this.brand*/);
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
        //Founder f = new Founder(0, "ЦК КПСС", "Совет министров СССР");
        Car car = new Car();
        car.setId(0);
        car.setName("Vesta");
        car.setBodies(bodies);
        //car.setBrand(new Brand(0, "ВАЗ", f));
        assertEquals("Car[id: 0, name: Vesta, bodies:[Body[id: 1, name: sedan]]]", car.toString());
    }
}