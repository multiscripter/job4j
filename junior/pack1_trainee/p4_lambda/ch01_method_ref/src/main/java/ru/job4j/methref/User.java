package ru.job4j.methref;

import java.util.Objects;
/**
 * Класс User реализует сущность Пользователь.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-17
 * @since 2018-12-17
 */
public class User {
    /**
     * Имя пользоваетля.
     */
    private final String name;
    /**
     * Конструктор.
     * @param name имя пользоваетля.
     */
    public User(String name) {
        this.name = name;
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
        return this.name.equals(user.getName());
    }
    /**
     * Получает имя.
     * @return имя.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Возвращает хэш-код.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
    /**
     * Возвращает строковое представление объекта.
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("User{name: %s}", name);
    }
}
