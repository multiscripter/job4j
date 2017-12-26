package ru.job4j.htmlcss;

import java.util.Arrays;
/**
 * Класс Filter реализует сущность Фильтр.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-25
 */
public class Filter {
    /**
     * Название фильтра.
     */
    private String name;
    /**
     * Массив значений фильтра.
     */
    private String[] value;
    /**
     * Конструктор.
     * @param name название фильтра.
     * @param value массив значений фильтра.
     */
    Filter(String name, String[] value) {
        this.name = name;
        this.value = value;
    }
    /**
     * Получает название фильтра.
     * @return название фильтра.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Получает массив значений фильтра.
     * @return массив значений фильтра.
     */
    public String[] getFilters() {
        return this.value;
    }
    /**
     * Получает массив значений фильтра.
     * @return массив значений фильтра.
     */
    public String getValue() {
        return String.join("\",\"", Arrays.asList(this.value));
    }
}