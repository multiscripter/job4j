package ru.job4j.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.net.URISyntaxException;
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
     * @param localName локальное имя properties-файла.
     * @throws IOException исключение ввода-вывода.
     */
    public void load(String localName) throws IOException {
        Path fName = Paths.get(path + localName);
        InputStream is = Files.newInputStream(fName);
        this.props.load(is);
    }
}