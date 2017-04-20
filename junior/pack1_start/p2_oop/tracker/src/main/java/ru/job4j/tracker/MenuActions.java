package ru.job4j.tracker;

/**
 * Enum MenuActions реализует константы действий пользовательского интрефэйса трэкера.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-04-19
 */
enum MenuActions {
    /**
     * Добавить заявку.
     */
    ADD("Add new Item"),
    /**
    * Показать заявки.
    */
    SHOW("Show all items"),
    /**
     * Редактировать заявку.
     */
    EDIT("Edit item"),
    /**
     * Удалить заявку.
     */
    DELETE("Delete item"),
    /**
     * Найти заявку по идентификатору.
     */
    FINDBYID("Find item by Id"),
    /**
     * Найти заявку по имени.
     */
    FINDBYNAME("Find items by name"),
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
     * Конструктор.
     * @return значение перечисления.
     */
    public String getValue() {
        return this.value;
    }
}
