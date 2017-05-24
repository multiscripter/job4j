package ru.job4j.generic;
/**
 * Класс Role реализует сущность Роль.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-24
 */
class Role extends Base {
    /**
     * Получает строковое представление объекта.
     * @return строковое представление объекта.
     */
    public String toString() {
        return String.format("Role{name: %s}", this.getName());
    }
}