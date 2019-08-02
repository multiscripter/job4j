package ru.job4j.models;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Класс User реализует сущность Пользователь.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2018-05-10
 */
@Entity
@Table(name = "users")
public class User implements IModel {
    /**
     * Активность пользователя.
     */
    @Column(name = "enabled")
    private boolean activity;
    /**
     * Идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Логин пользователя.
     */
    @Column(name = "name")
    private String name;
    /**
     * Хаш пароля пользователя.
     */
    @Column(name = "password")
    private String pass;
    /**
     * Конструктор без параметров.
     */
    public User() {
    }
    /**
     * Конструктор.
     * @param activity активность пользователя.
     * @param id идентификатор пользователя.
     * @param name логин пользователя.
     * @param pass хаш пароля пользователя.
     */
    public User(boolean activity, final Long id, String name, final String pass) {
        this.activity = activity;
        this.id = id;
        this.name = name;
        this.pass = pass;
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
        return this.activity == user.getActivity()
                && Objects.equals(this.id, user.getId())
                && Objects.equals(this.name, user.getName())
                && Objects.equals(this.pass, user.getPass());
    }
    /**
     *  Получет активность пользователя.
     * @return активность пользователя.
     */
    public boolean getActivity() {
        return this.activity;
    }
    /**
     * Получет идентификатор пользователя.
     * @return нидентификатор пользователя.
     */
    public Long getId() {
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
     * Получет хаш пароля пользователя.
     * @return хаш пароля пользователя.
     */
    public String getPass() {
        return this.pass;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумма объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.activity, this.id, this.name, this.pass);
    }
    /**
     * Устанавливает активность пользователя.
     * @param activity активность пользователя.
     */
    public void setActivity(boolean activity) {
        this.activity = activity;
    }
    /**
     * Устанавливает идентификатор пользователя.
     * @param id идентификатор пользователя.
     */
    public void setId(final Long id) {
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
     * Устанавливает хаш пароля пользователя.
     * @param pass хаш пароля пользователя.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    /**
     * Переопределяет метод toString().
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("User[id: %d, name: %s, activity: %b]", this.id, this.name, this.activity);
    }
}