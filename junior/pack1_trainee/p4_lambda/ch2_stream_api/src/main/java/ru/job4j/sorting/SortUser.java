package ru.job4j.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
/**
 * Class SortUser реализует сортировку пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-01
 * @since 2017-05-12
 */
class SortUser {
    /**
     * Сортирует пользователей.
     * @param list список пользователей.
     * @return Множество с отсортированными пользователями.
     */
    Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }
    /**
     * Сортирует пользователей с использованием Stream API.
     * @param list список пользователей.
     * @return список с отсортированными пользователями.
     */
    List<User> sortByAge(List<User> list) {
        return new ArrayList<>(list.stream().sorted((o1, o2) -> o1.getAge() - o2.getAge()).collect(Collectors.toList()));
    }
    /**
     * Сортирует пользователей по имени в лексикографическом порядке, потом по возрасту с использованием Stream API.
     * @param list список пользователей.
     * @return список с отсортированными пользователями.
     */
    public List<User> sortByAllFields(List<User> list) {
        return new ArrayList<>(list.stream().sorted(Comparator.comparing(User::getName).thenComparing(User::getAge)).collect(Collectors.toList()));
    }
    /**
     * Сортирует пользователей по длине имени с использованием Stream API.
     * @param list список пользователей.
     * @return список с отсортированными пользователями.
     */
    public List<User> sortByNameLength(List<User> list) {
        return new ArrayList<>(list.stream().sorted((o1, o2) -> o1.getName().length() - o2.getName().length()).collect(Collectors.toList()));
    }
}
