package ru.job4j.tracker;

/**
 * Class MenuActions реализует константы действий пользовательского интрефэйса трэкера.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-19
 */
public final class MenuActions {
    /**
     * Добавить заявку.
     */
    static final int ADD = 0;
    /**
     * Показать заявки.
     */
    static final int SHOW = 1;
    /**
     * Редактировать заявку.
     */
    static final int EDIT = 2;
    /**
     * Удалить заявок.
     */
    static final int DELETE = 3;
    /**
     * Найти заявку по идентификатору.
     */
    static final int FINDBYID = 4;
    /**
     * Найти заявку по имени.
     */
    static final int FINDBYNAME = 5;
    /**
     * Выход из программы.
     */
    static final int EXIT = 6;
    /**
     * Конструктор.
     */
    private MenuActions() {
    }
}
