package ru.job4j.ui;

/**
 * Класс MenuOutException реализует сущность Исключение "Выход за пределы меню".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-04-24
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
