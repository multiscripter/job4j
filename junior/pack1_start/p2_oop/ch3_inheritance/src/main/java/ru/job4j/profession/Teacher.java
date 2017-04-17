package ru.job4j.profession;

import java.util.Date;

/**
 * Class Teacher реализует профессию преподавателя.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-17
 */
public class Teacher extends Profession {
    /**
     * Количество учеников преподавателя.
     */
    private int learners;
    /**
     * Конструктор.
     * @param nameLast фамилия.
     * @param nameFirst имя.
     * @param gender пол.
     * @param birthDay дата рождения.
     * @param experience опыт.
     * @param degree учёная степень.
     */
    public Teacher(String nameLast, String nameFirst, Gender gender, Date birthDay, int experience, String degree) {
        super(nameLast, nameFirst, gender, birthDay, experience, degree);
        this.learners = 0;
    }
    /**
     * Возвращает проделанную работу.
     * @param nameLast фамилия ученика.
     * @return проделанная работа.
     */
    @Override
    public String makeJob(String learnerNameLast) {
        StringBuilder str = new StringBuilder();
        str.append("Преподаватель ");
        str.append(this.getNameLast());
        str.append(" учит ученика по фамилии ");
        str.append(learnerNameLast);
        this.learners++;
        return str.toString();
    }
    /**
     * Возвращает количество учеников.
     * @return количество учеников.
     */
    public int getLearners() {
        return this.learners;
    }
}