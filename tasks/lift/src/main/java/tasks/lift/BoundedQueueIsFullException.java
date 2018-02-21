package tasks.lift;

/**
 * Класс BoundedQueueIsFullException реализует исключение "Очереь заполнена".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-18
 * @since 2018-01-18
 */
class BoundedQueueIsFullException extends RuntimeException {
    /**
     * Конструктор.
     */
    BoundedQueueIsFullException() {
        super("Queue is full");
    }
}