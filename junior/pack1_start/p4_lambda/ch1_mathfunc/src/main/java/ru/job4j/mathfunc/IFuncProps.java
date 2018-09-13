package ru.job4j.mathfunc;

/**
 * Интерфейс свойств математической функции.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-12
 * @since 2018-09-12
 */
public interface IFuncProps {
    /**
     * Получает значение свойства математической функции.
     * @param name имя совйства математической функции.
     * @return значение свойства математической функции.
     */
    double getProp(String name);
    /**
     * Устанавливает свойство математической функции.
     * @param name имя свойства математической функции.
     * @param value значение свойства математической функции.
     */
    void setProp(String name, Double value);
}
