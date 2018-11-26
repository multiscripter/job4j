package ru.job4j.set;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleArraySetTest тестирует класс SimpleArraySet<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-11
 * @since 2017-06-01
 */
public class SimpleArraySetTest {
    /**
     * Объект множества на массивах.
     */
    private SimpleArraySet<String> sas;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.sas = new SimpleArraySet<>();
        for (int a = 0; a < 9; a++) {
            this.sas.add("Foo" + a * 2);
        }
    }
    /**
     * Тестирует линейный поиск. 100000 записей.
     */
    @Ignore@Test
    public void testLinearSearch100K() {
        SimpleArraySet<String> sas = new SimpleArraySet<>();
        System.out.print("testLinearSearch100K: ");
        long start = System.nanoTime();
        for (int a = 0; a < 100000; a++) {
            sas.add("M" + a);
        }
        System.out.println(System.nanoTime() - start);
    }
    /**
     * Тестирует add(E e).
     */
    @Test
    public void testAddTrue() {
        assertTrue(this.sas.add("Baz"));
    }
    /**
     * Тестирует add(E e). Добавляемый объект уже есть в множестве.
     */
    @Test
    public void testAddFalse() {
        this.sas.add("Baz");
        assertFalse(this.sas.add("Baz"));
    }
    /**
     * Тестирует clear(). Проверка по размеру size().
     */
    @Test
    public void testClearBySize() {
        this.sas.clear();
        assertEquals(0, this.sas.size());
    }
    /**
     * Тестирует clear(). Проверка массива, плученного вызовом toArray().
     */
    @Test
    public void testClearByToArray() {
        this.sas.clear();
        assertEquals(0, this.sas.toArray().length);
    }
    /**
     * Тестирует contains(Object o). Проверяемый объект уже есть в множестве.
     */
    @Test
    public void testContainsTrue() {
        assertTrue(this.sas.contains("Foo10"));
    }
    /**
     * Тестирует contains(Object o). Проверяемого объекта нет в множестве.
     */
    @Test
    public void testContainsFalse() {
        assertFalse(this.sas.contains("Bar"));
    }
    /**
     * Тестирует increaseCapacity().
     */
    @Test
    public void testIncreaseCapacity() {
        for (int a = 0; a < 9; a++) {
            this.sas.add("Bar" + a * 2);
        }
        assertEquals(18, this.sas.size());
    }
    /**
     * Тестирует isEmpty(). Множество не пустое.
     */
    @Test
    public void testIsEmptyFalse() {
        assertFalse(this.sas.isEmpty());
    }
    /**
     * Тестирует isEmpty(). Множество пустое.
     */
    @Test
    public void testIsEmptyTrue() {
        this.sas.clear();
        assertTrue(this.sas.isEmpty());
    }
    /**
     * Тестирует iterator().
     */
    @Test
    public void testIterator() {
        String[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        int size = 9;
        String[] result = new String[size];
        Iterator<String> iter = this.sas.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует remove().
     */
    @Test
    public void testRemove() {
        Object[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo10", "Foo12", "Foo14", "Foo16"};
        this.sas.remove("Foo8");
        Object[] result = this.sas.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        assertEquals(9, this.sas.size());
    }
    /**
     * Тестирует Object[] toArray().
     */
    @Test
    public void testToObjectArray() {
        Object[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        Object[] result = this.sas.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует E[] toArray().
     */
    @Test
    public void testToEArray() {
        String[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        String[] result = this.sas.toArray(new String[9]);
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует Iterator.remove().
     */
    @Test
    public void testIteratorRemove() {
        Object[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo10", "Foo12", "Foo14", "Foo16"};
        Iterator<String> iter = this.sas.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals("Foo8")) {
                iter.remove();
            }
        }
        Object[] result = this.sas.toArray();
        assertArrayEquals(expected, result);
    }
}
