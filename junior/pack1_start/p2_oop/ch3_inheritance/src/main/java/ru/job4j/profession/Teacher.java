package ru.job4j.profession;

import java.util.Date;

/**
 * Class Teacher реализует профессию преподавателя.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-17
 */
public class Teacher extends Profession {
    /**
     * Количество учеников.
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
     * @param learner объект учащегося.
     * @return проделанная работа.
     */
    public String makeJob(Learner learner) {
        StringBuilder str = new StringBuilder();
        str.append("Преподаватель ");
        str.append(this.getNameLast());
        str.append(" учит учащегося по фамилии ");
        str.append(learner.getNameLast());
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