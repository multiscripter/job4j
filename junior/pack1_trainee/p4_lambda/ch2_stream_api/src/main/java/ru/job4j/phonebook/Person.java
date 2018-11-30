package ru.job4j.phonebook;

import java.util.Objects;
/**
 * Класс Person реализует сущность Человек.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-30
 * @since 2018-11-30
 */
public class Person {
    /**
     * Адрес.
     */
    private final String address;
    /**
     * Имя.
     */
    private final String name;
    /**
     * Телефон.
     */
    private final String phone;
    /**
     * Фамилия.
     */
    private final String surname;
    /**
     * Конструктор.
     * @param name имя.
     * @param surname фамилия.
     * @param phone телефон.
     * @param address адрес.
     */
    public Person(final String name, final String surname, final String phone, final String address) {
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.surname = surname;
    }
    /**
     * Сравнивает объекты заказов.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые. Иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Person p = (Person) obj;
        return this.address.equals(p.getAddress()) && this.name.equals(p.getName()) && this.phone.equals(p.getPhone()) && this.surname.equals(p.getSurname());
    }
    /**
     * Получает адрес.
     * @return адрес.
     */
    public String getAddress() {
        return address;
    }
    /**
     * Получает имя.
     * @return имя.
     */
    public String getName() {
        return name;
    }
    /**
     * Получает телефон.
     * @return телефон.
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Получает фамилию.
     * @return фамилия.
     */
    public String getSurname() {
        return surname;
    }
    /**
     * Возвращает хэш-код объекта.
     * @return хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.address, this.name, this.phone, this.surname);
    }
}
