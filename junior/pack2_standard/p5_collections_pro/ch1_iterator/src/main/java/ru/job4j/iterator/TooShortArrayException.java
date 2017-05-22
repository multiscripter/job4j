package ru.job4j.iterator;

/**
 * Класс TooShortArrayException реализует исключение "Слишком короткий массив".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-22
 */
class TooShortArrayException extends RuntimeException {
    /**
     * Конструктор.
     */
    TooShortArrayException() {
        super("Array is too short.");
    }
}