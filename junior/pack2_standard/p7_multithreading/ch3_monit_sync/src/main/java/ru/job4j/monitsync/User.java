package ru.job4j.monitsync;

import java.util.Objects;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Class User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-25
 */
@ThreadSafe
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
    @GuardedBy("lock")
    public int getAmount() {
        synchronized (this.lock) {
            return this.amount;
        }
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
    @GuardedBy("lock")
    @Override
    public int compareTo(User obj) {
        synchronized (this.lock) {
            return this.hashCode() - obj.hashCode();
        }
    }
    /**
     * Возвращает хэш-код объекта пользователя.
     * @return хэш-код объекта пользователя.
     */
    @GuardedBy("lock")
    @Override
    public int hashCode() {
        synchronized (this.lock) {
            return Objects.hash(id, amount);
        }
    }
    /**
     * Сравнивает объекты пользователя.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые, иначе false.
     */
    @GuardedBy("lock")
    @Override
    public boolean equals(Object obj) {
        synchronized (this.lock) {
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
    }
    /**
     * Устанавливает счёт пользователя.
     * @param amount количество.
     */
    @GuardedBy("lock")
    public void setAmount(int amount) {
        synchronized (this.lock) {
            this.amount = amount;
        }
    }
    /**
     * Генерирует строковое представление объект пользователя.
     * @return строковое представление объект пользователя.
     */
    @GuardedBy("lock")
    @Override
    public String toString() {
        synchronized (this.lock) {
            return String.format("User{id: %d, amount: %d}", this.getId(), this.getAmount());
        }
    }
}
