package tasks.lift;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
/**
 * Класс BoundedQueueTest тестирует класс BoundedQueue.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-18
 * @since 2018-01-18
 */
public class BoundedQueueTest {
    /**
     * Тестирует public boolean add(int floor) throws BoundedQueueIsFullException.
     */
    @Test
    public void testAdd() {
        BoundedQueue q = new BoundedQueue(10);
        assertTrue(q.add(5));
    }
}