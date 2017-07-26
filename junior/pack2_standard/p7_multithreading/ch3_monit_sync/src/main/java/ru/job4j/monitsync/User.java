package ru.job4j.monitsync;

import java.util.Objects;
/**
 * Class User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-25
 */
class User implements Comparable<User> {
    /**
     * Идентификатор пользователя.
     */
    private int id;
    /**
     * Счёт пользователя.
     */
    private int amount;
    /**
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор.
     * @param id идентификатор пользователя.
     * @param amount счёт пользователя.
     */
    User(int id, int amount) {
        this.id = id;
        this.amount = amount;
        this.lock = new Object();
    }
    /**
     * Получает счёт пользователя.
     * @return счёт пользователя.
     */
    public int getAmount() {
        return this.amount;
    }
    /**
     * Получает идентификатор пользователя.
     * @return идентификатор пользователя.
     */
    public int getId() {
        return this.id;
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
        return Objects.hash(id, amount);
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
        if (this.id != user.id || this.amount != user.amount) {
            return false;
        }
        return true;
    }
    /**
     * Устанавливает счёт пользователя.
     * @param amount количество.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    /**
     * Генерирует строковое представление объект пользователя.
     * @return строковое представление объект пользователя.
     */
    @Override
    public String toString() {
        return String.format("User{id: %d, amount: %d}", this.getId(), this.getAmount());
    }
}
