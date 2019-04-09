package ru.job4j.conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Класс Config реализует сущность Конфигурация.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-04-08
 * @since 2019-04-08
 */
public class Config {
    /**
     * Имя properties-файла.
     */
    private final String path;
    /**
     * Коллеция пар ключ-значение свойств.
     */
    private final Map<String, String> values = new HashMap<>();
    /**
     * Конструктор.
     * @param path Имя properties-файла.
     */
    public Config(final String path) {
        this.path = path;
    }
    /**
     * Загружает пару ключ-значение в коллекцию.
     * @throws Exception исключение.
     */
    public void load() throws Exception {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach((line) -> {
                line = line.trim();
                if (line.length() > 0 && !line.startsWith("#") && !line.startsWith("!")) {
                    String[] pair = line.split("=");
                    if (pair.length == 1) {
                        pair = new String[] {pair[0], ""};
                    }
                    this.values.put(pair[0].trim(), pair[1].trim());
                }
            });
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    /**
     * Возвращает размер коллекции свойств.
     * @return размер коллекции свойств.
     */
    public int size() {
        return this.values.size();
    }
    /**
     * Возвращает строковое предстваление коллекции свойств.
     * @return строковое предстваление коллекции свойств.
     * @throws Exception исключение.
     */
    public String toStr() throws Exception {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return out.toString();
    }
    /**
     * Возвращает значение ключа.
     * @param key ключ.
     * @return значение.
     */
    public String value(String key) {
        return this.values.get(key);
    }
}
