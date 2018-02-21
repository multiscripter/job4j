package tasks.lift;

/**
 * AbstractLiftIO объявляет класс ввода-вывода.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
abstract class AbstractLiftIO implements ILiftInput, ILiftOutput {
    /**
     * Читает данные из источника ввода.
     * @return номер этажа.
     */
    public abstract String read();
    /**
     * Отправляет сообщение в источник вывода.
     * @param message сообщение, отправляемое в источник вывода.
     */
    public abstract void write(final String message);
}