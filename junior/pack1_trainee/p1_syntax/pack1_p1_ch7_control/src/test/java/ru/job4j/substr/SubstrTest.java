package ru.job4j.substr;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class SubstrTest тестирует методы класса Substr.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 */
public class SubstrTest {
    /**
     * Тестирует метод contains(). Вторая часть подстроки совпадает с началом строки.
     */
    @Test
    public void testContainsSubstrBeforeBegin() {
        String origin = "The class String includes methods for examining individual characters";
        char[] originChars = origin.toCharArray();
        String sub = "azThe";
        char[] subChars = sub.toCharArray();
        Substr substr = new Substr();
        boolean result = substr.contains(origin, sub);
        boolean expected = false;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод contains(). Подстрока совпадает с началом строки.
     */
    @Test
    public void testContainsSubstrAtBegin() {
        String origin = "The class String includes methods for examining individual characters";
        char[] originChars = origin.toCharArray();
        String sub = "The cla";
        char[] subChars = sub.toCharArray();
        Substr substr = new Substr();
        boolean result = substr.contains(origin, sub);
        boolean expected = true;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод contains(). Подстрока найдена в серидине строки.
     */
    @Test
    public void testContainsSubstrInside() {
        String origin = "The class String includes methods for examining individual characters";
        char[] originChars = origin.toCharArray();
        String sub = "ining indiv";
        char[] subChars = sub.toCharArray();
        Substr substr = new Substr();
        boolean result = substr.contains(origin, sub);
        boolean expected = true;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод contains(). Подстрока совпадает с концом строки.
     */
    @Test
    public void testContainsSubstrAtEnd() {
        String origin = "The class String includes methods for examining individual characters";
        char[] originChars = origin.toCharArray();
        String sub = "cters";
        char[] subChars = sub.toCharArray();
        Substr substr = new Substr();
        boolean result = substr.contains(origin, sub);
        boolean expected = true;
        assertThat(result, is(expected));
    }
    /**
     * Тестирует метод contains(). Первая часть подстроки совпадает с концом строки.
     */
    @Test
    public void testContainsSubstrAfterEnd() {
        String origin = "The class String includes methods for examining individual characters";
        char[] originChars = origin.toCharArray();
        String sub = "ractersvzbnxc";
        char[] subChars = sub.toCharArray();
        Substr substr = new Substr();
        boolean result = substr.contains(origin, sub);
        boolean expected = false;
        assertThat(result, is(expected));
    }
}