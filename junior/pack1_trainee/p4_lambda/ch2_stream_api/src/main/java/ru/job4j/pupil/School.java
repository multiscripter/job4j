package ru.job4j.pupil;

import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Класс School реализует сущность Школа.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-02-23
 * @since 2019-02-23
 */
public class School {
    /**
     * Возвращает коллекцию учеников, отфильтрованную по предикату.
     * @param students коллекция учеников.
     * @param predicat предикат для фильтрации.
     * @return отфильтрованная коллекция учеников.
     */
    public List<Student> collect(List<Student> students, Predicate<Student> predicat) {
        return students.stream().filter(predicat).collect(Collectors.toList());
    }
}
