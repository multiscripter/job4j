package ru.job4j.control;

/**
 * Класс FishListIsFullException реализует исключение "Рыбный список полон".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-10
 */
class FishListIsFullException extends RuntimeException {
    /**
     * Конструктор.
     */
    FishListIsFullException() {
        super("FishList is full");
    }
}
