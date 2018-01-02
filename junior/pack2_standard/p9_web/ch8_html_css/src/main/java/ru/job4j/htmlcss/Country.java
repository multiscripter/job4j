package ru.job4j.htmlcss;

import java.util.Objects;
/**
 * Класс Country реализует сущность Страна.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-19
 */
public class Country {
    /**
	 * Идентификатор.
	 */
    private int id;
    /**
	 * Название.
	 */
    private String name;
    /**
	 * Конструктор.
	 * @param id идентификатор.
	 * @param name название.
	 */
	Country(final int id, final String name) {
		this.id = id;
		this.name = name;
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
        Country country = (Country) obj;
        if (this.id != country.getId() || !this.name.equals(country.getName())) {
            return false;
        }
        return true;
    }
    /**
	 * Получает идентификатор.
	 * @return идентификатор.
	 */
	public int getId() {
		return this.id;
	}
    /**
	 * Получает название.
	 * @return название.
	 */
	public String getName() {
		return this.name;
	}
    /**
     * Возвращает хэш-код.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
    /**
	 * Устанавливает идентификатор.
	 * @param id идентификатор.
	 */
	public void setId(final int id) {
		this.id = id;
	}
    /**
	 * Устанавливает название.
	 * @param name название.
	 */
	public void setName(final String name) {
		this.name = name;
	}
    /**
	 * Возвращает строковое представление.
	 * @return строковое представление.
	 */
	@Override
	public String toString() {
		return String.format("Country[id: %d, name: %s]", this.id, this.name);
	}
}