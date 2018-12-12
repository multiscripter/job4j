package ru.job4j.bank;

import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс AccountTest тестирует класс Account.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-11
 * @since 2018-12-11
 */
public class AccountTest {
    /**
     * Счёт в банке.
     */
    private Account acc;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.acc = new Account("0OIKZHGH", 9999.99);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     */
    @Test
    public void testEqualsObjectAreEquals() {
        Account acc1 = new Account("0OIKZHGH", 9999.99);
        Account acc2 = new Account("0OIKZHGH", 9999.99);
        assertTrue(acc1.equals(acc2));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Две ссылки на один объект.
     */
    @Test
    public void testEqualsSameRef() {
        assertEquals(this.acc, this.acc);
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с null.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(this.acc.equals(null));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение с объектом другого класса.
     */
    @Test
    public void testEqualsWithDifferentClassObject() {
        assertFalse(this.acc.equals(""));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение со счётом с другими реквизитами.
     */
    @Test
    public void testEqualsWithObjectWithDifferentRequisites() {
        Account anotherAcc = new Account("ololo", 9999.99);
        assertFalse(this.acc.equals(anotherAcc));
    }
    /**
     * Тестирует public boolean equals(Object obj).
     * Сравнение со счётом с другим значением.
     */
    @Test
    public void testEqualsWithObjectWithDifferentValues() {
        Account anotherAcc = new Account("0OIKZHGH", 100500.0);
        assertFalse(this.acc.equals(anotherAcc));
    }
    /**
     * Тестирует public String getRequisites().
     */
    @Test
    public void testGetRequisites() {
        assertEquals("0OIKZHGH", this.acc.getRequisites());
    }
    /**
     * Тестирует public double getValue().
     */
    @Test
    public void testGetValue() {
        assertEquals(9999.99, this.acc.getValue(), 0.001);
    }
    /**
     * Тестирует public int hashCode().
     */
    @Test
    public void testHashCode() {
        assertEquals(Objects.hash("0OIKZHGH", 9999.99), this.acc.hashCode());
    }
    /**
     * Тестирует public void setValue(double value).
     */
    @Test
    public void testSetValue() {
        double expected = 100500.0;
        this.acc.setValue(expected);
        assertEquals(expected, this.acc.getValue(), 0.001);
    }
    /**
     * Тестирует public String toString().
     */
    @Test
    public void testToString() {
        String expected = String.format("Account{requisites: 0OIKZHGH, value: %.2f}", 9999.99);
        assertEquals(expected, this.acc.toString());
    }
}