package ru.job4j.ui;

/**
 * Перечисление MenuActions реализует константы действий пользовательского интрефейса хранилища.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-04
 * @since 2017-04-19
 */
enum MenuActions {
    /**
     * Добавить заявку.
     */
    ADD("Add new user"),
    /**
    * Показать заявки.
    */
    SHOW("Show all users"),
    /**
     * Выход из программы.
     */
    EXIT("Exit program");
    /**
     * Значение перечисления.
     */
    private String value;
    /**
     * Конструктор.
     * @param value значение перечисления.
     */
    MenuActions(String value) {
        this.value = value;
    }
    /**
     * Возвращает значение перечисления.
     * @return значение перечисления.
     */
    public String getValue() {
        return this.value;
    }
}
