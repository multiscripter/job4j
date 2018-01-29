package ru.job4j.control.service;

import java.util.Objects;
/**
 * Класс Address реализует сущность Адрес.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-09
 * @since 2018-01-09
 */
public class Address {
    /**
	 * Улица, номер дома.
	 */
    private String addr;
    /**
	 * Населённый пункт.
	 */
    private String city;
    /**
	 * Страна.
	 */
    private String country;
    /**
	 * Идентификатор.
	 */
    private int id;
    /**
	 * Конструктор.
	 * @param id идентификатор.
	 * @param addr улица, номер дома.
     * @param city населённый пункт.
     * @param country страна.
	 */
    public Address(final int id, final String country, final String city, final String addr) {
        this.addr = addr;
        this.city = city;
        this.country = country;
        this.id = id;
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
        Address other = (Address) obj;
        if (!this.addr.equals(other.getAddr()) || !this.city.equals(other.getCity()) || !this.country.equals(other.getCountry()) || this.id != other.getId()) {
            return false;
        }
        return true;
    }
    /**
	 * Получает улицу, номер дома.
     * @return улица, номер дома.
	 */
    public String getAddr() {
        return this.addr;
    }
    /**
	 * Получает населённый пункт.
     * @return населённый пункт.
	 */
    public String getCity() {
        return this.city;
    }
    /**
	 * Получает страну.
     * @return страна.
	 */
    public String getCountry() {
        return this.country;
    }
    /**
	 * Получает идентификатор.
     * @return идентификатор.
	 */
    public int getId() {
        return this.id;
    }
    /**
     * Возвращает хэш-код.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.addr, this.city, this.country, this.id);
    }
    /**
	 * Устанавливает улицу, номер дома.
	 * @param addr улицу, номер дома.
	 */
	public void setAddr(final String addr) {
		this.addr = addr;
	}
    /**
	 * Устанавливает населённый пункт.
	 * @param city населённый пункт.
	 */
	public void setCity(final String city) {
		this.city = city;
	}
    /**
	 * Устанавливает страну.
	 * @param country страна.
	 */
	public void setCountry(final String country) {
		this.country = country;
	}
    /**
	 * Устанавливает идентификатор.
	 * @param id идентификатор.
	 */
	public void setId(final int id) {
		this.id = id;
	}
    /**
	 * Возвращает строковое представление.
	 * @return строковое представление.
	 */
	@Override
	public String toString() {
		return String.format("Address[id: %d, country: %s, city: %s, addr: %s]", this.id, this.country, this.city, this.addr);
	}
}