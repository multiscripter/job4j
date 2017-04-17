package ru.job4j.profession;

import java.util.Date;

/**
 * Class Profession базовый для всех профессий.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-17
 */
abstract class Profession {
    /**
     * Фамилия профессионала.
     */
    private String nameLast;
    /**
     * Имя профессионала.
     */
    private final String nameFirst;
    /**
     * пол профессионала.
     */
    private Gender gender;
    /**
     * День рождения профессионала.
     */
    private final Date birthDay;
    /**
     * Образование профессионала.
     */
    private String[] education;
    /**
     * Специальность профессионала.
     */
    private String[] speciality;
    /**
     * Опыт профессионала.
     */
    private int experience;
    /**
     * Учёная степень профессионала.
     */
    private String degree;
    /**
     * Конструктор.
     * @param nameLast фамилия.
     * @param nameFirst имя.
     * @param gender пол.
     * @param birthDay дата рождения.
     * @param experience опыт.
     * @param degree учёная степень.
     */
    Profession(String nameLast, String nameFirst, Gender gender, Date birthDay, int experience, String degree) {
        this.nameLast = nameLast;
        this.nameFirst = nameFirst;
        this.gender = gender;
        this.birthDay = birthDay;
        this.experience = experience;
        this.degree = degree;
    }
    /**
     * Возвращает фамилию профессионала.
     * @return фамилия.
     */
    public String getNameLast() {
        return this.nameLast;
    }
    /**
     * Устанавливает фамилию профессионала.
     * @param nameLast новая фамилия.
     */
    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }
    /**
     * Возвращает имя профессионала.
     * @return имя.
     */
    public String getNameFirst() {
        return this.nameFirst;
    }
    /**
     * Возвращает пол профессионала.
     * @return пол.
     */
    public Gender getGender() {
        return this.gender;
    }
    /**
     * Возвращает дату рождения профессионала.
     * @return дата рождения.
     */
    public Date getBirthDay() {
        return this.birthDay;
    }
    /**
     * Возвращает образования профессионала.
     * @return образования.
     */
    public String[] getEducation() {
        return this.education;
    }
    /**
     * Устанавливает образование профессионала.
     * @param education образование.
     */
    public void setEducation(String education) {
        String[] edu = new String[this.education.length + 1];
        edu = this.education;
        edu[edu.length - 1] = education;
        this.education = edu;
    }
    /**
     * Возвращает специальности профессионала.
     * @return специальности.
     */
    public String[] getSpeciality() {
        return this.speciality;
    }
    /**
     * Устанавливает специальность профессионала.
     * @param speciality специальность.
     */
    public void setSpeciality(String speciality) {
        String[] spec = new String[this.speciality.length + 1];
        spec = this.speciality;
        spec[spec.length - 1] = speciality;
        this.speciality = spec;
    }
    /**
     * Возвращает опыт профессионала.
     * @return опыт.
     */
    public int getExperience() {
        return this.experience;
    }
    /**
     * Устанавливает опыт профессионала.
     * @param days опыт.
     */
    public void setExperience(int days) {
        this.experience = this.experience + days;
    }
    /**
     * Возвращает учёную степень профессионала.
     * @return учёная степень.
     */
    public String getDegree() {
        return this.degree;
    }
    /**
     * Устанавливает учёную степень профессионала.
     * @param degree учёная степень.
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }
    /**
     * Возвращает проделанную работу профессионала.
     * @param arg аргумент.
     * @return проделанная работа.
     */
    abstract String makeJob(String arg);
}