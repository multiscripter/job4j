package ru.job4j.list2map;

/**
 * Класс User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
    /**
     * Возвращает идентификатор пользователя.
     * @return идентификатор пользователя.
     */
    public int getBoxedId() {
        return new Integer(this.id);
    }
    /**
     * Возвращает имя пользователя.
     * @return имя пользователя.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Возвращает город пользователя.
     * @return город пользователя.
     */
    public String getCity() {
        return this.city;
    }
    /**
     * Устанавливает город пользователя.
     * @param city город пользователя.
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Возвращает текущий объект пользователя.
     * @return this текущий объект пользователя.
     */
    public User returnSelf() {
        return this;
    }
}