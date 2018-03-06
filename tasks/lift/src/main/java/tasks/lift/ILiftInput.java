package tasks.lift;

/**
 * ILiftInput объявляет интрефэйс ввода.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-18
 * @since 2018-01-18
 */
public interface ILiftInput {
    /**
     * Читает данные из источника ввода.
     * @return номер этажа.
     */
    String read();
}