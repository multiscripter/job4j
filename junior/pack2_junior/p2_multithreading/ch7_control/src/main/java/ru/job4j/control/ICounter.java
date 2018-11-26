package ru.job4j.control;

/**
 * ICounter объявляет интерфэйс Счётчик.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-28
 */
public interface ICounter {
    /**
     * Декрементирует счётчик.
     * @return декрементированное значение счётчика.
     */
    int decrement();
    /**
     * Инкрементирует счётчик.
     * @return инкрементированное значение счётчика.
     */
    int increment();
    /**
     * Возвращает значение счётчика.
     * @return значение счётчика.
     */
    int getVal();
    /**
     * Устанавливает значение счётчика.
     * @param val значение счётчика.
     */
    void setVal(int val);
}