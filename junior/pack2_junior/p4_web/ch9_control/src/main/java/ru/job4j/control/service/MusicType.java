package ru.job4j.control.service;

import java.util.Objects;
/**
 * Класс MusicType реализует сущность Музыкальный стиль.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-10
 * @since 2018-01-10
 */
public class MusicType {
    /**
	 * Идентификатор.
	 */
    private int id;
    /**
	 * Имя.
	 */
    private String name;
    /**
	 * Конструктор.
	 * @param id идентификатор.
	 * @param name имя.
	 */
	public MusicType(final int id, final String name) {
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
        MusicType other = (MusicType) obj;
        if (this.id != other.getId() || !this.name.equals(other.getName())) {
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
	 * Получает имя.
	 * @return имя.
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
	 * Устанавливает имя.
	 * @param name имя.
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
		return String.format("MusicType[id: %d, name: %s]", this.id, this.name);
	}
}