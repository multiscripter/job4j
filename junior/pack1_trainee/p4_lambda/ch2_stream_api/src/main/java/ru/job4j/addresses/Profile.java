package ru.job4j.addresses;

import java.util.Objects;
/**
 * Класс Profile реализует сущность Анкета.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-11
 * @since 2019-03-11
 */
public class Profile {
    /**
     * Адрес клиента.
     */
    private Address address;
    /**
     * Конструктор.
     * @param address адрес клиента.
     */
    public Profile(Address address) {
        this.address = address;
    }
    /**
     * Сравнивает объекты.
     * @param o целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые. Иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile profile = (Profile) o;
        return Objects.equals(this.address, profile.getAddress());
    }
    /**
     * Возвращает хэш-код объекта.
     * @return хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.address);
    }
    /**
     * Получает адрес клиента.
     * @return адрес клиента.
     */
    public Address getAddress() {
        return address;
    }
    /**
     * Устанавливает адрес клиента.
     * @param address адрес клиента.
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
