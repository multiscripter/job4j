package ru.job4j.generic;
/**
 * Класс User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-24
 */
class User extends Base {
    /**
     * Получает строковое представление объекта.
     * @return строковое представление объекта.
     */
    public String toString() {
        return String.format("User{name: %s}", this.getName());
    }
}