package ru.job4j.bank;

import java.util.Objects;
/**
 * Class User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-15
 */
class User implements Comparable<User> {
    /**
     * Имя пользователя.
     */
    private String name;
    /**
     * Номер паспорта пользователя.
     */
    private String passport;
    /**
     * Конструктор.
     * @param name имя пользователя.
     * @param passport номер паспорта пользователя.
     */
    User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }
    /**
     * Возвращает имя пользователя.
     * @return имя пользователя.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Устанавливает имя пользователя.
     * @param name имя пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Возвращает номер паспорта пользователя.
     * @return номер паспорта пользователя.
     */
    public String getPassport() {
        return this.passport;
    }
    /**
     * Устанавливает номер паспорта пользователя.
     * @param passport номер паспорта пользователя.
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }
    /**
     * Сравнивает два объекта пользователя.
     * @param obj объект пользователя.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(User obj) {
        return this.hashCode() - obj.hashCode();
    }
    /**
     * Возвращает хэш-код объекта пользователя.
     * @return хэш-код объекта пользователя.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, passport);
    }
    /**
     * Сравнивает объекты пользователя.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые, иначе false.
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
        if (this.name != user.name || this.passport != user.passport) {
            return false;
        }
        return true;
    }
    /**
     * Генерирует строковое представление объект пользователя.
     * @return строковое представление объект пользователя.
     */
    @Override
    public String toString() {
        return String.format("User{name: %s, passport: %s}", this.getName(), this.getPassport());
    }
}
