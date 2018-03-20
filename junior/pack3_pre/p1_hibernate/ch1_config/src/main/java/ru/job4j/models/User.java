package ru.job4j.models;

import java.util.Date;
import java.util.Objects;
/**
 * Класс User реализует сущность Пользоваетель.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-09
 * @since 2018-03-05
 */
public class User {
	/**
	 * Дата создания.
	 */
	private Date created;
	/**
	 * Адрес электронной почты.
	 */
	private String email;
	/**
	 * Идентификатор.
	 */
	private int id;
	/**
	 * Логин.
	 */
	private String login;
	/**
	 * Имя.
	 */
	private String name;
    /**
	 * Хэш пароля.
	 */
    private String pass;
    /**
	 * Конструктор.
	 */
	public User() {
	}
	/**
	 * Конструктор.
	 * @param id идентификатор.
	 * @param name имя.
	 * @param login логин.
	 * @param email адрес электронной почты.
	 * @param created дата создания.
     * @param pass пароль.
	 */
	public User(final int id, final String name, final String login, final String email, final Date created, final String pass) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.created = created;
        this.pass = pass;
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
        User user = (User) obj;
        return !(this.id != user.getId() || !this.name.equals(user.getName()) || !this.login.equals(user.getLogin()) || !this.email.equals(user.getEmail()) || !this.created.equals(user.getCreated()));
    }
	/**
	 * Получает дату создания.
	 * @return дата создания.
	 */
	public Date getCreated() {
        return this.created;
	}
	/**
	 * Получает адрес электронной почты.
	 * @return адрес электронной почты.
	 */
	public String getEmail() {
        return this.email;
	}
	/**
	 * Получает идентификатор.
	 * @return идентификатор.
	 */
	public int getId() {
        return this.id;
	}
	/**
	 * Получает логин.
	 * @return логин.
	 */
	public String getLogin() {
        return this.login;
	}
	/**
	 * Получает имя.
	 * @return имя.
	 */
	public String getName() {
        return this.name;
	}
    /**
	 * Получает хэш пароля.
	 * @return хэш пароля.
	 */
    public String getPass() {
        return this.pass;
    }
	/**
     * Возвращает хэш-код.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.login, this.email, this.created);
    }
	/**
	 * Устанавливает дату создания.
	 * @param created дата создания.
	 */
	public void setCreated(final Date created) {
        this.created = created;
	}
	/**
	 * Устанавливает адрес электронной почты.
	 * @param email адрес электронной почты.
	 */
	public void setEmail(final String email) {
        this.email = email;
	}
	/**
	 * Устанавливает идентификатор.
	 * @param id идентификатор.
	 */
	public void setId(int id) {
        this.id = id;
	}
	/**
	 * Устанавливает логин.
	 * @param login логин.
	 */
	public void setLogin(final String login) {
        this.login = login;
	}
	/**
	 * Устанавливает имя.
	 * @param name имя.
	 */
	public void setName(final String name) {
        this.name = name;
	}
    /**
	 * Устанавливает пароль.
	 * @param pass пароль.
	 */
    public void setPass(String pass) {
        this.pass = pass;
    }
	/**
	 * Возвращает строковое представление.
	 * @return строковое представление.
	 */
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("user[");
        sb.append("id: ");
        sb.append(this.id);
        sb.append(", name: ");
        sb.append(this.name);
        sb.append(", login: ");
        sb.append(this.login);
        sb.append(", email: ");
        sb.append(this.email);
        sb.append(", created: ");
        sb.append(this.created);
        sb.append("]");
        return sb.toString();
	}
}