package tasks.lift;

/**
 * ILiftAction объявляет интрефейс Действие лифта.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
interface ILiftAction {
    /**
     * Возвращает идентификатор действия пользователя.
     * @return идентификатор действия.
     */
    int key();
    /**
     * Выполняет действие, выбранное пользователем.
     * @param lift лифт.
     * @param io источник ввода и вывода данных.
     */
    void execute(final Lift lift, final AbstractLiftIO io);
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    String info();
}
