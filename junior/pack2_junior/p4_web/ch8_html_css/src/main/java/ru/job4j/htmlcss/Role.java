package ru.job4j.htmlcss;

import java.util.Objects;
/**
 * Класс Role реализует сущность Роль.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-08
 * @since 2017-12-08
 */
public class Role {
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
	Role(final int id, final String name) {
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
        Role role = (Role) obj;
		return !(this.id != role.getId() || !this.name.equals(role.getName()));
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
		return String.format("Role[id: %d, name: %s]", this.id, this.name);
	}
}