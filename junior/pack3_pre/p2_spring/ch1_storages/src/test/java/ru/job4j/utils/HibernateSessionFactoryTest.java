package ru.job4j.utils;

import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
/**
 * Класс HibernateSessionFactoryTest тестирует класс HibernateSessionFactory.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-18
 * @since 2018-08-04
 */
public class HibernateSessionFactoryTest {
    /**
     * Локальное имя файла конфигурации Hibernate.
     */
    private static String cfgLocalFileName;
    /**
     * Название субд.
     */
    private static String dbmsName;
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        String path = HibernateSessionFactoryTest.class.getClassLoader().getResource(".").getPath();
        path = path.replaceFirst("^/(.:/)", "$1");
        dbmsName = new PropertyLoader(String.format("%s%s", path, "activeDBMS.properties")).getPropValue("name");
        cfgLocalFileName = "hibernate.cfg.xml";
        if (!dbmsName.equals("PostgreSQL")) {
            cfgLocalFileName = String.format("hibernate.%s.cfg.xml", dbmsName);
        }
    }
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     * @throws Exception исключение.
     */
    @Test
    public  void testGetPassCfgNameSessionFactoryAllreadyExists() throws Exception {
        HibernateSessionFactory.get(cfgLocalFileName);
        SessionFactory sf = HibernateSessionFactory.get(cfgLocalFileName);
        assertNotNull(sf);
    }
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     * Получает новый SessionFactory.
     * @throws Exception исключение.
     */
    @Test
    public  void testGetPassCfgNameSessionFactoryNotExistsYet() throws Exception {
        SessionFactory sf = HibernateSessionFactory.get(cfgLocalFileName);
        assertNotNull(sf);
    }
    /**
     * Тестирует public static SessionFactory get(String localFileName).
     * Возвращает существующий SessionFactory по-умолчанию.
     * @throws Exception исключение.
     */
    @Test
    public  void testGetSessionFactoryAllreadyExists() throws Exception {
        SessionFactory sf = HibernateSessionFactory.get();
        assertNotNull(sf);
    }
}