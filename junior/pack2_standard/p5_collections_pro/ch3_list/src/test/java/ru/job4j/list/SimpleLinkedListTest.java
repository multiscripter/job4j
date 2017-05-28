package ru.job4j.list;

//import java.util.Iterator;
import org.junit.Test;
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс SimpleLinkedListTest тестирует класс SimpleLinkedList<E>.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-28
 */
public class SimpleLinkedListTest {
    /**
     * Тестирует add(E e).
     */
    @Test
    public void testAdd() {
        SimpleLinkedList<String> sll = new SimpleLinkedList<>();
        boolean result = sll.add("Foo");
        assertTrue(result);
    }
}
