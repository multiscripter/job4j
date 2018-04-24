package ru.job4j.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс Item реализует сущность Элемент TODO-листа.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-18
 * @since 2018-04-17
 */
public class Validation {
    /**
     * Коллекция ошибок.
     */
    private HashMap<String, String> errors = new HashMap<>();
    /**
     * Логгер.
     */
    private final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Конструктор без параметров.
     */
    public Validation() {
    }
    /**
     * Получает коллекцию ошибок.
     * @return коллекция ошибок.
     */
    public HashMap<String, String> getErrors() {
        return this.errors;
    }
    /**
     * Проверяет есть ли ошибка валидации по имени поля на момент вызова метода.
     * @param name имя поля.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean hasError(String name) {
        return this.errors.get(name) != null;
    }
    /**
     * Проверяет есть ли ошибки валидации на момент вызова метода.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }
    /**
     * Проверяет является ли значение строковым представление даты, отформатированной по шаблону.
     * @param name имя поля.
     * @param pattern шаблон строкового представления даты.
     * @param value проверяемое значение.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean isDateFormat(String name, String pattern, String value) {
        boolean result = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.parse(value);
        } catch (Exception ex) {
            result = false;
            this.errors.put(name, "isDateFormat");
        } finally {
            return result;
        }
    }
    /**
     * Проверяет является ли значение целым числом больше нуля.
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
     * Проверяет является ли значение null.
     * @param value проверяемое значение.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean isExists(Object value) {
        return value != null;
    }
    /**
     * Проверяет значение на заполненность.
     * @param value проверяемое значение.
     * @return true если значение прошло фильтр. Иначе false.
     */
    public boolean isFilled(String value) {
        return !value.isEmpty();
    }
    /**
     * Производит валидацию значения.
     * @param name имя поля.
     * @param value значение поля.
     * @param filters массив названий фильтров.
     * @return true если значение прошло валидацию. Иначе false.
     * @throws IllegalAccessException исключение нелегальный доступ.
     * @throws InstantiationException исключение создания экземпляра.
     * @throws InvocationTargetException исключение InvocationTarget.
     * @throws NoSuchMethodException исключение "нет такого метода".
     * @throws SecurityException исключение безопастности.
     * @throws UnsupportedEncodingException исключение неподдерживаемая кодировка.
     */
    public boolean validate(String name, String value, String[] filters) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, UnsupportedEncodingException {
        boolean result = true;
        LinkedList<String> errors = new LinkedList<>();
        Class cl = this.getClass();
        Validation ins = new Validation();
        for (String filter : filters) {
            java.lang.reflect.Method method;
            if (filter.equals("isExists")) {
                method = cl.getMethod(filter, Object.class);
            } else {
                method = cl.getMethod(filter, String.class);
            }
            Object rslt = method.invoke(ins, value);
            if (!(Boolean) rslt) {
                errors.add(filter);
                break;
            }
        }
        if (!errors.isEmpty()) {
            this.errors.put(name, errors.getFirst());
            result = false;
        }
        return result;
    }
}