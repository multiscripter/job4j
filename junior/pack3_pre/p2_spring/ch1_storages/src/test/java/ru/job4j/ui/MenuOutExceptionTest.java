package ru.job4j.ui;

import org.junit.Test;
/**
 * Класс MenuOutExceptionTest тестирует класс MenuOutException.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-04
 * @since 2018-08-04
 */
public class MenuOutExceptionTest {
    /**
     * Тестирует public MenuOutException(String msg).
     * @throws MenuOutException исключение.
     */
    @Test(expected = MenuOutException.class)
    public void testMenuOutException() throws MenuOutException {
        throw new MenuOutException("testMenuOutException");
    }
}