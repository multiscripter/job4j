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
 * Класс SimpleLinkedSetTest тестирует класс SimpleLinkedSet<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-11
 * @since 2017-06-03
 */
public class SimpleLinkedSetTest {
    /**
     * Объект множества на массивах.
     */
    private SimpleLinkedSet<String> sls;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.sls = new SimpleLinkedSet<>();
        for (int a = 0; a < 9; a++) {
            this.sls.add("Foo" + a * 2);
        }
    }
    /**
     * Тестирует бинарый поиск. 100000 записей.
     */
    @Ignore
    @Test
    public void testBinarySearch100K() {
        SimpleLinkedSet<String> sls2 = new SimpleLinkedSet<>();
        System.out.print("testBinarySearch100K: ");
        long start = System.nanoTime();
        for (int a = 0; a < 100000; a++) {
            sls2.add("M" + a);
        }
        System.out.println(System.nanoTime() - start);
    }
    /**
     * Тестирует бинарый поиск.
     */
    @Test
    public void testBinarySearch19odd() {
        SimpleLinkedSet<String> sls2 = new SimpleLinkedSet<>();
        for (int a = 1; a < 20; a++) {
            sls2.add("Zaz" + a);
        }
        assertTrue(sls2.contains("Zaz17"));
    }
    /**
     * Тестирует бинарый поиск.
     */
    @Test
    public void testBinarySearch20odd() {
        SimpleLinkedSet<String> sls2 = new SimpleLinkedSet<>();
        for (int a = 1; a < 31; a++) {
            sls2.add("Zaz" + a);
        }
        assertTrue(sls2.contains("Zaz17"));
    }
    /**
     * Тестирует бинарый поиск.
     */
    @Test
    public void testBinarySearch19even() {
        SimpleLinkedSet<String> sls2 = new SimpleLinkedSet<>();
        for (int a = 1; a < 20; a++) {
            sls2.add("Zaz" + a);
        }
        assertTrue(sls2.contains("Zaz16"));
    }
    /**
     * Тестирует бинарый поиск.
     */
    @Test
    public void testBinarySearch20even() {
        SimpleLinkedSet<String> sls2 = new SimpleLinkedSet<>();
        for (int a = 1; a < 21; a++) {
            sls2.add("Zaz" + a);
        }
        assertTrue(sls2.contains("Zaz16"));
    }
    /**
     * Тестирует add(E e). Добавляемого объекта нет в множестве.
     */
    @Test
    public void testAddTrue() {
        assertTrue(this.sls.add("Baz"));
    }
    /**
     * Тестирует add(E e). Добавляемый объект уже есть в множестве.
     */
    @Test
    public void testAddFalse() {
        assertFalse(this.sls.add("Foo10"));
    }
    /**
     * Тестирует clear(). Проверка по размеру size().
     */
    @Test
    public void testClearBySize() {
        this.sls.clear();
        assertEquals(0, this.sls.size());
    }
    /**
     * Тестирует clear(). Проверка массива, плученного вызовом toArray().
     */
    @Test
    public void testClearByToArray() {
        this.sls.clear();
        assertEquals(0, this.sls.toArray().length);
    }
    /**
     * Тестирует contains(Object o). Проверяемый объект уже есть в множестве.
     */
    @Test
    public void testContainsTrue() {
        assertTrue(this.sls.contains("Foo10"));
    }
    /**
     * Тестирует contains(Object o). Проверяемого объекта нет в множестве.
     */
    @Test
    public void testContainsFalse() {
        assertFalse(this.sls.contains("Bar"));
    }
    /**
     * Тестирует isEmpty(). Множество не пустое.
     */
    @Test
    public void testIsEmptyFalse() {
        assertFalse(this.sls.isEmpty());
    }
    /**
     * Тестирует isEmpty(). Множество пустое.
     */
    @Test
    public void testIsEmptyTrue() {
        this.sls.clear();
        assertTrue(this.sls.isEmpty());
    }
    /**
     * Тестирует iterator().
     */
    @Test
    public void testIterator() {
        String[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        int size = 9;
        String[] result = new String[size];
        Iterator<String> iter = this.sls.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует remove(). Удаление элемента из начала.
     */
    @Test
    public void testRemoveFromBegin() {
        Object[] expected = {"Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        this.sls.remove("Foo0");
        Object[] result = this.sls.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует remove(). Удаление элемента из середины.
     */
    @Test
    public void testRemoveFromMiddle() {
        Object[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo10", "Foo12", "Foo14", "Foo16"};
        this.sls.remove("Foo8");
        Object[] result = this.sls.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует remove(). Удаление элемента из конца.
     */
    @Test
    public void testRemoveFromEnd() {
        Object[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14"};
        this.sls.remove("Foo16");
        Object[] result = this.sls.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        assertEquals(9, this.sls.size());
    }
    /**
     * Тестирует Object[] toArray().
     */
    @Test
    public void testToObjectArray() {
        Object[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        Object[] result = this.sls.toArray();
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует E[] toArray().
     */
    @Test
    public void testToEArray() {
        String[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo8", "Foo10", "Foo12", "Foo14", "Foo16"};
        String[] result = this.sls.toArray(new String[9]);
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует Iterator.remove().
     */
    @Test
    public void testIteratorRemove() {
        Object[] expected = {"Foo0", "Foo2", "Foo4", "Foo6", "Foo10", "Foo12", "Foo14", "Foo16"};
        Iterator<String> iter = this.sls.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals("Foo8")) {
                iter.remove();
            }
        }
        Object[] result = this.sls.toArray();
        assertArrayEquals(expected, result);
    }
}
