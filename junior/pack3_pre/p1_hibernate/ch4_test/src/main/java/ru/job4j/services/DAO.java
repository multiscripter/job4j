package ru.job4j.services;

import java.util.List;
import java.util.function.Function;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import ru.job4j.models.IModel;
/**
 * Класс DAO реализует слой DAO для моделей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-06-18
 * @since 2018-04-27
 */
public class DAO {
    /**
     * Фабрика.
     */
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    /**
     * Логгер.
     */
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    /**
     * Конструктор без параметров.
     */
    public DAO() {
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
     * @return идентификатор объекта, добавленного в БД.
     */
    public int create(IModel obj) throws PersistenceException {
        return this.process(session -> {
            int r = (int) session.save(obj);
            //System.err.println("obj: " + obj);
            //session.flush();
            return r;
        });
    }
    /**
     * Удаляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void delete(IModel obj) throws PersistenceException {
        this.process(session -> {
            session.delete(obj);
            return null;
        });
    }
    /**
     * Обрабатывает объект, выполняя нужное действие.
     * @param <U> параметризированный тип.
     * @param command действие над объектом.
     * @return идентификатор для save() и null для остальных операций.
     */
    private <U> U process(final Function<Session, U> command) {
        U result = null;
        try (final Session session = this.factory.openSession()) {
            session.beginTransaction();
            try {
                result = command.apply(session);
            } catch (Exception ex) {
                this.logger.error("ERROR", ex);
                session.getTransaction().rollback();
            } finally {
                if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                    session.getTransaction().commit();
                }
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return result;
    }
    /**
     * Возвращает список объектов.
     * @param obj объект.
     * @return список объектов.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public List<IModel> read(IModel obj) throws PersistenceException {
        return this.process((Function<Session, List<IModel>>) session -> session.createQuery(String.format("from %s order by name", obj.getClass().getSimpleName())).list());
    }
    /**
     * Обновляет объект.
     * @param obj объект.
     * @throws javax.persistence.PersistenceException исключение JPA.
     */
    public void update(IModel obj) throws PersistenceException {
        this.process(session -> {
            session.update(obj);
            return null;
        });
    }
}