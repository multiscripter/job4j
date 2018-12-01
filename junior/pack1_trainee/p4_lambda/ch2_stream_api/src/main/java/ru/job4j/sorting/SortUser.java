package ru.job4j.sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Set;
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
     * @return Set с отсортированными пользователями.
     */
    Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }
    /**
     * Сортирует пользователей с использованием Stream API.
     * @param list список пользователей.
     * @return Set с отсортированными пользователями.
     */
    ArrayList<User> testSortStreamAsc(List<User> list) {
        return new ArrayList<>(list.stream().sorted((o1, o2) -> o1.getAge() - o2.getAge()).collect(Collectors.toList()));
    }
}
