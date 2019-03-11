package ru.job4j.addresses;

import java.util.Objects;
/**
 * Класс Address реализует сущность Адрес.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-11
 * @since 2019-03-11
 */
public class Address {
    /**
     * Номер квартиры.
     */
    private int apartment;
    /**
     * Название населённого пункта.
     */
    private String city;
    /**
     * Номер дома.
     */
    private int home;
    /**
     * Название улицы.
     */
    private String street;
    /**
     * Конструктор.
     * @param apartment номер квартиры.
     * @param home номер дома.
     * @param street название улицы.
     * @param city название населённого пункта.
     */
    public Address(int apartment, int home, String street, String city) {
        this.apartment = apartment;
        this.home = home;
        this.street = street;
        this.city = city;
    }
    /**
     * Сравнивает объекты.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые. Иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address a = (Address) obj;
        return this.home == a.getHome() && this.apartment == a.getApartment() && this.city.equals(a.getCity()) && this.street.equals(a.getStreet());
    }
    /**
     * Получает номер квартиры.
     * @return номер квартиры.
     */
    public int getApartment() {
        return this.apartment;
    }
    /**
     * Получает название населённого пункта.
     * @return название населённого пункта.
     */
    public String getCity() {
        return this.city;
    }
    /**
     * Получает номер дома.
     * @return номер дома.
     */
    public int getHome() {
        return this.home;
    }
    /**
     * Получает название улицы.
     * @return название улицы.
     */
    public String getStreet() {
        return this.street;
    }
    /**
     * Возвращает хэш-код объекта.
     * @return хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.apartment, this.city, this.home, this.street);
    }
    /**
     * Устанавливает номер квартиры.
     * @param apartment номер квартиры.
     */
    public void setApartment(int apartment) {
        this.apartment = apartment;
    }
    /**
     * Устанавливает название населённого пункта.
     * @param city название населённого пункта.
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Устанавливает номер дома.
     * @param home номер дома.
     */
    public void setHome(int home) {
        this.home = home;
    }
    /**
     * Устанавливает название улицы.
     * @param street название улицы.
     */
    public void setStreet(String street) {
        this.street = street;

    }
}
