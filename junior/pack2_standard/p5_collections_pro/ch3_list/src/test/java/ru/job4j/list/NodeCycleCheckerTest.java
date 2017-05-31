package ru.job4j.list;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Класс NodeCycleCheckerTest тестирует класс NodeCycleChecker<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-31
 */
public class NodeCycleCheckerTest {
    /**
     * Тестирует hasCycle(). Замкнутый список из 4 узлов.
     */
    @Test
    public void testHasCycleReturnTrueFourNodes() {
        Node<Integer> first = new Node(1);
        Node<Integer> second = new Node(2);
        Node<Integer> third = new Node(3);
        Node<Integer> four = new Node(4);
        first.setNext(second);
        second.setNext(third);
        third.setNext(four);
        four.setNext(first);
        NodeCycleChecker<Integer> ncc = new NodeCycleChecker();
        assertTrue(ncc.hasCycle(second));
    }
    /**
     * Тестирует hasCycle(). Замкнутый список из 2 узла.
     */
    @Test
    public void testHasCycleReturnTrueTwoNodes() {
        Node<Integer> first = new Node(1);
        Node<Integer> second = new Node(2);
        first.setNext(second);
        second.setNext(first);
        NodeCycleChecker<Integer> ncc = new NodeCycleChecker();
        assertTrue(ncc.hasCycle(second));
    }
    /**
     * Тестирует hasCycle(). Ссылка на самого себя.
     */
    @Test
    public void testHasCycleReturnTrueOneNode() {
        Node<Integer> first = new Node(1);
        first.setNext(first);
        NodeCycleChecker<Integer> ncc = new NodeCycleChecker();
        assertTrue(ncc.hasCycle(first));
    }
    /**
     * Тестирует hasCycle(). Замкнутый список из 4 узлов. 4->2.
     */
    @Test
    public void testHasCycleReturnTrueFourNodes4refTo2() {
        Node<Integer> first = new Node(1);
        Node<Integer> second = new Node(2);
        Node<Integer> third = new Node(3);
        Node<Integer> four = new Node(4);
        first.setNext(second);
        second.setNext(third);
        third.setNext(four);
        four.setNext(second);
        NodeCycleChecker<Integer> ncc = new NodeCycleChecker();
        assertTrue(ncc.hasCycle(first));
    }
    /**
     * Тестирует hasCycle(). Незамкнутый список из 4 узлов.
     */
    @Test
    public void testHasCycleReturnFalseFourNodes() {
        Node<Integer> first = new Node(1);
        Node<Integer> second = new Node(2);
        Node<Integer> third = new Node(3);
        Node<Integer> four = new Node(4);
        first.setNext(second);
        second.setNext(third);
        third.setNext(four);
        NodeCycleChecker<Integer> ncc = new NodeCycleChecker();
        assertFalse(ncc.hasCycle(first));
    }
    /**
     * Тестирует hasCycle(). Незамкнутый список из 2 узла.
     */
    @Test
    public void testHasCycleReturnFalseTwoNodes() {
        Node<Integer> first = new Node(1);
        Node<Integer> second = new Node(2);
        first.setNext(second);
        NodeCycleChecker<Integer> ncc = new NodeCycleChecker();
        assertFalse(ncc.hasCycle(first));
    }
    /**
     * Тестирует hasCycle(). Незамкнутый список из 1 узла.
     */
    @Test
    public void testHasCycleReturnFalseOneNode() {
        Node<Integer> first = new Node(1);
        NodeCycleChecker<Integer> ncc = new NodeCycleChecker();
        assertFalse(ncc.hasCycle(first));
    }
}
