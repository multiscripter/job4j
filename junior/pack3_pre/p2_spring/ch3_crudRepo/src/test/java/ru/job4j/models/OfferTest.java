package ru.job4j.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
/**
 * Класс OfferTest тестирует класс Offer.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-01
 * @since 2018-05-10
 */
public class OfferTest {
    /**
     * Карта идентификаторов.
     */
    private HashMap<String, IModel> items = new HashMap<>();
    /**
     * Объявление.
     */
    private Offer offer;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.items.put("body", new Body(1L, "sedan"));
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body(1L, "sedan"));
        bodies.add(new Body(2L, "hatchback"));
        Founder founder = new Founder(0L, "ЦК КПСС", "Совет министров СССР");
        Brand brand = new Brand(0L, "ВАЗ", founder);
        Car car = new Car(0L, "Vesta", bodies, brand);
        this.items.put("car", car);
        User user = new User(1L, "testUser1");
        this.items.put("user", user);
        this.offer = new Offer(0L, this.items, 100500, false);
    }
    /**
     * Получает объект объявления.
     * @return объект объявления.
     */
    private Offer getExpected() {
        Offer offer = new Offer();
        offer.setBody(new Body(1L, "sedan"));
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body(1L, "sedan"));
        bodies.add(new Body(2L, "hatchback"));
        Founder founder = new Founder(0L, "ЦК КПСС", "Совет министров СССР");
        Brand brand = new Brand(0L, "ВАЗ", founder);
        Car car = new Car(0L, "Vesta", bodies, brand);
        offer.setCar(car);
        User user = new User(1L, "testUser1");
        offer.setUser(user);
        offer.setPrice(100500);
        offer.setStatus(false);
        return offer;
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEquals() {
        Offer expected = this.getExpected();
        assertEquals(expected, this.offer);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * 2 ссылки на один объект.
     */
    @Test
    public void testEquals2refsOfOneObject() {
        Offer obj = this.offer;
        assertEquals(obj, this.offer);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        Offer offer = null;
        assertFalse(this.offer.equals(offer));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение объектов разных классов.
     */
    @Test
    public void testEqualsWithDifferentClasses() {
        assertFalse(this.offer.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные id.
     */
    @Test
    public void testEqualsDifferentIds() {
        Offer expected = this.getExpected();
        expected.setId(999L);
        assertFalse(expected.equals(this.offer));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные user.
     */
    @Test
    public void testEqualsDifferentUserIds() {
        Offer expected = this.getExpected();
        expected.getUser().setId(999L);
        assertFalse(expected.equals(this.offer));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные car.
     */
    @Test
    public void testEqualsDifferentCars() {
        Offer expected = this.getExpected();
        expected.getCar().setId(2L);
        expected.getCar().setName("Granta");
        assertFalse(expected.equals(this.offer));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные body.
     */
    @Test
    public void testEqualsDifferentBodies() {
        Offer expected = this.getExpected();
        expected.setBody(new Body(2L, "hatchback"));
        assertFalse(expected.equals(this.offer));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные price.
     */
    @Test
    public void testEqualsDifferentPrices() {
        Offer expected = this.getExpected();
        expected.setPrice(999);
        assertFalse(expected.equals(this.offer));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Разные status.
     */
    @Test
    public void testEqualsDifferentStatuses() {
        Offer expected = this.getExpected();
        expected.setStatus(true);
        assertFalse(expected.equals(this.offer));
    }
    /**
     * Тестирует public String[] getFotos().
     */
    @Test
    public  void testGetFotos() {
        assertNull(this.getExpected().getFotos());
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        int expected = this.getExpected().hashCode();
        int actual = this.offer.hashCode();
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        Offer o = this.getExpected();
        assertEquals("Offer[id: 0, user: User[id: 1, name: testUser1], car: Car[id: 0, name: Vesta, bodies:[Body[id: 1, name: sedan]Body[id: 2, name: hatchback]], brand: Brand[id: 0, name: ВАЗ, founder: Founder[id: 0, nameLast: ЦК КПСС, name: Совет министров СССР]]], body: Body[id: 1, name: sedan], price: 100500, status: false]", o.toString());
    }
}
