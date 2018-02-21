package tasks.lift;

import java.util.HashMap;
/**
 * LiftAction имплементирует интерфэйс ILiftAction.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-12
 * @since 2018-01-22
 */
abstract class LiftAction implements ILiftAction {
    /**
     * Константа действия.
     */
    private final LiftActions action;
    /**
     * Массив сообщений.
     */
    private final HashMap<Integer, String> messages;
    /**
     * Конструктор.
     * @param action константа действия.
     * @param messages хэш-карта сообщений.
     */
    LiftAction(LiftActions action, HashMap<Integer, String> messages) {
        this.action = action;
        this.messages = messages;
    }
    /**
     * Получает сообщение по коду.
     * @param code код сообщения.
     * @return сообщение.
     */
    public String getMessage(final int code) {
        return this.messages.get(code);
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
     * @param client клиент лифта.
     * @param io источник ввода и вывода данных.
     * @return команду в виде строки.
     */
    public abstract String execute(final LiftClient client, final AbstractLiftIO io);
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    public String info() {
        return String.format("%s. %s", this.action.ordinal(), this.action.getValue());
    }
}
