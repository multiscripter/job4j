package ru.job4j.utils;

import org.hibernate.SessionFactory;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
/**
 * Класс HibernateSessionFactoryTest тестирует класс HibernateSessionFactory.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-04
 * @since 2018-08-04
 */
public class HibernateSessionFactoryTest {
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     */
    @Test
    public  void testGetPassCfgNameSessionFactoryAllreadyExists() {
        SessionFactory sf = HibernateSessionFactory.get("hibernate.cfg.xml");
        assertNotNull(sf);
    }
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     * Получает новый SessionFactory.
     */
    @Test
    public  void testGetPassCfgNameSessionFactoryNotExistsYet() {
        SessionFactory sf = HibernateSessionFactory.get("hibernate.H2.cfg.xml");
        assertNotNull(sf);
    }
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     * Возвращает существующий SessionFactory по-умолчанию.
     */
    @Test
    public  void testGetSessionFactoryAllreadyExists() {
        SessionFactory sf = HibernateSessionFactory.get();
        assertNotNull(sf);
    }
}