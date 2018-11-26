package ru.job4j.profession;

import java.util.Date;

/**
 * Class Doctor реализует профессию врача.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-17
 */
public class Doctor extends Profession {
    /**
     * Количество пациентов.
     */
    private int patients;
    /**
     * Конструктор.
     * @param nameLast фамилия.
     * @param nameFirst имя.
     * @param gender пол.
     * @param birthDay дата рождения.
     * @param experience опыт.
     * @param degree учёная степень.
     */
    public Doctor(String nameLast, String nameFirst, Gender gender, Date birthDay, int experience, String degree) {
        super(nameLast, nameFirst, gender, birthDay, experience, degree);
        this.patients = 0;
    }
    /**
     * Возвращает проделанную работу.
     * @param patient объект пациента.
     * @return проделанная работа.
     */
    public String makeJob(Patient patient) {
        StringBuilder str = new StringBuilder();
        str.append("Врач ");
        str.append(this.getNameLast());
        str.append(" лечит пациента по фамилии ");
        str.append(patient.getNameLast());
        this.patients++;
        return str.toString();
    }
    /**
     * Возвращает количество пациентов.
     * @return количество пациентов.
     */
    public int getPatients() {
        return this.patients;
    }
}