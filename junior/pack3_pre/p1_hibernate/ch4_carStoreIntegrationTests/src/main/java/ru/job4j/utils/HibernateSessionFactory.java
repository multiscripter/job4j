package ru.job4j.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Класс HibernateSessionFactory реализует функционал получения SessionFactory.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-06-30
 * @since 2018-06-30
 */
public class HibernateSessionFactory {
    /**
     * Экземпляр фабрики сессий.
     */
    private static final SessionFactory FACTORY = buildSessionFactory();
    /**
     * Строит фабрику сессий.
     * @return экземпляр фабрики сессий.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    /**
     * Получает фабрику сессий.
     * @return фабрику сессий.
     */
    public static SessionFactory get() {
        return FACTORY;
    }
}
