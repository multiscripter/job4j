package ru.job4j.scopeex;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
/**
 * Класс ScopeTest демострирует область видимости и лямбда.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2018-09-11
 */
public class ScopeTest {
    /**
     * Пытается присвоить значение переменной из внешней области видимости.
     */
    @Test
    public void tryToAssingOuterVar() {
        /*
        List<String> names = Arrays.asList("Petr", "Nick", "Ban");
        String last = null;
        names.forEach(
            // Ошибка компиляции.
            // local variables referenced from a lambda expression must be final or effectively final
            n -> last = n
        );
        System.err.println("last: " + last);
        */
    }
    /**
     * Пытается присвоить значение переменной из внешней области видимости.
     */
    @Test
    public void tryToAssingOuterVar2() {
        String[] expected = {"Ban"};
        List<String> names = Arrays.asList("Petr", "Nick", "Ban");
        String[] actual = {null};
        names.forEach(
            n -> actual[0] = n
        );
        assertArrayEquals(expected, actual);
    }
    /**
     * Демострирует выброс исключения из лямбда.
     * Обращение к несуществующему элементу массива.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void lambdaThrowsException() {
        List<String> names = Arrays.asList("Petr", "Nick", "Ban");
        String[] test = {null};
        names.forEach(n -> test[1] = n);
        System.out.println(test[1]);
    }
}
