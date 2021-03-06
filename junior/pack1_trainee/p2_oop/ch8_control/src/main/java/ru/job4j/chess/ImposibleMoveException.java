package ru.job4j.chess;

/**
 * Класс ImposibleMoveException реализует исключение "Движение невозможно".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-03
 */
class ImposibleMoveException extends RuntimeException {
    /**
     * Конструктор.
     * @param src начальная поле хода.
     * @param dest конечное поле хода.
     */
    ImposibleMoveException(String src, String dest) {
        super(String.format("Imposible movement. src: %s, dest: %s", src, dest));
    }
}
