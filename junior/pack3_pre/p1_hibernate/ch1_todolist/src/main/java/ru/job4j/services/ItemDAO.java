package ru.job4j.services;

import javax.persistence.PersistenceException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.Item;
/**
 * Класс ItemDAO реализует шаблон "DAO" для элементов TODO-листа.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-05
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
        return this.process(obj, "create");
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void delete(Item obj) throws PersistenceException {
        this.process(obj, "delete");
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @param action действие над объектом.
     * @throws javax.persistence.PersistenceException исключение JPA.
     * @return идентификатор для save() и null для остальных операций.
     */
    private int process(Item obj, String action) throws PersistenceException {
        int id = 0;
        try (Session session = this.factory.openSession()) {
            session.beginTransaction();
            if (action.equals("create")) {
                id = (int) session.save(obj);
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
        return id;
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void update(Item obj) throws PersistenceException {
        this.process(obj, "update");
    }
}