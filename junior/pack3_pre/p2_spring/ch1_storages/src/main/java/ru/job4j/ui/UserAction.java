package ru.job4j.ui;

import ru.job4j.services.Storage;
/**
 * Класс UserAction реализует методы взаимодействия с пользовательским действием.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-03
 * @since 2017-04-23
 */
abstract class UserAction implements IUserAction {
    /**
     * Константа действия.
     */
    private final MenuActions action;
    /**
     * Конструктор.
     * @param action константа действия.
     */
    UserAction(MenuActions action) {
        this.action = action;
    }
    /**
     * Возвращает идентификатор действия пользователя.
     * @return идентификатор действия.
     */
    public int key() {
        return this.action.ordinal();
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param io объект ввода-вывода.
     * @param storage объект хранилища.
     */
    public abstract void execute(IIO io, Storage storage);
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    public String info() {
        return String.format("%s. %s", this.action.ordinal(), this.action.getValue());
    }
}
