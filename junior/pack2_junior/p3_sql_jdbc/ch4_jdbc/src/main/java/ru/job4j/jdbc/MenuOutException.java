package ru.job4j.jdbc;

/**
 * Класс MenuOutException реализует сущность пользовательского интрефэйса трэкера.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-24
 */
public class MenuOutException extends RuntimeException {
    /**
     * Конструктор.
     * @param msg сообщение об ошибке.
     */
    public MenuOutException(String msg) {
        super(msg);
    }
}
