package ru.job4j.methref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс UserConvertTest тестирует класс UserConvert.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-11
 * @since 2018-09-11
 */
public class UserConvertTest {
    /**
     * Тестирует public List<User> factory(List<String> names, Function<String, User> op).
     */
    @Test
    public void testFactory() {
        List<UserConvert.User> expected = new ArrayList<>();
        expected.add(new UserConvert.User("Foo"));
        expected.add(new UserConvert.User("Bar"));
        expected.add(new UserConvert.User("Baz"));
        UserConvert uc = new UserConvert();
        List<String> names = Arrays.asList("Foo", "Bar", "Baz");
        List<UserConvert.User> actual = uc.factory(names, UserConvert.User::new);
        assertEquals(expected, actual);
    }
}

