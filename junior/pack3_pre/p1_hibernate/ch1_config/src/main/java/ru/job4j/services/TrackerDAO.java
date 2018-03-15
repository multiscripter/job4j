package ru.job4j.services;

import java.util.List;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
/**
 * Класс TrackerDAO реализует слой DAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-12
 * @since 2018-03-05
 * @param <T> параметризированный тип.
 */
public class TrackerDAO<T> {
    /**
     * Фабрика.
     */
    private final SessionFactory factory;
    /**
     * Логгер.
     */
    private final Logger logger;
    /**
	 * Конструктор.
	 */
    public TrackerDAO() {
        this.factory = new Configuration().configure().buildSessionFactory();
        this.logger = LogManager.getLogger(this.getClass().getName());
    }
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
        Transaction tx = null;
        try (Session session = this.factory.openSession()) {
            tx = session.beginTransaction();
            session.save(obj);
            tx.commit();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            if (tx != null) {
                tx.rollback();
            }
        }
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void delete(T obj) throws PersistenceException {
        Transaction tx = null;
        try (Session session = this.factory.openSession()) {
            tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            if (tx != null) {
                tx.rollback();
            }
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
        Transaction tx = null;
        try (Session session = this.factory.openSession()) {
            tx = session.beginTransaction();
            objs = session.createQuery(String.format("from %s", obj.getClass().getSimpleName())).list();
            tx.commit();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            if (tx != null) {
                tx.rollback();
            }
        }
        return objs;
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void update(T obj) throws PersistenceException {
        Transaction tx = null;
        try (Session session = this.factory.openSession()) {
            tx = session.beginTransaction();
            session.update(obj);
            session.getTransaction().commit();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}