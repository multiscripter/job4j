package ru.job4j.ui;

import ru.job4j.services.Storage;
/**
 * Интерфейс UserAction объявляет методы взаимодействия с пользовательским действием.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-03
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
     * @param io объект ввода-вывода.
     * @param storage объект хранилища.
     */
    void execute(IIO io, Storage storage);
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    String info();
}
