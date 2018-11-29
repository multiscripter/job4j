package ru.job4j.list2map;

/**
 * Класс User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-05-11
 * @since 2017-05-11
 */
public class User {
    /**
     * Итератор идентификатора.
     */
    private static int idrator = 0;
    /**
     * Идентификатор пользователя.
     */
    private final int id;
    /**
     * Имя пользователя.
     */
    private final String name;
    /**
     * Город пользователя.
     */
    private String city;
    /**
     * Конструктор.
     * @param name имя пользователя.
     * @param city город пользователя.
     */
    User(String name, String city) {
        this.id = ++User.idrator;
        this.name = name;
        this.city = city;
    }
    /**
     * Возвращает идентификатор пользователя.
     * @return идентификатор пользователя.
     */
    public int getId() {
        return this.id;
    }
}