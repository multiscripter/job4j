package ru.job4j.services;

import java.util.HashMap;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.models.Item;
/**
 * Класс ItemRepository реализует шаблон "Репозиторий" для элементов TODO-листа.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-19
 * @since 2018-03-20
 */
public class ItemRepository {
    /**
     * Фабрика.
     */
    private final EntityManager manager = Persistence.createEntityManagerFactory(this.getClass().getSimpleName()).createEntityManager();
    /**
     * Логгер.
     */
    private final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Конструктор без параметров.
     */
    public ItemRepository() {
    }
    /**
     * Получает список элементов по заданному критерию.
     * @param params карта с параметрами запроса.
     * @return список элементов.
     * @throws java.lang.Exception исключение.
     */
    public List<Item> getItems(HashMap<String, String> params) throws Exception {
        List<Item> list = null;
        try {
            CriteriaBuilder cb = this.manager.getCriteriaBuilder();
            CriteriaQuery<Item> cq = cb.createQuery(Item.class);
            Root<Item> root = cq.from(Item.class);
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
            throw new Exception(ex);
        }
        return list;
    }
}