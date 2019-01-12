package ru.job4j.services;

import java.util.function.Function;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.Item;
/**
 * Класс ItemDAO реализует шаблон "DAO" для модели Item.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-12
 * @since 2018-04-04
 */
public class ItemDAO {
    /**
     * Фабрика.
     */
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    /**
     * Логгер.
     */
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    /**
     * Закрывает фабрику.
     */
    public void close() {
        this.factory.close();
    }
    /**
     * Добавляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     * @return идентификатор объекта, добавленного в БД.
     */
    public int create(Item obj) throws PersistenceException {
        return this.process(session -> (int) session.save(obj));
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void delete(Item obj) throws PersistenceException {
        this.process(session -> {
            session.delete(obj);
            return null;
        });
    }
    /**
     * Обрабатывает объект, выполняя нужное действие.
     * @param <T> параметризированный тип.
     * @param command действие над объектом.
     * @return идентификатор для save() и null для остальных операций.
     */
    private <T> T process(final Function<Session, T> command) {
        T result = null;
        try (final Session session = this.factory.openSession()) {
            session.beginTransaction();
            try {
                result = command.apply(session);
            } catch (Exception ex) {
                this.logger.error("ERROR", ex);
                session.getTransaction().rollback();
            } finally {
                session.getTransaction().commit();
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return result;
    }
    /**
     * Обновляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void update(Item obj) throws PersistenceException {
        this.process(session -> {
            session.update(obj);
            return null;
        });
    }
}