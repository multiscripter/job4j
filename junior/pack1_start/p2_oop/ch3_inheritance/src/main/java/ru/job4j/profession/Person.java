package ru.job4j.profession;

import java.util.Date;

/**
 * Class Person.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-18
 */
abstract class Person {
    /**
     * Фамилия.
     */
    private String nameLast;
    /**
     * Имя.
     */
    private final String nameFirst;
    /**
     * Пол.
     */
    private Gender gender;
    /**
     * Дата рождения.
     */
    private final Date birthDay;
    /**
     * Конструктор.
     * @param nameLast фамилия.
     * @param nameFirst имя.
     * @param gender пол.
     * @param birthDay дата рождения.
     */
    Person(String nameLast, String nameFirst, Gender gender, Date birthDay) {
        this.nameLast = nameLast;
        this.nameFirst = nameFirst;
        this.gender = gender;
        this.birthDay = birthDay;
    }
    /**
     * Возвращает фамилию.
     * @return фамилия.
     */
    public String getNameLast() {
        return this.nameLast;
    }
    /**
     * Устанавливает фамилию.
     * @param nameLast новая фамилия.
     */
    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }
    /**
     * Возвращает имя.
     * @return имя.
     */
    public String getNameFirst() {
        return this.nameFirst;
    }
    /**
     * Возвращает пол.
     * @return пол.
     */
    public Gender getGender() {
        return this.gender;
    }
    /**
     * Возвращает дату рождения.
     * @return дата рождения.
     */
    public Date getBirthDay() {
        return this.birthDay;
    }
}