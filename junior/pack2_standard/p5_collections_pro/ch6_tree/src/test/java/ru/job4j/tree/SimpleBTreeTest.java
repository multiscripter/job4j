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
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.sbt = new SimpleBTree<>();
        String[] names = {"Victor", "Echo", "Oscar", "Alpha", "Mike", "Sierra", "Charlie", "Papa", "Foxtrot", "India", "Delta", "Juliet", "Tango", "Kilo", "Yankee", "Hotel", "Lima", "November", "Xray", "Quebec", "Uniform", "Golf", "Bravo", "Zulu", "Whiskey", "Romeo"};
        for (int a = 0; a < names.length; a++) {
            this.sbt.add(names[a]);
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
        assertTrue(this.sbt.contains("Juliet"));
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
