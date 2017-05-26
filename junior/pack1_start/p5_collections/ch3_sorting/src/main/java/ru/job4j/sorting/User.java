package ru.job4j.sorting;

import java.util.Objects;
/**
 * Class User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
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
     * @param obj объект пользователя.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(User obj) {
        return this.getAge() - obj.getAge();
    }
    /**
     * Возвращает хэш-код объекта пользователя.
     * @return хэш-код объекта пользователя.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
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
        if (this.name != user.name || this.age != user.age) {
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
        return String.format("User{name: %s, age: %d}", this.getName(), this.getAge());
    }
}
