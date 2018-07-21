package ru.job4j.services;

import java.util.List;
import java.util.Map;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.utils.HibernateSessionFactory;

/**
 * Класс TrackerDAO реализует слой DAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-21
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
    private final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Получает.
     * @return карта со свойствами SessionFactory.
     */
    public Map<String, Object> get() {
        return this.factory.getProperties();
    }
    /**
     * Конструктор без параметров.
     */
    public TrackerDAO() {
        this.factory = HibernateSessionFactory.get();
    }
    /**
     * Конструктор.
     * @param localFileName имя файла конфигурации.
     */
    public TrackerDAO(String localFileName) {
        this.factory = HibernateSessionFactory.get(localFileName);
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
     * @return идентификатор объекта, добавленного в БД.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public int create(T obj) throws PersistenceException {
        return this.process(obj, "create");
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
     * @return идентификатор для save() и null для остальных операций.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    private int process(T obj, String action) throws PersistenceException {
        Integer result = null;
        try (Session session = this.factory.openSession()) {
            session.beginTransaction();
            if (action.equals("create")) {
                result = (int) session.save(obj);
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
        return result;
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