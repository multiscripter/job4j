package ru.job4j.services;

import java.util.List;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Класс TrackerDAO реализует слой DAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-16
 * @since 2018-03-05
 * @param <T> параметризированный тип.
 */
public class TrackerDAO<T> {
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
     */
    public void create(T obj) throws PersistenceException {
        this.process(obj, "create");
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void delete(T obj) throws PersistenceException {
        this.process(obj, "delete");
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @param action действие над объектом.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    private void process(T obj, String action) throws PersistenceException {
        try (Session session = this.factory.openSession()) {
            session.beginTransaction();
            if (action.equals("create")) {
                session.save(obj);
            } else if (action.equals("delete")) {
                session.delete(obj);
            } else if (action.equals("update")) {
                session.update(obj);
            }
            try {
                session.getTransaction().commit();
            } catch (Exception ex) {
                this.logger.error("ERROR", ex);
                session.getTransaction().rollback();
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Возвращает объекты.
     * @param obj объект.
     * @return список объектов.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public List<T> read(T obj) throws PersistenceException {
        List<T> objs = null;
        try (Session session = this.factory.openSession()) {
            session.beginTransaction();
            objs = session.createQuery(String.format("from %s", obj.getClass().getSimpleName())).list();
            try {
                session.getTransaction().commit();
            } catch (Exception ex) {
                this.logger.error("ERROR", ex);
                session.getTransaction().rollback();
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return objs;
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void update(T obj) throws PersistenceException {
        this.process(obj, "update");
    }
}