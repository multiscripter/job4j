package tasks.lift;

/**
 * ILiftAction объявляет интерфейс Действие лифта.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
 * @since 2018-01-22
 */
interface ILiftAction {
    /**
     * Получает команду в виде строки для отправки на сервер лифта.
     * @param client абстрактный лифт.
     * @param io источник ввода и вывода данных.
     * @return команду в виде строки.
     */
    String execute(final LiftClient client, final AbstractLiftIO io);
    /**
     * Получает сообщение по коду.
     * @param code код сообщения.
     * @return сообщение.
     */
    String getMessage(final int code);
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    String info();
}