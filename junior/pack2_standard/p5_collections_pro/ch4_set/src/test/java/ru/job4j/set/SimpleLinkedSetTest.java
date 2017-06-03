package ru.job4j.set;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleLinkedSetTest тестирует класс SimpleLinkedSet<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
        Iterator iter = this.sls.iterator();
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
