package ru.job4j.profession;

import java.util.Date;

/**
 * Class Patient реализует сущность Пациент.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-18
 */
public class Patient extends Person {
    /**
     * диагнозы.
     */
    private String[] diagnosis;
    /**
     * Конструктор.
     * @param nameLast фамилия.
     * @param nameFirst имя.
     * @param gender пол.
     * @param birthDay дата рождения.
     * @param diagnosis диагноз.
     */
    public Patient(String nameLast, String nameFirst, Gender gender, Date birthDay, String diagnosis) {
        super(nameLast, nameFirst, gender, birthDay);
        this.diagnosis = new String[1];
        this.diagnosis[0] = diagnosis;
    }
    /**
     * Возвращает диагнозы.
     * @return диагнозы.
     */
    public String[] getDiagnosis() {
        return this.diagnosis;
    }
    /**
     * Устанавливает диагноз.
     * @param diagnosis диагноз.
     */
    public void setDiagnosis(String diagnosis) {
        String[] spec = new String[this.diagnosis.length + 1];
        spec = this.diagnosis;
        spec[spec.length - 1] = diagnosis;
        this.diagnosis = spec;
    }
}