package ru.job4j.utils;

import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс ValidationTest тестирует класс Validation.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-23
 * @since 2018-04-23
 */
public class ValidationTest {
    /**
     * Объект валидации.
     */
    private Validation va;
    /**
     * Логгер.
     */
    private final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.va = new Validation();
    }
    /**
     * Тестирует public HashMap<String, String> getErrors().
     */
    @Test
    public void testGetErrors() {
        try {
            this.va.validate("name", null, new String[]{"isExists", "isFilled"});
            HashMap<String, String> errors = new HashMap<>();
            if (this.va.hasErrors()) {
                errors = this.va.getErrors();
            }
            assertTrue(errors.size() > 0);
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
     * Тестирует public boolean hasError(String name).
     * Нет ошибок.
     */
    @Test
    public void testHasErrorNoError() {
        try {
            this.va.validate("name", "Test name", new String[]{"isExists", "isFilled"});
            assertFalse(this.va.hasError("name"));
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
     * Тестирует public boolean hasError(String name).
     * Есть ошибки.
     */
    @Test
    public void testHasError() {
        try {
            this.va.validate("name", "", new String[]{"isExists", "isFilled"});
            assertTrue(this.va.hasError("name"));
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
     * Тестирует public boolean hasErrors().
     * Нет ошибок.
     */
    @Test
    public void testHasErrorsReturnsFalse() {
        assertFalse(this.va.hasErrors());
    }
    /**
     * Тестирует public boolean isDateFormat(String name, String pattern, String value).
     */
    @Test
    public void testIsDateFormatReturnsTrue() {
        assertTrue(this.va.isDateFormat("created", "yyyy-MM-dd H:m:s", "2018-04-23 22:57:00"));
    }
    /**
     * Тестирует public boolean isDateFormat(String name, String pattern, String value).
     */
    @Test
    public void testIsDateFormatReturnsFalse() {
        assertFalse(this.va.isDateFormat("created", "yyyy-MM-dd H:m:s", "incorrect date string"));
    }
    /**
     * Тестирует public boolean isDecimal(String value).
     */
    @Test
    public void testIsDecimalReturnsTrue() {
        assertTrue(this.va.isDecimal("100500"));
    }
    /**
     * Тестирует public boolean isDecimal(String value).
     * Число меньше 1.
     */
    @Test
    public void testIsDecimalReturnsFalseValueLessThenOne() {
        assertFalse(this.va.isDecimal("-999"));
    }
    /**
     * Тестирует public boolean isDecimal(String value).
     * Число больше Integer.MAX_VALUE.
     */
    @Test
    public void testIsDecimalReturnsFalseValueGreateThenMaxInt() {
        assertFalse(this.va.isDecimal("2333444555666"));
    }
    /**
     * Тестирует public boolean isDecimal(String value).
     * Не число.
     */
    @Test
    public void testIsDecimalReturnsFalseValueIsNotNumber() {
        assertFalse(this.va.isDecimal("NotAnumber"));
    }

}