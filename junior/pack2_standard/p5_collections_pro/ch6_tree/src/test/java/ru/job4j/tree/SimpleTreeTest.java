package ru.job4j.tree;

import java.util.GregorianCalendar;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс SimpleTreeTest тестирует класс SimpleTree.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-19
 */
public class SimpleTreeTest {
    /**
     * Объект Простого дерева.
     */
    private SimpleTree<User> st;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.st = new SimpleTree<>();
        User root = new User("Replicant10", new GregorianCalendar(2016, 1, 11), 0);
        this.st.add(root);
        this.st.add(root, new User("Replicant20", new GregorianCalendar(1980, 2, 20), 1));
        this.st.add(root, new User("Replicant30", new GregorianCalendar(1999, 1, 1), 0));
        this.st.add(root, new User("Replicant40", new GregorianCalendar(1955, 5, 5), 1));
        User twin = new User("Replicant40", new GregorianCalendar(1955, 5, 5), 1);
        this.st.add(root, twin);
        this.st.add(twin, new User("Replicant50", new GregorianCalendar(1985, 5, 5), 5));
        this.st.add(twin, new User("Replicant60", new GregorianCalendar(1986, 6, 6), 5));
        this.st.add(root, new User("Replicant11", new GregorianCalendar(1980, 2, 20), 1));
        //System.out.println("this.st.size(): " + this.st.size());
    }
    /**
     * Теструет итератор.
     */
    @Test
    public void testIterator() {
        Iterator<User> iter = this.st.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().toString());
        }
    }
}
