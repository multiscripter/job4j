package ru.job4j.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс SortUserTest тестирует класс SortUser.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-01
 * @since 2017-05-12
 */
public class SortUserTest {
    /**
     * Ожидаемый список пользователей.
     */
    private ArrayList<User> expected;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        int[] ages = new int[]{73, 67, 76, 88, 63, 79};
        String[] names = new String[]{"Emelyan", "Frol", "Nikifor", "Dormidont", "Alampy", "Nafanail"};
        this.expected = new ArrayList<>();
        for (int a = 0; a < names.length; a++) {
            this.expected.add(new User(names[a], ages[a]));
        }
        this.expected.sort((o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
    }
    /**
     * Тестирует Set<User> sort(List<User> list).
     */
    @Test
    public void testSort() {
        int[] ages = new int[]{88, 67, 73, 76, 63, 79};
        String[] names = new String[]{"Dormidont", "Frol", "Emelyan", "Nikifor", "Alampy", "Nafanail"};
        ArrayList<User> usersList = new ArrayList<>();
        for (int a = 0; a < names.length; a++) {
            usersList.add(new User(names[a], ages[a]));
        }
        SortUser su = new SortUser();
        TreeSet<User> sorted = (TreeSet<User>) su.sort(usersList);
        ArrayList<User> actual = new ArrayList<>(sorted);
        assertEquals(this.expected, actual);
    }
    /**
     * Тестирует List<User> sortByAge(List<User> list).
     */
    @Test
    public void testSortByAge() {
        int[] ages = new int[]{88, 67, 76, 63, 79, 73};
        String[] names = new String[]{"Dormidont", "Frol", "Nikifor", "Alampy", "Nafanail", "Emelyan"};
        ArrayList<User> usersList = new ArrayList<>();
        for (int a = 0; a < names.length; a++) {
            usersList.add(new User(names[a], ages[a]));
        }
        SortUser su = new SortUser();
        ArrayList<User> actual = (ArrayList<User>) su.sortByAge(usersList);
        assertEquals(this.expected, actual);
    }
    /**
     * Тестирует public List<User> sortByAllFields (List<User> list).
     */
    @Test
    public void testSortByAllFields() {
        ArrayList<User> expected = new ArrayList<>();
        expected.add(new User("Ivan", 25));
        expected.add(new User("Ivan", 30));
        expected.add(new User("Sergey", 20));
        expected.add(new User("Sergey", 25));
        ArrayList<User> actual = new ArrayList<>();
        actual.add(new User("Sergey", 25));
        actual.add(new User("Ivan", 30));
        actual.add(new User("Sergey", 20));
        actual.add(new User("Ivan", 25));
        SortUser su = new SortUser();
        actual = (ArrayList<User>) su.sortByAllFields(actual);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<User> sortByNameLength(List<User> list).
     */
    @Test
    public void testSortByNameLength() {
        this.expected.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().length() - o2.getName().length();
            }
        });
        int[] ages = new int[]{73, 67, 76, 88, 63, 79};
        String[] names = new String[]{"Emelyan", "Frol", "Nikifor", "Dormidont", "Alampy", "Nafanail"};
        ArrayList<User> usersList = new ArrayList<>();
        for (int a = 0; a < names.length; a++) {
            usersList.add(new User(names[a], ages[a]));
        }
        SortUser su = new SortUser();
        ArrayList<User> actual = (ArrayList<User>) su.sortByNameLength(usersList);
        assertEquals(this.expected, actual);
    }
}