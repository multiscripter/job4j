package ru.job4j.htmlcss;

import java.util.GregorianCalendar;
import java.util.Objects;
/**
 * Класс User реализует сущность Пользоваетель.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-08
 * @since 2017-11-06
 */
public class User {
    /**
	 * Город.
	 */
    private City city;
    /**
	 * Страна.
	 */
    private Country country;
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
	 */
	User() {
		this.id = 0;
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
		return !(this.id != user.getId() || !this.name.equals(user.getName()) || !this.login.equals(user.getLogin()) || !this.email.equals(user.getEmail()) || !this.date.equals(user.getDate()) || !this.role.equals(user.getRole()) || !this.country.equals(user.country) || !this.city.equals(user.city));
	}
    /**
	 * Получает город.
	 * @return город.
	 */
    public City getCity() {
        return this.city;
    }
    /**
	 * Получает идентификатор города.
	 * @return идентификатор города.
	 */
    public int getCityId() {
        return this.city.getId();
    }
    /**
	 * Получает название города.
	 * @return название города.
	 */
    public String getCityName() {
        return this.city.getName();
    }
    /**
	 * Получает страну.
	 * @return страну.
	 */
    public Country getCountry() {
        return this.country;
    }
    /**
	 * Получает идентификатор страны.
	 * @return идентификатор страны.
	 */
    public int getCountryId() {
        return this.country.getId();
    }
    /**
	 * Получает название страны.
	 * @return название страны.
	 */
    public String getCountryName() {
        return this.country.getName();
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
        return Objects.hash(this.id, this.name, this.login, this.email, this.date, this.role, this.country, this.city);
    }
    /**
	 * Устанавливает город.
	 * @param city город.
	 */
    public void setCity(City city) {
        this.city = city;
    }
    /**
	 * Устанавливает страну.
	 * @param country страна.
	 */
    public void setCountry(Country country) {
        this.country = country;
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
	 * Устанавливает роль.
	 * @param role роль.
	 */
    public void setRole(Role role) {
        this.role = role;
    }
	/**
	 * Возвращает строковое представление.
	 * @return строковое представление.
	 */
	@Override
	public String toString() {
		return String.format("user[id: %d, name: %s, login: %s, email: %s, date: %s, role: %s, country: %s, city: %s]", this.id, this.name, this.login, this.email, this.getDateStr(), this.role.toString(), this.country.toString(), this.city.toString());
	}
}