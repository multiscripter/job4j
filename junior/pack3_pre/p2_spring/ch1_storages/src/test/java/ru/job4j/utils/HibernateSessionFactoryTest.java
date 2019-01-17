package ru.job4j.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
/**
 * Класс HibernateSessionFactoryTest тестирует класс HibernateSessionFactory.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-17
 * @since 2018-08-04
 */
public class HibernateSessionFactoryTest {
    /**
     * Название субд.
     */
    private static String dbmsName;
    /**
     * Логгер.
     */
    private static Logger logger = LogManager.getLogger(HibernateSessionFactoryTest.class.getSimpleName());
    /**
     * Действия перед тестом.
     */
    @BeforeClass
    public static void beforeAllTests() {
        try {
            String path = HibernateSessionFactoryTest.class.getClassLoader().getResource(".").getPath();
            path = path.replaceFirst("^/(.:/)", "$1");
            dbmsName = new PropertyLoader(String.format("%s%s", path, "activeDBMS.properties")).getPropValue("name");
        } catch (Exception ex) {
            logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     */
    @Test
    public  void testGetPassCfgNameSessionFactoryAllreadyExists() {
        HibernateSessionFactory.get(String.format("hibernate.%s.cfg.xml", dbmsName));
        SessionFactory sf = HibernateSessionFactory.get(String.format("hibernate.%s.cfg.xml", dbmsName));
        assertNotNull(sf);
    }
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     * Получает новый SessionFactory.
     */
    @Test
    public  void testGetPassCfgNameSessionFactoryNotExistsYet() {
        SessionFactory sf = HibernateSessionFactory.get(String.format("hibernate.%s.cfg.xml", dbmsName));
        assertNotNull(sf);
    }
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     * Возвращает существующий SessionFactory по-умолчанию.
     */
    @Ignore@Test
    public  void testGetSessionFactoryAllreadyExists() {
        SessionFactory sf = HibernateSessionFactory.get();
        assertNotNull(sf);
    }
}