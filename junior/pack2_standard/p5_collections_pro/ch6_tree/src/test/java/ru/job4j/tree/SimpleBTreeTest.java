package ru.job4j.tree;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
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
     * Теструет конструктор SimpleBTree(E e).
     */
    @Test
    public void testConstructor() {
        sbt = new SimpleBTree<>("Foo");
        //Iterator<String> iter = sbt.iterator();
        //String result = iter.next();
        //assertEquals(expected, result);
    }
    /**
     * Теструет boolean contains(E e). Элемент содержится в коллекции.
     */
    @Test
    public void testContainsReturnTrue() {
        for (int a = 0; a < this.names.length; a++) {
            assertTrue(this.sbt.contains(this.names[a]));
        }
        //assertTrue(this.sbt.contains("J"));
    }
    /**
     * Теструет boolean contains(E e). Элемент отсутствует в коллекции.
     */
    @Test
    public void testContainsReturnFalse() {
        assertFalse(this.sbt.contains("Foo"));
    }
    /**
     * Теструет int size().
     */
    @Test
    public void testSize() {
        assertEquals(26, this.sbt.size());
    }
    /**
     * Теструет итератор.
     */
    @Test
    public void testIterator() {
        //StringBuilder sb = new StringBuilder();
        Iterator<String> iter = this.sbt.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
            //sb.append(iter.next());
        }
        //assertEquals(this.sb.toString(), sb.toString());
    }
}
