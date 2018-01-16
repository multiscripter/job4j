package ru.job4j.control.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
/**
 * Класс User реализует сущность Пользоваетель.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-12
 * @since 2018-01-09
 */
public class User {
    /**
	 * Адрес.
	 */
    private Address address;
	/**
	 * Идентификатор.
	 */
	private int id;
	/**
	 * Логин.
	 */
	private String login;
    /**
	 * Список музыкальных стилей.
	 */
    private LinkedList<MusicType> mtypes;
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
	public User() {
		this.id = 0;
        this.mtypes = new LinkedList<>();
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
        if (this.id != user.getId() || !this.login.equals(user.getLogin()) || !this.role.equals(user.getRole())  || !this.address.equals(user.getAddress())) {
            return false;
        }
        return true;
    }
    /**
	 * Получает адрес.
	 * @return адрес.
	 */
    public Address getAddress() {
        return this.address;
    }
    /**
	 * Получает улицу, номер дома.
	 * @return улица, номер дома.
	 */
    public String getAddr() {
        return this.address.getAddr();
    }
    /**
	 * Получает идентификатор адреса.
	 * @return идентификатор адреса.
	 */
    public int getAddressId() {
        return this.address.getId();
    }
    /**
	 * Получает страну.
	 * @return страна.
	 */
    public String getCountry() {
        return this.address.getCountry();
    }
    /**
	 * Получает город.
	 * @return город.
	 */
    public String getCity() {
        return this.address.getCity();
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
	 * Получает список музыкальных стилей.
	 * @return список музыкальных стилей.
	 */
	public LinkedList<MusicType> getMusicTypes() {
		return this.mtypes;
	}
    /**
	 * Получает список музыкальных названий стилей в виде строки.
	 * @return список музыкальных названий стилей в виде строки.
	 */
    public String getMusicTypeNamesAsString() {
        Iterator<MusicType> iter = this.mtypes.iterator();
        LinkedList<String> strs = new LinkedList<>();
        while (iter.hasNext()) {
            MusicType cur = iter.next();
            strs.add(cur.getName());
        }
        return String.join(", ", strs);
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
        return Objects.hash(this.address, this.id, this.login, this.role);
    }
    /**
	 * Устанавливает адрес.
	 * @param address адрес.
	 */
    public void setAddress(Address address) {
        this.address = address;
    }
    /**
	 * Устанавливает идентификатор адреса.
	 * @param id идентификатор адреса.
	 */
    public void setAddressId(int id) {
        this.address.setId(id);
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
	public void setLogin(String login) {
		this.login = login;
	}
    /**
	 * Устанавливает список музыкальных стилей.
	 * @param mtypes список музыкальных стилей.
	 */
	public void setMusicTypes(LinkedList<MusicType> mtypes) {
		this.mtypes = mtypes;
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
		return String.format("user[id: %d, login: %s, music types: [%s], role: %s, address: %s]", this.id, this.login, this.mtypes.toString(), this.role.toString(), this.address.toString());
	}
}