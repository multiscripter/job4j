package ru.job4j.utils;

import java.util.HashMap;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Класс HibernateSessionFactory реализует функционал получения SessionFactory.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-21
 * @since 2018-06-30
 */
public class HibernateSessionFactory {
    /**
     * Экземпляр фабрики сессий.
     */
    private static final HashMap<String, SessionFactory> FACTORY = new HashMap<>();
    /**
     * Закрытый конструктор.
     */
    private HibernateSessionFactory() {
    }
    /**
     * Строит фабрику сессий с файлом конфигурации по умолчанию (hibernate.cfg.xml).
     */
    private static void buildSessionFactory() {
        try {
            FACTORY.put("hibernate.cfg.xml", new Configuration().configure().buildSessionFactory());
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    /**
     * Строит фабрику сессий с файлом конфигурации localFileName.
     * @param localFileName имя файла конфигурации.
     */
    private static void buildSessionFactory(String localFileName) {
        try {
            FACTORY.put(localFileName, new Configuration().configure(localFileName).buildSessionFactory());
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
            buildSessionFactory();
        }
        return FACTORY.get("hibernate.cfg.xml");
    }
    /**
     * Получает фабрику сессий.
     * @param localFileName имя файла конфигурации.
     * @return фабрику сессий.
     */
    public static SessionFactory get(String localFileName) {
        if (!FACTORY.containsKey(localFileName)) {
            buildSessionFactory(localFileName);
        }
        return FACTORY.get(localFileName);
    }
}
