package ru.job4j.profession;

import java.util.Date;

/**
 * Class Learner реализует сущность Учащийся.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-18
 */
public class Learner extends Person {
    /**
     * Специальность.
     */
    private String[] speciality;
    /**
     * Конструктор.
     * @param nameLast фамилия.
     * @param nameFirst имя.
     * @param gender пол.
     * @param birthDay дата рождения.
     * @param speciality специальность.
     */
    public Learner(String nameLast, String nameFirst, Gender gender, Date birthDay, String speciality) {
        super(nameLast, nameFirst, gender, birthDay);
        this.speciality = new String[1];
        this.speciality[0] = speciality;
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
}