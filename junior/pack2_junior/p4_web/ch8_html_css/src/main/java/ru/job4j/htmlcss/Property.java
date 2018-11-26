package ru.job4j.htmlcss;

/**
 * Класс Property реализует сущность Свойство.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-23
 */
public class Property {
    /**
     * Имя свойства.
     */
    private final String name;
    /**
     * Значение свойства.
     */
    private final String value;
    /**
     * Конструктор.
     * @param name имя свойства.
     * @param value значение свойства.
     */
    Property(final String name, final String value) {
        this.name = name;
        this.value = value;
    }
    /**
     * Получает имя свойства.
     * @return имя свойства.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Получает значение свойства.
     * @return значение свойства.
     */
    public String getValue() {
        return this.value;
    }
}