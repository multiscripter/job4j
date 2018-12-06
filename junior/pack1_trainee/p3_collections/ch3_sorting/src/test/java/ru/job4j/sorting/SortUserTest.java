package ru.job4j.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс SortUserTest тестирует класс SortUser.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
 * @since 2017-05-12
 */
public class SortUserTest {
    /**
     * Тестирует public Set<User> sort(List<User> list).
     */
    @Test
    public void testSort() {
        String[] names = new String[]{"Emelyan", "Frol", "Nikifor", "Dormidont", "Alampy", "Nafanail"};
        int[] ages = new int[]{73, 67, 76, 88, 63, 79};
        ArrayList<User> usersList = new ArrayList<>();
        ArrayList<User> expected = new ArrayList<>();
        for (int a = 0; a < names.length; a++) {
            usersList.add(new User(names[a], ages[a]));
            expected.add(new User(names[a], ages[a]));
        }
        expected.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        });
        SortUser su = new SortUser();
        TreeSet<User> sorted = (TreeSet<User>) su.sort(usersList);
        ArrayList<User> actual = new ArrayList<>(sorted);
        assertEquals(expected, actual);
    }
}