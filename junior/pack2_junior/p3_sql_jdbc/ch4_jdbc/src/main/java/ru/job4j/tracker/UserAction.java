package ru.job4j.tracker;

/**
 * Класс UserAction имплементирует интерфэйс UserAction.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
     * @param input объект ввода.
     * @param tracker объект трэкера.
     * @throws Exception исключение.
     */
    public abstract void execute(Input input, Tracker tracker) throws Exception;
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    public String info() {
        return String.format("%s. %s", this.action.ordinal(), this.action.getValue());
    }
}
