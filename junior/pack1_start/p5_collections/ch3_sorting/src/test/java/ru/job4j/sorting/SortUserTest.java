package ru.job4j.sorting;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.TreeMap;
=======
import java.util.Comparator;
>>>>>>> origin/junior.pack1.p5.ch3.task3.1
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
     * Массив имён пользователей.
     */
    private String[] names = new String[]{"Emelyan", "Frol", "Nikifor", "Dormidont", "Alampy", "Nafanail"};
    /**
     * Массив возрастов пользователей.
     */
    private int[] ages = new int[]{73, 67, 76, 88, 63, 79};
    /**
     * Тестирует sort().
     */
    @Test
    public void testSort() {
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
<<<<<<< HEAD
    /**
     * Тестирует sortHash().
     */
    @Test
    public void testSortHash() {
        ArrayList<User> result = new ArrayList<>();
        TreeMap<Integer, User> map = new TreeMap<>();
        for (int a = 0; a < names.length; a++) {
            result.add(new User(names[a], ages[a]));
            map.put(new User(names[a], ages[a]).hashCode(), new User(names[a], ages[a]));
        }
        ArrayList<User> expected = new ArrayList<User>(map.values());
        SortUser su = new SortUser();
        result = (ArrayList) su.sortHash(result);
        assertEquals(expected, result);
    }
    /**
     * Тестирует sortLength().
     */
    @Test
    public void testSortLength() {
        String[] expected = new String[]{"Frol", "Alampy", "Emelyan", "Nikifor", "Nafanail", "Dormidont"};
        ArrayList<User> usersList = new ArrayList<>();
        for (int a = 0; a < names.length; a++) {
            usersList.add(new User(names[a], ages[a]));
        }
        SortUser su = new SortUser();
        usersList = (ArrayList) su.sortLength(usersList);
        String[] result = new String[expected.length];
        for (int a = 0; a < names.length; a++) {
            result[a] = usersList.get(a).getName();
        }
        assertEquals(expected, result);
    }
}
=======
}
>>>>>>> origin/junior.pack1.p5.ch3.task3.1
