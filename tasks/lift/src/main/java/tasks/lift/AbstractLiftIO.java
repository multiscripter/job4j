package tasks.lift;

/**
 * AbstractLiftIO объявляет сущность ввода-вывода лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
 * @since 2018-01-22
 */
abstract class AbstractLiftIO implements ILiftInput, ILiftOutput {
    /**
     * Закрывает сущность ввода-вывода.
     */
    public abstract void close();
    /**
     * Проверяет если ли данные для чтения.
     * @return true если есть данные для чтения. Иначе false.
     */
    public abstract boolean hasData();
    /**
     * Читает данные из источника ввода.
     * @return номер этажа.
     */
    @Override
    public abstract String read();
    /**
     * Отправляет сообщение в источник вывода.
     * @param message сообщение, отправляемое в источник вывода.
     */
    @Override
    public abstract void write(final String message);
}