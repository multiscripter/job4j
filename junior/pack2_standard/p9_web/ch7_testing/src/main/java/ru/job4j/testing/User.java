package ru.job4j.testing;

//import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
/**
 * Класс User реализует сущность Пользоваетель.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 4
 * @since 2017-11-06
 */
public class User {
	/**
	 * Дата создания.
	 */
	private GregorianCalendar date;
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
	 * Роль.
	 */
    private Role role;
	/**
	 * Конструктор.
	 * @param id идентификатор.
	 * @param name имя.
	 * @param login логин.
	 * @param email адрес электронной почты.
	 * @param date дата создания.
     * @param pass пароль.
     * @param role роль.
	 */
	User(final int id, final String name, final String login, final String email, final GregorianCalendar date, final String pass, final Role role) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.email = email;
		this.date = date;
        this.pass = pass;
        this.role = role;
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
        if (this.id != user.getId() || !this.name.equals(user.getName()) || !this.login.equals(user.getLogin()) || !this.email.equals(user.getEmail()) || !this.date.equals(user.getDate()) || !this.role.equals(user.getRole())) {
            return false;
        }
        return true;
    }
	/**
	 * Получает дату создания.
	 * @return дата создания.
	 */
	public GregorianCalendar getDate() {
		return this.date;
	}
    /**
	 * Получает строковое представление даты создания.
	 * @return строковое представление даты создания.
	 */
	public String getDateStr() {
		//return String.format("%d-%02d-%02d", this.date.get(Calendar.YEAR), this.date.get(Calendar.MONTH), this.date.get(Calendar.DAY_OF_MONTH));
        return String.format("%1$tY-%1$tm-%1$td", this.date);
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
	 * Получает роль.
	 * @return роль.
	 */
    public Role getRole() {
        return this.role;
    }
    /**
	 * Получает идентификатор роли.
	 * @return идентификатор роли.
	 */
    public int getRoleId() {
        return this.role.getId();
    }
    /**
	 * Получает имя роли.
	 * @return имя роли.
	 */
    public String getRoleName() {
        return this.role.getName();
    }
	/**
     * Возвращает хэш-код.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.login, this.email, this.date, this.role);
    }
	/**
	 * Устанавливает дату создания.
	 * @param date дата создания.
	 */
	public void setDate(final GregorianCalendar date) {
		this.date = date;
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
		return String.format("user[id: %d, name: %s, login: %s, email: %s, date: %s, role: %s]", this.id, this.name, this.login, this.email, this.getDateStr(), this.role.toString());
	}
}