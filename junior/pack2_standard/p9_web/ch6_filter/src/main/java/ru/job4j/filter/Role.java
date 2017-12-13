package ru.job4j.filter;

/**
 * Класс Role реализует сущность Роль.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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