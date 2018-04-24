package ru.job4j.utils;

import java.util.Objects;
/**
 * Класс Property реализует сущность Свойство.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-12
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
     * Сравнивает объекты.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты равны. Иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Property other = (Property) obj;
        return this.name.equals(other.getName()) && this.value.equals(other.getValue());
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
    /**
     * Возвращает хэш-код.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.value);
    }
    /**
	 * Возвращает строковое представление.
	 * @return строковое представление.
	 */
	@Override
	public String toString() {
		return String.format("property[name: %s, value: %s]", this.name, this.value);
	}
}