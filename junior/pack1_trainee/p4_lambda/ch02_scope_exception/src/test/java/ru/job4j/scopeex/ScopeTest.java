package ru.job4j.scopeex;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/**
 * Класс ScopeTest демострирует область видимости и лямбда.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-11
 * @since 2018-09-11
 */
public class ScopeTest {
    /**
     * Пытается присвоить значение переменной из внешней области видимости.
     */
    @Test
    public void tryToAssingOuterVar() {
        List<String> names = Arrays.asList("Petr", "Nick", "Ban");
        String last = null;
        /*
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
     * Обёртка.
     * @param <T> параметризированный тип.
     */
    public class Wrapper<T> {
        /**
         * Объект исключения.
         */
        private T ex = null;
        /**
         * Получает значение.
         * @return значение.
         */
        public T get() {
            return this.ex;
        }
        /**
         * Устанавливает значение.
         * @param ex значение.
         */
        public void set(T ex) {
            this.ex = ex;
        }
        /**
         * Проверяет значение на пустоту.
         * @return true если значение пусто. Иначе false.
         */
        boolean isEmpty() {
            return this.ex == null;
        }
    }
    /**
     * Демострирует выброс исключения из лямбда.
     */
    @Test
    public void lambdaThrowsException() {
        String expected = "java.lang.ArrayIndexOutOfBoundsException";
        List<String> names = Arrays.asList("Petr", "Nick", "Ban");
        String[] test = {null};
        Wrapper<Exception> exw = new Wrapper();
        String actual = null;
        names.forEach(
            n -> {
                try {
                    test[1] = n;
                } catch (Exception ex) {
                    exw.set(ex);
                }
            }
        );
        if (!exw.isEmpty()) {
            actual = exw.get().getClass().getName();
        }
        assertEquals(expected, actual);
    }
}
