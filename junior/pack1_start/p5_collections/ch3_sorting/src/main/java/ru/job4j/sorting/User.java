package ru.job4j.sorting;

/**
 * Class User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-12
 */
class User implements Comparable<User> {
    /**
     * Имя пользователя.
     */
    private String name;
    /**
     * Возраст пользователя.
     */
    private int age;
    /**
     * Конструктор.
     * @param name имя пользователя.
     * @param age возраст пользователя.
     */
    User(String name, int age) {
        this.name = name;
        this.age = age;
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
    public void getName(String name) {
        this.name = name;
    }
    /**
     * Возвращает возраст пользователя.
     * @return возраст пользователя.
     */
    public int getAge() {
        return this.age;
    }
    /**
     * Устанавливает возраст пользователя.
     * @param age возраст пользователя.
     */
    public void getAge(int age) {
        this.age = age;
    }
    /**
     * Сравнивает два объекта пользователя.
     * @param age возраст пользователя.
     */
    @Override
    public int compareTo(User o) {
        return this.getAge() - o.getAge();
    }
}