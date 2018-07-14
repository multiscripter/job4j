package ru.job4j.services;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.models.User;
import static org.junit.Assert.assertNotNull;
/**
 * Класс UserStorageTest тестирует класс UserStorage.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-14
 * @since 2018-07-14
 */
public class UserStorageTest {
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Тестирует public void add(User user).
     */
    @Test
    public void testAdd() {
        try {
            StorageMemory memory = new StorageMemory();
            UserStorage storage = new UserStorage(memory);
            storage.add(new User());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Тестирует получение бинов после загрузки контекста.
     */
    @Test
    public void testInstantiationWithConstructor() {
        try {
            ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
            /**
             * Следующая инструкция отрабатывает, но в
             * junior.pack3.p2.ch1_beans_error.log записывается ошибка:
             * NoUniqueBeanDefinitionException: No qualifying bean of type ...
             */
            // UserStorage storage = ctx.getBean(UserStorage.class);
            UserStorage storage = (UserStorage) ctx.getBean("userStorageMemory");
            storage.add(new User());
            assertNotNull(storage);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Тестирует создание бина с помощью статического фабричного метода.
     */
    @Test
    public void testInstantiationWithStaticFactoryMethod() {
        try {
            ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
            UserStorage storage = (UserStorage) ctx.getBean("userStorageJDBC");
            storage.add(new User());
            assertNotNull(storage);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Тестирует создание бина с помощью фабричного метода экземпляра.
     */
    @Test
    public void testInstantiationWithFactoryMethod() {
        try {
            ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
            UserStorage storage = (UserStorage) ctx.getBean("userStorageFile");
            storage.add(new User());
            assertNotNull(storage);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
}