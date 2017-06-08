package ru.job4j.map;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
/**
 * Класс User реализует сущность Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-07
 */
class User implements Comparable<User> {
    /**
     * Дата рождения.
     */
    private GregorianCalendar birthday;
    /**
     * Количество детей.
     */
    private int children;
    /**
     * Имя пользователя.
     */
    private String name;
    /**
     * Конструктор.
     * @param name имя пользователя.
     * @param birthday дата рождения.
     */
    User(String name, GregorianCalendar birthday) {
        this.name = name;
        this.birthday = birthday;
        this.children = 0;
    }
    /**
     * Конструктор.
     * @param name имя пользователя.
     * @param birthday дата рождения.
     * @param children количество детей.
     */
    User(String name, GregorianCalendar birthday, int children) {
        this.name = name;
        this.birthday = birthday;
        this.children = children;
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
     * Сравнивает объекты пользователя.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые. Иначе false.
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
        if (this.name != user.name || this.children != user.children || this.birthday.equals(user.getBirthday())) {
            return false;
        }
        return true;
    }
    /**
     * Получает строку с датой рождения.
     * @return строка с датой рождения.
     */
    public String getBirthday() {
        StringBuilder str = new StringBuilder();
        str.append(this.birthday.get(Calendar.YEAR));
        str.append("-");
        str.append(this.birthday.get(Calendar.MONTH));
        str.append("-");
        str.append(this.birthday.get(Calendar.DATE));
        return str.toString();
    }
    /**
     * Получает объект даты рождения.
     * @return объект даты рождения.
     */
    public GregorianCalendar getBirthdayObj() {
        return this.birthday;
    }
    /**
     * Получает количество детей.
     * @return количество детей.
     */
    public int getChildren() {
        return this.children;
    }
    /**
     * Получает имя пользователя.
     * @return имя пользователя.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Возвращает хэш-код объекта пользователя.
     * @return хэш-код объекта пользователя.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.children, this.birthday);
    }
    /**
     * Устанавливает дату рождения.
     * @param birthday дата рождения.
     */
    public void setBirthday(GregorianCalendar birthday) {
        this.birthday = birthday;
    }
    /**
     * Устанавливает количество детей.
     * @param children количество детей.
     */
    public void setChildren(int children) {
        this.children = children;
    }
    /**
     * Устанавливает имя пользователя.
     * @param name имя пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Генерирует строковое представление объект пользователя.
     * @return строковое представление объект пользователя.
     */
    @Override
    public String toString() {
        return String.format("User{name: %s, birthday: %s, children: %d}", this.getName(), this.getBirthday().toString(), this.getChildren());
    }
}
