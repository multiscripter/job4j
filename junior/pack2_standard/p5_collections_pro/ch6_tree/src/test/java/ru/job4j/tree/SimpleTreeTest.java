package ru.job4j.tree;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleTreeTest тестирует класс SimpleTree.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-20
 */
public class SimpleTreeTest {
    /**
     * Объект Простого дерева.
     */
    private SimpleTree<User> st;
    /**
     * Строка из объектов, помещаемыхв дерево.
     */
    private StringBuilder sb;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.st = new SimpleTree<>();
        this.sb = new StringBuilder();
        User root = new User("Rep1", new GregorianCalendar(2016, 1, 11), 0);
        this.sb.append(new User("Rep1", new GregorianCalendar(2016, 1, 11), 0));
        this.st.setRoot(root);
        this.st.add(root, new User("Rep1-Rep1", new GregorianCalendar(1980, 2, 20), 1));
        this.sb.append(new User("Rep1-Rep1", new GregorianCalendar(1980, 2, 20), 1));
        this.st.add(root, new User("Rep1-Rep2", new GregorianCalendar(1999, 1, 1), 0));
        this.sb.append(new User("Rep1-Rep2", new GregorianCalendar(1999, 1, 1), 0));
        User rep1Rep3 = new User("Rep1-Rep3", new GregorianCalendar(1955, 5, 5), 1);
        this.sb.append(new User("Rep1-Rep3", new GregorianCalendar(1955, 5, 5), 1));
        this.st.add(root, rep1Rep3);
        this.st.add(rep1Rep3, new User("Rep1-Rep3-Rep1", new GregorianCalendar(1955, 5, 5), 1));
        this.sb.append(new User("Rep1-Rep3-Rep1", new GregorianCalendar(1955, 5, 5), 1));
        this.st.add(rep1Rep3, new User("Rep1-Rep3-Rep2", new GregorianCalendar(1955, 5, 5), 1));
        this.sb.append(new User("Rep1-Rep3-Rep2", new GregorianCalendar(1955, 5, 5), 1));
        User rep1Rep4 = new User("Rep1-Rep4", new GregorianCalendar(1955, 5, 5), 1);
        this.sb.append(new User("Rep1-Rep4", new GregorianCalendar(1955, 5, 5), 1));
        this.st.add(root, rep1Rep4);
        this.st.add(rep1Rep4, new User("Rep1-Rep4-Rep1", new GregorianCalendar(1985, 5, 5), 5));
        this.sb.append(new User("Rep1-Rep4-Rep1", new GregorianCalendar(1985, 5, 5), 5));
        User rep1Rep4Rep2 = new User("Rep1-Rep4-Rep2", new GregorianCalendar(1986, 6, 6), 5);
        this.sb.append(new User("Rep1-Rep4-Rep2", new GregorianCalendar(1986, 6, 6), 5));
        this.st.add(rep1Rep4, rep1Rep4Rep2);
        this.st.add(rep1Rep4Rep2, new User("Rep1-Rep4-Rep2-Rep1", new GregorianCalendar(1980, 2, 20), 1));
        this.sb.append(new User("Rep1-Rep4-Rep2-Rep1", new GregorianCalendar(1980, 2, 20), 1));
        this.st.add(rep1Rep4Rep2, new User("Rep1-Rep4-Rep2-Rep2", new GregorianCalendar(1980, 2, 20), 1));
        this.sb.append(new User("Rep1-Rep4-Rep2-Rep2", new GregorianCalendar(1980, 2, 20), 1));
        this.st.add(rep1Rep4Rep2, new User("Rep1-Rep4-Rep2-Rep3", new GregorianCalendar(1980, 2, 20), 1));
        this.sb.append(new User("Rep1-Rep4-Rep2-Rep3", new GregorianCalendar(1980, 2, 20), 1));
        this.st.add(root, new User("Rep1-Rep4", new GregorianCalendar(1980, 2, 20), 1));
        this.sb.append(new User("Rep1-Rep4", new GregorianCalendar(1980, 2, 20), 1));
        this.st.add(root, new User("Rep1-Rep5", new GregorianCalendar(1980, 2, 20), 1));
        this.sb.append(new User("Rep1-Rep5", new GregorianCalendar(1980, 2, 20), 1));
    }
    /**
     * Теструет конструктор SimpleTree(E root).
     */
    @Test
    public void testConstructorRoot() {
        User expected = new User("Rep1-Rep4", new GregorianCalendar(1980, 2, 20), 1);
        SimpleTree<User> st = new SimpleTree<>(new User("Rep1-Rep4", new GregorianCalendar(1980, 2, 20), 1));
        Iterator<User> iter = st.iterator();
        User result = iter.next();
        assertEquals(expected, result);
    }
    /**
     * Теструет boolean contains(E e). Элемент содержится в коллекции.
     */
    @Test
    public void testContainsReturnTrue() {
        assertTrue(this.st.contains(new User("Rep1-Rep4-Rep2-Rep2", new GregorianCalendar(1980, 2, 20), 1)));
    }
    /**
     * Теструет boolean contains(E e). Элемент отсутствует в коллекции.
     */
    @Test
    public void testContainsReturnFalse() {
        assertFalse(this.st.contains(new User("nonexistent", new GregorianCalendar(1000, 1, 1), 1)));
    }
    /**
     * Теструет int size().
     */
    @Test
    public void testSize() {
        assertEquals(14, this.st.size());
    }
    /**
     * Теструет итератор.
     */
    @Test
    public void testIterator() {
        StringBuilder sb = new StringBuilder();
        Iterator<User> iter = this.st.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next());
        }
        assertEquals(this.sb.toString(), sb.toString());
    }
    /**
     * Теструет SimpleIterator.hasNext(). Следующий элемент есть.
     */
    @Test
    public void testIteratorHasNextReturnsTrue() {
        Iterator<User> iter = this.st.iterator();
        assertTrue(iter.hasNext());
    }
    /**
     * Теструет SimpleIterator.hasNext(). Следующего элемента нет.
     */
    @Test
    public void testIteratorHasNextReturnsFalse() {
        Iterator<User> iter = this.st.iterator();
        while (iter.hasNext()) {
            iter.next();
        }
        assertFalse(iter.hasNext());
    }
    /**
     * Теструет SimpleIterator.next(). Следующий элемент есть.
     */
    @Test
    public void testIteratorReturnsItem() {
        User expected = new User("Rep1", new GregorianCalendar(2016, 1, 11), 0);
        Iterator<User> iter = this.st.iterator();
        User result = iter.next();
        assertEquals(expected, result);
    }
    /**
     * Теструет NoSuchElementException, бросаемый из SimpleIterator.next(). Следующего элемента нет.
     */
    @Test(expected = NoSuchElementException.class)
    public void thenIteratorNextTryToGetNonexistentItem() {
        Iterator<User> iter = this.st.iterator();
        while (true) {
            iter.next();
        }
    }
    /**
     * Теструет SimpleIterator.remove().
     */
    @Test
    public void testIteratorRemove() {
        User expected = new User("Rep1-Rep3-Rep1", new GregorianCalendar(1955, 5, 5), 1);
        Iterator<User> iter = this.st.iterator();
        iter.next();
        iter.next();
        iter.next();
        iter.remove();
        iter.next();
        iter.remove();
        User result = iter.next();
        assertEquals(expected, result);
    }
    /**
     * Теструет IllegalStateException, бросаемый из SimpleIterator.remove(). Не вызван next().
     */
    @Test(expected = IllegalStateException.class)
    public void thenIteratorNextHasNotYetBeenCalled() {
        Iterator<User> iter = this.st.iterator();
        iter.remove();
        iter.remove();
    }
    /**
     * Теструет IllegalStateException, бросаемый из SimpleIterator.remove(). Remove() вызван дважды.
     */
    @Test(expected = IllegalStateException.class)
    public void thenIteratorRemoveCalledTwice() {
        Iterator<User> iter = this.st.iterator();
        iter.next();
        iter.next();
        iter.remove();
        iter.remove();
    }
}
