package ru.job4j.utils;

import java.net.URL;
import java.util.HashMap;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Класс HibernateSessionFactory реализует функционал получения SessionFactory.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-16
 * @since 2018-06-30
 */
public class HibernateSessionFactory {
    /**
     * Карта с экземплярами фабрики сессий.
     */
    private static final HashMap<String, SessionFactory> FACTORY = new HashMap<>();
    /**
     * Закрытый конструктор.
     */
    private HibernateSessionFactory() {
    }
    /**
     * Строит фабрику сессий с файлом конфигурации localFileName.
     * @param localFileName локальное имя файла конфигурации.
     */
    private static void buildSessionFactory(String localFileName) {
        try {
            FACTORY.put(localFileName, new Configuration().configure(localFileName).buildSessionFactory());
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    /**
     * Строит фабрику сессий с файлом конфигурации url.
     * @param url имя файла конфигурации.
     */
    private static void buildSessionFactory(URL url) {
        try {
            FACTORY.put(url.getFile(), new Configuration().configure(url).buildSessionFactory());
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    /**
     * Получает фабрику сессий.
     * @return фабрику сессий.
     */
    public static SessionFactory get() {
        if (!FACTORY.containsKey("hibernate.cfg.xml")) {
            buildSessionFactory("hibernate.cfg.xml");
        }
        return FACTORY.get("hibernate.cfg.xml");
    }
    /**
     * Получает фабрику сессий.
     * @param localFileName локальное имя файла конфигурации.
     * @return фабрику сессий.
     */
    public static SessionFactory get(String localFileName) {
        if (!FACTORY.containsKey(localFileName)) {
            buildSessionFactory(localFileName);
        }
        return FACTORY.get(localFileName);
    }
    /**
     * Получает фабрику сессий.
     * @param url имя файла конфигурации.
     * @return фабрику сессий.
     */
    public static SessionFactory get(URL url) {
        System.err.println(url.getFile());
        if (!FACTORY.containsKey(url.getFile())) {
            buildSessionFactory(url);
        }
        return FACTORY.get(url.getFile());
    }
}
