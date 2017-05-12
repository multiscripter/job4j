package ru.job4j.sorting;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.Set;
/**
 * Class SortUser реализует сортировку пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-12
 */
class SortUser {
    /**
     * Сортирует пользователей.
     * @param list список пользователей.
     * @return TreeSet с отсортированными пользователями.
     */
    Set<User> sort(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        });
        TreeSet<User> set = new TreeSet<>(list);
        return set;
    }
}