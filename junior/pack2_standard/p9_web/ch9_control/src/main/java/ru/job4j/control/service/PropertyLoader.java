package ru.job4j.control.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс PropertyLoader реализует функционал загрузки файлов свойств.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2017-12-23
 */
public class PropertyLoader {
    /**
     * Логгер.
     */
    private final Logger logger;
    /**
     * Путь до файла.
     */
    private String path;
    /**
     * Свойства.
     */
    private Properties props;
    /**
     * Конструктор без параметров.
     */
    public PropertyLoader() {
        this.logger = LogManager.getLogger("PropertyLoader");
        this.props = new Properties();
        try {
            this.path = new File(PropertyLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
    		this.path = this.path.replaceFirst("^/(.:/)", "$1");
        } catch (URISyntaxException | NullPointerException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Конструктор.
     * @param localName локальное имя properties-файла.
     * @throws IOException исключение ввода-вывода.
     */
    public PropertyLoader(String localName) throws IOException {
        this();
        this.load(localName);
    }
    /**
     * Получает свойства.
     * @return объект со свойствами.
     */
    public Properties getProperties() {
        return this.props;
    }
    /**
     * Получает список свойств.
     * @return список свойств.
     * @throws UnsupportedEncodingException исключение неподдерживаемая
     * кодировка.
     */
    public LinkedList<Property> getPropertiesList() throws UnsupportedEncodingException {
        LinkedList<Property> list = new LinkedList<>();
        Set<String> names = this.props.stringPropertyNames();
        for (String name : names) {
            list.add(new Property(name, new String(this.props.getProperty(name).getBytes("ISO-8859-1"), "UTF-8")));
        }
        list.sort(new Comparator<Property>() {
            @Override
            public int compare(Property o1, Property o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return list;
    }
    /**
     * Загружает свойства из файла.
     * @param localName локальное имя properties-файла.
     * @throws IOException исключение ввода-вывода.
     */
    public void load(String localName) throws IOException {
        Path fName = Paths.get(path + localName);
        InputStream is = Files.newInputStream(fName);
        this.props.load(is);
    }
}