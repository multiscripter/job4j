package ru.job4j.pupil;

import java.util.Objects;
/**
 * Класс Student реализует сущность Студент.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-02-23
 * @since 2019-02-23
 */
public class Student {
    /**
     * Общий балл успеваемости.
     */
    private int score;
    /**
     * Конструктор.
     * @param score общий балл успеваемости.
     */
    public Student(final int score) {
        this.score = score;
    }
    /**
     * Сравнивает объекты Студент.
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
        Student s = (Student) obj;
        return this.score == s.getScore();
    }
    /**
     * Возвращает хэш-код объекта.
     * @return хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.score);
    }
    /**
     * Получает общий балл успеваемости.
     * @return общий балл успеваемости.
     */
    public int getScore() {
        return this.score;
    }
}
