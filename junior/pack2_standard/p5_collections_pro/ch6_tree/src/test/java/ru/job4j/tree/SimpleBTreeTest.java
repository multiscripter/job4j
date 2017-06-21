package ru.job4j.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleBTreeTest тестирует класс SimpleBTree.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-20
 */
public class SimpleBTreeTest {
    /**
     * Объект Простого бинарного дерева.
     */
    private SimpleBTree<String> sbt;
    /**
     * Массив слов.
     */
    private String[] names = {"V", "E", "O", "A", "M", "S", "C", "P", "F", "I", "D", "J", "T", "K", "Y", "H", "L", "N", "X", "Q", "U", "G", "B", "Z", "W", "R"};
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.sbt = new SimpleBTree<>();
        for (int a = 0; a < this.names.length; a++) {
            this.sbt.add(this.names[a]);
        }
    }
    /**
     * Тестирует конструктор SimpleBTree(E e).
     */
    @Test
    public void testConstructor() {
        sbt = new SimpleBTree<>("Foo");
        Iterator<String> iter = sbt.iterator();
        String result = iter.next();
        assertEquals("Foo", result);
    }
    /**
     * Тестирует boolean contains(E e). Элемент содержится в коллекции.
     */
    @Test
    public void testContainsReturnTrue() {
        for (int a = 0; a < this.names.length; a++) {
            assertTrue(this.sbt.contains(this.names[a]));
        }
        //assertTrue(this.sbt.contains("J"));
    }
    /**
     * Тестирует boolean contains(E e). Элемент отсутствует в коллекции.
     */
    @Test
    public void testContainsReturnFalse() {
        assertFalse(this.sbt.contains("Foo"));
    }
    /**
     * Тестирует boolean remove(E e). Левый узел без потомков.
     */
    @Test
    public void testRemoveLeftNoChildren() {
        String[] expected = {"V", "E", "A", "C", "B", "D", "O", "M", "F", "I", "H", "G", "J", "K", "L", "N", "S", "P", "Q", "R", "T", "U", "Y", "X", "Z"};
        this.sbt.remove("W");
        String[] result = new String[expected.length];
        Iterator<String> iter = this.sbt.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует boolean remove(E e). Правый узел без потомков.
     */
    @Test
    public void testRemoveRightNoChildren() {
        String[] expected = {"V", "E", "A", "C", "B", "D", "O", "M", "F", "I", "H", "G", "J", "K", "L", "N", "S", "P", "Q", "R", "T", "U", "Y", "X", "W"};
        this.sbt.remove("Z");
        String[] result = new String[expected.length];
        Iterator<String> iter = this.sbt.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует boolean remove(E e). Левый узел имеет одного потомка.
     */
    @Test
    public void testRemoveLeftHasOneChild() {
        String[] expected = {"V", "E", "A", "C", "B", "D", "O", "M", "F", "I", "H", "G", "J", "K", "L", "N", "S", "P", "Q", "R", "T", "U", "Y", "W", "Z"};
        this.sbt.remove("X");
        String[] result = new String[expected.length];
        Iterator<String> iter = this.sbt.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует boolean remove(E e). Правый узел имеет одного потомка.
     */
    @Test
    public void testRemoveRightHasOneChild() {
        String[] expected = {"V", "E", "A", "C", "B", "D", "O", "M", "F", "I", "H", "G", "J", "K", "L", "N", "S", "P", "Q", "R", "U", "Y", "X", "W", "Z"};
        this.sbt.remove("T");
        String[] result = new String[expected.length];
        Iterator<String> iter = this.sbt.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует boolean remove(E e). Левый узел имеет двух потомков.
     */
    @Test
    public void testRemoveLeftHasTwoChildren() {
        String[] expected = {"V", "A", "C", "B", "D", "O", "M", "F", "I", "H", "G", "J", "K", "L", "N", "S", "P", "Q", "R", "T", "U", "Y", "X", "W", "Z"};
        this.sbt.remove("E");
        String[] result = new String[expected.length];
        Iterator<String> iter = this.sbt.iterator();
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
            System.out.println("result[a]: " + result[a]);
        }
    }
    /**
     * Тестирует int size().
     */
    @Test
    public void testSize() {
        assertEquals(26, this.sbt.size());
    }
    /**
     * Тестирует итератор.
     */
    @Test
    public void testIterator() {
        String[] expected = {"V", "E", "A", "C", "B", "D", "O", "M", "F", "I", "H", "G", "J", "K", "L", "N", "S", "P", "Q", "R", "T", "U", "Y", "X", "W", "Z"};
        //StringBuilder sb = new StringBuilder();
        Iterator<String> iter = this.sbt.iterator();
        //while (iter.hasNext()) {
        //    System.out.println(iter.next());
            //sb.append(iter.next());
        //}
        //assertEquals(this.sb.toString(), sb.toString());
    }
    /**
     * Тестирует SimpleIterator.hasNext(). Следующий элемент есть.
     */
    @Test
    public void testIteratorHasNextReturnsTrue() {
        Iterator iter = this.sbt.iterator();
        assertTrue(iter.hasNext());
    }
    /**
     * Тестирует SimpleIterator.hasNext(). Следующего элемента нет.
     */
    @Test
    public void testIteratorHasNextReturnsFalse() {
        Iterator iter = this.sbt.iterator();
        while (iter.hasNext()) {
            iter.next();
        }
        assertFalse(iter.hasNext());
    }
    /**
     * Тестирует SimpleIterator.next(). Следующий элемент есть.
     */
    @Test
    public void testIteratorReturnsItem() {
        Iterator iter = this.sbt.iterator();
        assertEquals("V", iter.next());
    }
    /**
     * Тестирует NoSuchElementException, бросаемый из SimpleIterator.next(). Следующего элемента нет.
     */
    @Test(expected = NoSuchElementException.class)
    public void thenIteratorNextTryToGetNonexistentItem() {
        Iterator iter = this.sbt.iterator();
        while (true) {
            iter.next();
        }
    }
    /**
     * Тестирует SimpleIterator.remove().
     */
    @Test
    public void testIteratorRemove() {
        Iterator iter = this.sbt.iterator();
        iter.next();
        iter.next();
        iter.next();
        //iter.remove();
        //iter.next();
        //iter.remove();
        //assertEquals("B", iter.next());
    }
}
