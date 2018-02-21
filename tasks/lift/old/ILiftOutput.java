package tasks.lift;

/**
 * ILiftOutput объявляет интрефэйс вывода.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-18
 * @since 2018-01-18
 */
public interface ILiftOutput {
    /**
     * Отправляет сообщение в источник вывода.
     * @param message сообщение, отправляемое в источник вывода.
     */
    void write(final String message);
}