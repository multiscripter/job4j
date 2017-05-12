package ru.job4j.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Class SortUserTest тестирует класс SortUser.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-12
 */
public class SortUserTest {
    /**
     * Тестирует sort().
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
        TreeSet<User> sorted = (TreeSet) su.sort(usersList);
        ArrayList<User> result = new ArrayList<>(sorted);
        assertEquals(expected, result);
    }
}
