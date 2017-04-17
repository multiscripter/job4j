package ru.job4j.profession;

import java.util.Date;

/**
 * Class Engineer реализует профессию инженера.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-17
 */
public class Engineer extends Profession {
    /**
     * Количество количество проектов инженера.
     */
    private int projects;
    /**
     * Конструктор.
     * @param nameLast фамилия.
     * @param nameFirst имя.
     * @param gender пол.
     * @param birthDay дата рождения.
     * @param experience опыт.
     * @param degree учёная степень.
     */
    public Engineer(String nameLast, String nameFirst, Gender gender, Date birthDay, int experience, String degree) {
        super(nameLast, nameFirst, gender, birthDay, experience, degree);
        this.projects = 0;
    }
    /**
     * Возвращает проделанную работу.
     * @param projectName название проекта.
     * @return проделанная работа.
     */
    @Override
    public String makeJob(String projectName) {
        StringBuilder str = new StringBuilder();
        str.append("Инженер ");
        str.append(this.getNameLast());
        str.append(" работает над проектом ");
        str.append(projectName);
        this.projects++;
        return str.toString();
    }
    /**
     * Возвращает количество проектов.
     * @return количество проектов.
     */
    public int getProjects() {
        return this.projects;
    }
}