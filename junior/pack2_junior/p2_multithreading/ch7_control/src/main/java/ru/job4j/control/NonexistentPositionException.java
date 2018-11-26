package ru.job4j.control;

/**
 * Класс NonexistentPositionException реализует исключение "Несуществующая позиция".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-28
 */
class NonexistentPositionException extends RuntimeException {
    /**
     * Конструктор.
     * @param pos объект координат позиции.
     */
    NonexistentPositionException(Position pos) {
        super(String.format("Position is not exists. coords: [%d, %d]", pos.getX(), pos.getY()));
    }
}
