package ru.job4j.sorting;

import java.util.List;
import java.util.TreeSet;
import java.util.Set;
/**
 * Класс SortUser реализует сортировку пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
 * @since 2017-05-12
 */
class SortUser {
    /**
     * Сортирует пользователей.
     * @param list список пользователей.
     * @return TreeSet с отсортированными пользователями.
     */
    public Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }
}
