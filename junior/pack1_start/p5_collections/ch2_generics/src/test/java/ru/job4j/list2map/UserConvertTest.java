package ru.job4j.list2map;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс UserConvertTest тестирует класс UserConvert.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-11
 */
public class UserConvertTest {
    /**
     * Тестирует process().
     */
    @Test
    public void testProcess() {
        String[][] users = new String[][]{{"Foo", "FooCity"}, {"Bar", "BarCity"}, {"Baz", "BazCity"}};
        ArrayList<User> list = new ArrayList<>();
        HashMap<Integer, User> expected = new HashMap<>();
        for (int a = 0; a < users.length;) {
            User user = new User(users[a][0], users[a][1]);
            list.add(user);
            expected.put(++a, user);
        }
        UserConvert uc = new UserConvert();
        HashMap result = uc.process(list);
        assertEquals(expected, result);
    }
}