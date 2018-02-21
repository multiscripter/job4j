package tasks.lift;

/**
 * LiftAction имплементирует интерфэйс ILiftAction.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
abstract class LiftAction implements ILiftAction {
    /**
     * Константа действия.
     */
    private final AdminActions action;
    /**
     * Конструктор.
     * @param action константа действия.
     */
    LiftAction(AdminActions action) {
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
     * @param lift лифт.
     * @param io источник ввода и вывода данных.
     */
    public abstract void execute(final Lift lift, final AbstractLiftIO io);
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    public String info() {
        return String.format("%s. %s", this.action.ordinal(), this.action.getValue());
    }
}
