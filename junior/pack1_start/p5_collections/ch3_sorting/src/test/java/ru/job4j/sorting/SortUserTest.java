package ru.job4j.sorting;

import java.util.ArrayList;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Class SortUserTest тестирует класс SortUser.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
        TreeSet<User> expected = new TreeSet<>();
        for (int a = 0; a < names.length; a++) {
            usersList.add(new User(names[a], ages[a]));
            expected.add(new User(names[a], ages[a]));
        }
        SortUser su = new SortUser();
        TreeSet<User> result = (TreeSet) su.sort(usersList);
        assertEquals(expected, result);
    }
}