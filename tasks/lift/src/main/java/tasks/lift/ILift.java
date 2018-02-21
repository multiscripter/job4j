package tasks.lift;

/**
 * ILift объявляет сущность лифт.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-26
 * @since 2018-01-26
 */
interface ILift {
    /**
     * Возвращает текущий этаж.
     * @return текущий этаж.
     */
    int getCurrentFloor();
}