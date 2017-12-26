package ru.job4j.htmlcss;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
/**
 * Класс Validation реализует функционал валидации данных форм.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-20
 */
public class Validation {
    /**
     * Кодировка.
     */
    private final String enc;
    /**
     * Сообщения об ошибках.
     */
    private Properties errmsg;
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Коллекция ошибок.
     */
    private HashMap<String, String> result;
    /**
     * Конструктор.
     * @param logger логгер.
     * @param encoding кодировка.
     */
    Validation(final Logger logger, final String encoding) {
        this.logger = logger;
        this.enc = encoding;
        this.errmsg = new Properties();
        this.result = new HashMap<>();
        try {
            PropertyLoader pl = new PropertyLoader("junior.pack2.p9.ch8.task1.errmsg.properties");
    		this.errmsg = pl.getProperties();
        } catch (IOException | NullPointerException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Получает тест ошибки по её названию.
     * @param name название ошибки.
     * @return тест ошибки.
     */
    public String getProperty(String name) {
        return this.errmsg.getProperty(name);
    }
    /**
     * Проверяет есть ли ошибка валидации по имени поля на момент вызова метода.
     * @param name имя поля.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean hasError(String name) {
        return this.result.get(name) != null;
    }
    /**
     * Проверяет есть ли ошибки валидации на момент вызова метода.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean hasErrors() {
        return !this.result.isEmpty();
    }
    /**
     * Проверяет является ли значение числом значение.
     * @param value проверяемое значение.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean isDecimal(String value) {
        boolean result = true;
        try {
            int num = Integer.parseInt(value);
            if (num < 1 || num > Integer.MAX_VALUE) {
                result = false;
            }
        } catch (NumberFormatException ex) {
            result = false;
        }
        return result;
    }
    /**
     * Проверяет является ли значение адресом электронной почты.
     * @param value проверяемое значение.
     * @return true если значение прошло фильтр. Иначе false.
     * @throws IllegalArgumentException нелегальный аргумент.
     */
    public boolean isEmail(String value) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
    /**
     * Проверяет значение на заполненность.
     * @param value проверяемое значение.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean isFilled(String value) {
        if (value != null) {
            value = value.trim();
        }
        return value != null && !value.isEmpty();
    }
    /**
     * Проверяет является ли значение именем.
     * @param value проверяемое значение.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean isName(String value) {
        Pattern pattern = Pattern.compile("^[\\p{L}A-Z]+[\\p{L}A-Z0-9- ]+[\\p{L}A-Z0-9]$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
    /**
     * Проверяет является ли значение null.
     * @param value проверяемое значение.
     * @return true если значение прошло фильтр. Иначе false.
     * @throws UnsupportedEncodingException исключение неподдерживаемоя
     * кодировка.
     */
    public boolean isExists(Object value) throws UnsupportedEncodingException {
        return value != null;
    }
    /**
     * Получает результирующую коллекцию.
     * @return результирующую коллекцию.
     */
    public HashMap<String, String> getResult() {
        return this.result;
    }
    /**
     * Получает коллекцию ошибок.
     * @param name имя поля.
     * @param value значение поля.
     * @param filters массив названий фильтров.
     * @throws IllegalAccessException исключение нелегальный доступ.
     * @throws InstantiationException исключение создания экземпляра.
     * @throws InvocationTargetException исключение InvocationTarget.
     * @throws NoSuchMethodException исключение "нет такого метода".
     * @throws SecurityException исключение безопастности.
     * @throws UnsupportedEncodingException исключение неподдерживаемая
     * кодировка.
     */
    public void validate(String name, String value, String[] filters) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, UnsupportedEncodingException {
        LinkedList<String> errors = new LinkedList<>();
        Class cl = this.getClass();
        Validation ins = new Validation(this.logger, this.enc);
        for (String filter : filters) {
            java.lang.reflect.Method method = null;
            if (filter.equals("isExists")) {
                method = cl.getMethod(filter, Object.class);
            } else {
                method = cl.getMethod(filter, String.class);
            }
            Object rslt = method.invoke(ins, value);
            if (!(Boolean) rslt) {
                errors.add(new String(this.errmsg.getProperty(filter).getBytes("ISO-8859-1"), "UTF-8"));
                break;
            }
        }
        if (!errors.isEmpty()) {
            this.result.put(name, errors.getFirst());
        }
    }
}