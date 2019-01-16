package ru.job4j.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс PropertyLoader реализует функционал загрузки файлов свойств.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-12
 * @since 2017-12-23
 */
public final class PropertyLoader {
    /**
     * Логгер.
     */
    private final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Путь до файла.
     */
    private String path;
    /**
     * Свойства.
     */
    private final Properties props = new Properties();
    /**
     * Конструктор без параметров.
     */
    public PropertyLoader() {
    }
    /**
     * Конструктор.
     * @param name имя properties-файла.
     * @throws IOException исключение ввода-вывода.
     */
    public PropertyLoader(String name) throws IOException {
        this();
        this.load(name);
    }
    /**
     * Конструктор.
     * @param url имя properties-файла.
     * @throws IOException исключение ввода-вывода.
     */
    public PropertyLoader(URL url) throws IOException {
        this();
        this.load(url.toString());
    }
    /**
     * Получает свойства.
     * @return объект со свойствами.
     */
    public Properties getProperties() {
        return this.props;
    }
    /**
     * Получает свойство по имени.
     * @param propName имя свойства.
     * @return значение свойства.
     */
    public String getPropValue(String propName) {
        return this.props.getProperty(propName);
    }
    /**
     * Получает список свойств.
     * @return список свойств.
     * @throws UnsupportedEncodingException исключение неподдерживаемая
     * кодировка.
     *
    public LinkedList<Property> getPropertiesList() throws UnsupportedEncodingException {
        LinkedList<Property> list = new LinkedList<>();
        Set<String> names = this.props.stringPropertyNames();
        for (String name : names) {
            list.add(new Property(name, new String(this.props.getProperty(name).getBytes("ISO-8859-1"), "UTF-8")));
        }
        return list;
    }*/
    /**
     * Загружает свойства из файла.
     * @param name имя properties-файла.
     * @throws IOException исключение ввода-вывода.
     */
    public void load(String name) throws IOException {
        Path path = Paths.get(name);
        InputStream is = Files.newInputStream(path);
        this.props.load(is);
    }
}