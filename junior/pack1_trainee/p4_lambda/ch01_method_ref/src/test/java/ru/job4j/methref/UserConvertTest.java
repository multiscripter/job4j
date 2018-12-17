package ru.job4j.methref;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс UserConvertTest тестирует класс UserConvert.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-17
 * @since 2018-09-11
 */
public class UserConvertTest {
    /**
     * Тестирует public List<User> factory(List<String> names, Function<String, User> op).
     */
    @Test
    public void testFactory() {
        List<User> expected = new ArrayList<>();
        expected.add(new User("Foo"));
        expected.add(new User("Bar"));
        expected.add(new User("Baz"));
        UserConvert uc = new UserConvert();
        List<String> names = Arrays.asList("Foo", "Bar", "Baz");
        // Передача указателя ссылки на нестатический метод.
        List<User> actual = uc.factory(names, User::new);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<User> factory(List<String> names, Function<String, User> op).
     */
    @Test
    public void testFactoryShowRefOfStaticMethod() {
        String ls = System.getProperty("line.separator");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
        StringBuilder expected = new StringBuilder();
        expected.append(new User("Foo").toString());
        expected.append(ls);
        expected.append(new User("Bar").toString());
        expected.append(ls);
        expected.append(new User("Baz").toString());
        expected.append(ls);
        UserConvert uc = new UserConvert();
        List<String> names = Arrays.asList("Foo", "Bar", "Baz");
        List<User> users = uc.factory(names, User::new);
        System.setOut(new PrintStream(tempOut));
        // Передача указателя ссылки на статический метод.
        users.forEach(System.out::println);
        String actual = tempOut.toString();
        System.setOut(originalOut);
        assertEquals(expected.toString(), actual);
    }
}

