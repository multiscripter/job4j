package ru.job4j.profession;

import java.util.Date;

/**
 * Class Profession базовый для всех профессий.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-17
 */
abstract class Profession extends Person {
    /**
     * Образование.
     */
    private String[] education;
    /**
     * Специальность.
     */
    private String[] speciality;
    /**
     * Опыт.
     */
    private int experience;
    /**
     * Учёная степень.
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
        super(nameLast, nameFirst, gender, birthDay);
        this.experience = experience;
        this.degree = degree;
    }
    /**
     * Возвращает образования.
     * @return образования.
     */
    public String[] getEducation() {
        return this.education;
    }
    /**
     * Устанавливает образование.
     * @param education образование.
     */
    public void setEducation(String education) {
        String[] edu = new String[this.education.length + 1];
        edu = this.education;
        edu[edu.length - 1] = education;
        this.education = edu;
    }
    /**
     * Возвращает специальности.
     * @return специальности.
     */
    public String[] getSpeciality() {
        return this.speciality;
    }
    /**
     * Устанавливает специальность.
     * @param speciality специальность.
     */
    public void setSpeciality(String speciality) {
        String[] spec = new String[this.speciality.length + 1];
        spec = this.speciality;
        spec[spec.length - 1] = speciality;
        this.speciality = spec;
    }
    /**
     * Возвращает опыт.
     * @return опыт.
     */
    public int getExperience() {
        return this.experience;
    }
    /**
     * Устанавливает опыт.
     * @param days опыт.
     */
    public void setExperience(int days) {
        this.experience = this.experience + days;
    }
    /**
     * Возвращает учёную степень.
     * @return учёная степень.
     */
    public String getDegree() {
        return this.degree;
    }
    /**
     * Устанавливает учёную степень.
     * @param degree учёная степень.
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }
}