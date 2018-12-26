package ru.job4j.tracker;

/**
 * Интрефэйс UserAction пользовательское действие.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-23
 */
interface IUserAction {
    /**
     * Возвращает идентификатор действия пользователя.
     * @return идентификатор действия.
     */
    int key();
    /**
     * Выполняет действие, выбранное пользователем.
     * @param input объект ввода.
     * @param tracker объект трэкера.
     * @throws Exception исключение.
     */
    void execute(Input input, Tracker tracker) throws Exception;
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    String info();
}
