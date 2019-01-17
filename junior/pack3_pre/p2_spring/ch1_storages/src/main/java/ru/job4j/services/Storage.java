package ru.job4j.services;

import java.net.URL;
import java.util.List;
import java.util.function.Function;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Component;
import ru.job4j.models.User;
import ru.job4j.utils.HibernateSessionFactory;
/**
 * Класс Storage реализует сущность Хранилище.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-04
 * @since 2018-07-19
 */
@Component
public class Storage {
    /**
     * Фабрика.
     */
    private final SessionFactory factory;
    /**
     * Конструктор без параметров.
     * Создаёт фабрику с конфигурацей по умолчанию.
     */
    public Storage() {
        System.err.println("Storage()");
        this.factory = HibernateSessionFactory.get();
    }
    /**
     * Конструктор.
     * Создаёт фабрику с указанной конфигурацей.
     * @param localFileName локальное имя файла конфигурации.
     */
    public Storage(String localFileName) {
        System.err.println("Storage: " + localFileName);
        this.factory = HibernateSessionFactory.get(localFileName);
    }
    /**
     * Конструктор.
     * Создаёт фабрику с указанной конфигурацей.
     * @param url имя файла конфигурации.
     */
    public Storage(URL url) {
        this.factory = HibernateSessionFactory.get(url);
    }
    /**
     * Добавляет пользователя в хранилище.
     * @param user пользователь.
     * @throws Exception исключение.
     */
    public void add(User user) throws Exception {
        int id = this.process(session -> {
            int r = (int) session.save(user);
            session.flush();
            return r;
        });
        user.setId(id);
    }
    /**
     * Закрывает фабрику.
     */
    public void close() {
        this.factory.close();
    }
    /**
     * Обрабатывает объект, выполняя нужное действие.
     * @param <U> параметризированный тип.
     * @param command действие над объектом.
     * @return идентификатор для save() и null для остальных операций.
     * @throws Exception исключение.
     */
    private <U> U process(final Function<Session, U> command) throws Exception {
        U result = null;
        try (final Session session = this.factory.openSession()) {
            session.beginTransaction();
            try {
                result = command.apply(session);
            } catch (Exception ex) {
                session.getTransaction().rollback();
            } finally {
                if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                    session.getTransaction().commit();
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return result;
    }
    /**
     * Возвращает объекты.
     * @param obj объект.
     * @return список объектов.
     * @throws Exception исключение.
     */
    public List<User> read(User obj) throws Exception {
        List<User> objs = null;
        try (Session session = this.factory.openSession()) {
            session.beginTransaction();
            objs = session.createQuery(String.format("from %s", obj.getClass().getSimpleName())).list();
            try {
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return objs;
    }
}