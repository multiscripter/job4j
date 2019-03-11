package ru.job4j.addresses;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс ProfilesTest тестирует класс Profiles.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-11
 * @since 2019-03-11
 */
public class ProfilesTest {
    /**
     * Тестирует public List Address collect(List Profile profiles).
     */
    @Test
    public void testCollect() {
        ArrayList<Address> expected = new ArrayList<>();
        expected.add(new Address(0, 1, "Охотный ряд", "Москва"));
        expected.add(new Address(0, 7, "Моховая", "Москва"));
        ArrayList<Profile> profiles = new ArrayList<>();
        expected.forEach(address -> profiles.add(new Profile(address)));
        Profiles p = new Profiles();
        assertEquals(expected, p.collect(profiles));
    }
}