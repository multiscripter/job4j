package ru.job4j.models;

import java.util.Objects;
/**
 * Класс User реализует сущность Пользователь.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-10
 * @since 2018-05-10
 */
public class User implements IModel {
    /**
     * Идентификатор пользователя.
     */
    private int id;
    /**
     * Логин пользователя.
     */
    private String name;
    /**
     * Конструктор без параметров.
     */
    public User() {
    }
    /**
     * Конструктор.
     * @param id идентификатор пользователя.
     * @param name логин пользователя.
     */
    public User(final int id, String name) {
        this.id = id;
        this.name = name;
    }
    /**
     * Переопределяет метод equals().
     * @return true если объекты равны. Иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return this.id == user.getId() && this.name.equals(user.getName());
    }
    /**
     * Получет идентификатор пользователя.
     * @return нидентификатор пользователя.
     */
    public int getId() {
        return this.id;
    }
    /**
     * Получет логин пользователя.
     * @return логин пользователя.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумма объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
    /**
     * Устанавливает идентификатор пользователя.
     * @param id идентификатор пользователя.
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * Устанавливает логин пользователя.
     * @param name логин пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Переопределяет метод toString().
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("User[id: %d, name: %s]", this.id, this.name);
    }
}