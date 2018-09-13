package ru.job4j.mathfunc;

import java.util.HashMap;
/**
 * Класс FuncProps реализует абстракцию Свойства математической функции.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-13
 * @since 2018-09-13
 */
public class FuncProps {
    /**
     * Карта со свойствами функции.
     */
    private HashMap<String, Double> props = new HashMap<>();
    /**
     * Возвращает значение свойства функции.
     * @param name имя совйства функции.
     * @return значение свойства функции.
     */
    public double getProp(String name) {
        return this.props.get(name);
    }
    /**
     * Устанавливает свойство математической функции.
     * @param name имя свойства математической функции.
     * @param value значение свойства математической функции.
     */
    public  void setProp(String name, Double value) {
        this.props.put(name, value);
    }
}
