package ru.job4j.monitsync;

import java.util.Iterator;
import java.util.ListIterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс CustomConcurrentSimpleLinkedList расширяет ConcurrentSimpleLinkedList. Добавляет метод increment() для теста многопоточности.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-28
 */
@ThreadSafe
class CustomConcurrentSimpleLinkedList<E> extends ConcurrentSimpleLinkedList<E> {
    /**
     * Объект блокировки (монитора).
     */
    private Object lock = this;
    /**
     * Увеличивает значение элемента по указанному индексу на 1.
     * @param index индекс элемента.
     */
    @GuardedBy("lock")
    public void increment(int index) {
        synchronized (lock) {
            int tmp = (Integer) this.get(index);
            this.set(index, (E) new Integer(++tmp));
        }
    }
}
/**
 * Класс ConcurrentSimpleLinkedListTest тестирует класс ConcurrentSimpleLinkedList<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-27
 */
public class ConcurrentSimpleLinkedListTest {
    /**
     * Количество тридов.
     */
    private final int threadCount = 1000;
    /**
     * Количество итераций.
     */
    private final int iterations = 1000;
    /**
     * Массив тридов.
     */
    private Thread[] threads;
    /**
     * Объект связного списка.
     */
    private ConcurrentSimpleLinkedList<String> sll;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.sll = new ConcurrentSimpleLinkedList<>();
        for (int a = 0; a < 9; a++) {
            sll.add("Foo" + a);
        }
        this.threads = new Thread[this.threadCount];
    }
    /**
     * Тестирует add(E e).
     */
    @Test
    public void testAdd() {
        boolean result = this.sll.add("Baz");
        assertTrue(result);
    }
    /**
     * Тестирует add(int index, E e).
     */
    @Test
    public void testAddIndex() {
        String[] expected = {"Foo0", "Foo1", "Baz", "Foo2"};
        this.sll.add(2, "Baz");
        String[] result = new String[4];
        Iterator<String> iter = this.sll.iterator();
        int a = 0;
        while (a < 4) {
            result[a++] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует addFirst(E e).
     */
    @Test
    public void testAddFirst() {
        String expected = "Baz";
        this.sll.addFirst("Baz");
        String result = this.sll.get(0);
        assertEquals(expected, result);
    }
    /**
     * Тестирует addLast(E e).
     */
    @Test
    public void testAddLast() {
        String expected = "Baz";
        this.sll.addLast("Baz");
        String result = this.sll.get(9);
        assertEquals(expected, result);
    }
    /**
     * Тестирует element().
     */
    @Test
    public void testElement() {
        String expected = "Baz";
        this.sll.addFirst("Baz");
        String result = this.sll.element();
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 0.
     */
    @Test
    public void testGetIndex0() {
        String expected = "Foo0";
        String result = this.sll.get(0);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 1.
     */
    @Test
    public void testGetIndex1() {
        String expected = "Foo1";
        String result = this.sll.get(1);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 4.
     */
    @Test
    public void testGetIndex4() {
        String expected = "Foo4";
        String result = this.sll.get(4);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 5.
     */
    @Test
    public void testGetIndex5() {
        String expected = "Foo5";
        String result = this.sll.get(5);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 6.
     */
    @Test
    public void testGetIndex6() {
        String expected = "Foo6";
        String result = this.sll.get(6);
        assertEquals(expected, result);
    }
    /**
     * Тестирует get(). Index: 8.
     */
    @Test
    public void testGetIndex8() {
        String expected = "Foo8";
        String result = this.sll.get(8);
        assertEquals(expected, result);
    }
    /**
     * Тестирует getFirst().
     */
    @Test
    public void testGetFirst() {
        String expected = "Foo0";
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует getLast().
     */
    @Test
    public void testGetLast() {
        String expected = "Foo8";
        String result = this.sll.getLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует iterator().
     */
    @Test
    public void testIterator() {
        Iterator<String> iter = this.sll.iterator();
        String[] expected = {"Foo0", "Foo1", "Foo2", "Foo3", "Foo4", "Foo5", "Foo6", "Foo7", "Foo8"};
        String[] result = new String[9];
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует listIterator().
     */
    @Test
    public void testListIterator() {
        ListIterator<String> iter = this.sll.listIterator(4);
        String[] expected = {"Foo4", "Foo5", "Foo6", "Foo7", "Foo8"};
        String[] result = new String[5];
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует listIterator(). Обход в обратном порядке.
     */
    @Test
    public void testListIteratorReverse() {
        ListIterator<String> iter = this.sll.listIterator(this.sll.size() - 1);
        String[] expected = {"Foo8", "Foo7", "Foo6", "Foo5", "Foo4", "Foo3", "Foo2", "Foo1", "Foo0"};
        String[] result = new String[this.sll.size()];
        for (int a = 0; iter.hasPrevious(); a++) {
            result[a] = iter.previous();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует offer().
     */
    @Test
    public void testOffer() {
        boolean result = this.sll.offer("Baz");
        assertTrue(result);
    }
    /**
     * Тестирует offerFirst().
     */
    @Test
    public void testOfferFirst() {
        boolean result = this.sll.offerFirst("Baz");
        assertTrue(result);
    }
    /**
     * Тестирует offerLast().
     */
    @Test
    public void testOfferLast() {
        boolean result = this.sll.offerLast("Baz");
        assertTrue(result);
    }
    /**
     * Тестирует peek().
     */
    @Test
    public void testPeek() {
        String expected = "Foo0";
        String result = this.sll.peek();
        assertEquals(expected, result);
    }
    /**
     * Тестирует peekFirst().
     */
    @Test
    public void testPeekFirst() {
        String expected = "Foo0";
        String result = this.sll.peekFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует peekLast().
     */
    @Test
    public void testPeekLast() {
        String expected = "Foo8";
        String result = this.sll.peekLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует poll().
     */
    @Test
    public void testPoll() {
        String expected = "Foo1";
        this.sll.poll();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует pollFirst().
     */
    @Test
    public void testPollFirst() {
        String expected = "Foo1";
        this.sll.pollFirst();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует pollLast().
     */
    @Test
    public void testPollLast() {
        String expected = "Foo7";
        this.sll.pollLast();
        String result = this.sll.getLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует push().
     */
    @Test
    public void testPush() {
        String expected = "Bar";
        this.sll.push("Bar");
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует pop().
     */
    @Test
    public void testPop() {
        String expected = "Foo1";
        this.sll.pop();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует remove().
     */
    @Test
    public void testRemove() {
        String expected = "Foo1";
        this.sll.remove();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует remove(int index).
     */
    @Test
    public void testRemoveIndex() {
        this.sll.remove(6);
        ListIterator<String> iter = this.sll.listIterator(4);
        String[] expected = {"Foo4", "Foo5", "Foo7", "Foo8"};
        String[] result = new String[4];
        for (int a = 0; iter.hasNext(); a++) {
            result[a] = iter.next();
        }
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует removeFirst().
     */
    @Test
    public void testRemoveFirst() {
        String expected = "Foo1";
        this.sll.removeFirst();
        String result = this.sll.getFirst();
        assertEquals(expected, result);
    }
    /**
     * Тестирует removeLast().
     */
    @Test
    public void testRemoveLast() {
        String expected = "Foo7";
        this.sll.removeLast();
        String result = this.sll.getLast();
        assertEquals(expected, result);
    }
    /**
     * Тестирует set().
     */
    @Test
    public void testSet() {
        String[] expected = {"Baz0", "Baz1", "Baz2", "Baz3", "Baz4", "Baz5", "Baz6", "Baz7", "Baz8"};
        for (int a = 0; a < this.sll.size(); a++) {
            this.sll.set(a, "Baz" + a);
        }
        String[] result = this.sll.toArray(new String[this.sll.size()]);
        assertArrayEquals(expected, result);
    }
    /**
     * Многопоточно тестирует E get(int index) и E set(int index, E e).
     */
    @Test
    public void checkConcurrentGetSet() {
        CustomConcurrentSimpleLinkedList<Integer> csll = new CustomConcurrentSimpleLinkedList<>();
        int length = 9;
        Integer[] expected = new Integer[length];
        for (int a = 0; a < length; a++) {
            expected[a] = this.threadCount * this.iterations + a;
            csll.add(a);
        }
        for (int a = 0; a < this.threadCount; a++) {
            this.threads[a] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int b = 0; b < iterations; b++) {
                            for (int c = 0; c < csll.size(); c++) {
                                csll.increment(c);
                            }
                            Thread.currentThread().sleep(1);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            this.threads[a].setName("T" + a);
        }
        try {
            for (int a = 0; a < this.threadCount; a++) {
                this.threads[a].start();
            }
            for (int a = 0; a < this.threadCount; a++) {
                this.threads[a].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Integer[] result = csll.toArray(new Integer[length]);
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        assertEquals(9, this.sll.size());
    }
}
