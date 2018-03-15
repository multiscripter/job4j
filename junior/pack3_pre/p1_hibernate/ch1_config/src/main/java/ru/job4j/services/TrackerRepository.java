package ru.job4j.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс TrackerRepository реализует шаблон Репозиторий.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-15
 * @since 2018-03-10
 * @param <T> параметризированный тип.
 */
public class TrackerRepository<T> {
    /**
     * Фабрика.
     */
    private final EntityManager manager;
    /**
     * Логгер.
     */
    private final Logger logger;
    /**
	 * Конструктор.
	 */
    public TrackerRepository() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(this.getClass().getSimpleName());
        this.manager = factory.createEntityManager();
        this.logger = LogManager.getLogger(this.getClass().getSimpleName());
    }
    /**
     * Получает список полей по заданному критерию.
     * @param obj объект.
     * @param params карта с параметрами запроса.
     * @return список объектов.
     */
    public List<Tuple> getFields(T obj, HashMap<String, List<String>> params) {
        List<Tuple> list = null;
        try {
            CriteriaBuilder cb = this.manager.getCriteriaBuilder();
            CriteriaQuery<Tuple> criteria = cb.createQuery(Tuple.class);
            Root<T> root = criteria.from((Class<T>) obj.getClass());
            if (params.containsKey("select")) {
                List<Selection<?>> selectList = new ArrayList<>();
                for (String fieldName : params.get("select")) {
                    selectList.add(root.get(fieldName));
                }
                criteria.multiselect(selectList);
            }
            list = this.manager.createQuery(criteria).getResultList();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
        return list;
    }
    /**
     * Получает список объектов по заданному критерию.
     * @param obj объект.
     * @param params карта с параметрами запроса.
     * @return список объектов.
     */
    public List<T> getObjects(T obj, HashMap<String, String> params) {
        List<T> list = null;
        try {
            CriteriaBuilder cb = this.manager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery((Class<T>) obj.getClass());
            Root<T> root = cq.from((Class<T>) obj.getClass());
            cq.select(root);
            if (params.containsKey("whereField") && params.containsKey("whereOp") && params.containsKey("whereVal")) {
                Predicate p;
                String field = params.get("whereField");
                String val = params.get("whereVal");
                if (params.get("whereOp").equals(">")) {
                    p = cb.gt(root.get(field), Double.parseDouble(val));
                } else if (params.get("whereOp").equals("<")) {
                    p = cb.lt(root.get(field), Double.parseDouble(val));
                } else {
                    p = cb.equal(root.get(field), val);
                }
                cq.where(p);
            }
            if (params.containsKey("orderBy")) {
                if (params.containsKey("orderDir") && params.get("orderDir").equals("desc")) {
                    cq.orderBy(cb.desc(root.get(params.get("orderBy"))));
                } else {
                    cq.orderBy(cb.asc(root.get(params.get("orderBy"))));
                }
            }
            list = this.manager.createQuery(cq).getResultList();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return list;
    }
}