package ru.job4j.tracker;

/**
 * Интрефэйс UserAction пользовательское действие.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-23
 */
interface UserAction {
    /**
     * Возвращает идентификатор действия пользователя.
     * @return идентификатор действия.
     */
    int key();
    /**
     * Выполняет действие, выбранное пользователем.
     * @param input объект ввода.
     * @param tracker объект трэкера.
     */
    void execute(Input input, Tracker tracker);
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    String info();
}
