package ru.job4j.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import ru.job4j.models.IModel;

/**
 * Интерфейс CarStoreSpecification реализаует спецификацию
 * для метода JpaSpecificationExecutor.findAll(Predicate).
 *
 * @param <T> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-12
 * @since 2019-05-27
 */
public class CarStoreSpecification<T> {
    /**
     * entity manager..
     */
    private EntityManager entityManager;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Устанавливает entity manager.
     * @param entityManager entity manager.
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    /**
     * Получает спецификацию по параметрам.
     * @param params параметрам.
     * @return спецификаця.
     */
    public Specification<T> getSpec(HashMap<String, List<String[]>> params) {
        return new Specification<T>() {
            /**
             * Получает цепочку связанных сущностей.
             * @param ends массив имён классов сущностей концов цепочки.
             * @return цепочка связанных сущностей.
             */
            private LinkedList<Entry> getChain(String[] ends) {
                Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
                LinkedList<Entry> entries = new LinkedList<>();
                LinkedList<EntityType<?>> chain = new LinkedList<>();
                LinkedList<EntityType<?>> other = new LinkedList<>();
                LinkedList<Integer> indexes = new LinkedList<>();
                entities.forEach(entity -> {
                    if (entity.getJavaType().getName().equals(ends[0])) {
                        chain.add(entity);
                    } else if (entity.getJavaType().getName().equals(ends[1])) {
                        other.addFirst(entity);
                    } else {
                        other.add(entity);
                    }
                });
                int[] chainBuilt = new int[]{0};
                for (int[] a = new int[]{0}; a[0] < other.size(); a[0]++) {
                    EntityType<?> cur = chain.getLast();
                    cur.getDeclaredAttributes().forEach(attr -> {
                        if (chainBuilt[0] > 0) {
                            return;
                        }
                        if (attr.getJavaType().getName().equals(other.get(a[0]).getJavaType().getName())) {
                            EntityType<?> removed = other.remove(a[0]);
                            chain.add(removed);
                            entries.add(new Entry(cur, removed, attr.getName()));
                            indexes.add(a[0]);
                            a[0] = -1;
                            chainBuilt[0] = 1;
                            if (attr.getJavaType().getName().equals(ends[1])) {
                                chainBuilt[0] = 2;
                            }
                        }
                    });
                    if (chainBuilt[0] == 2) {
                        break;
                    }
                    if (a[0] == other.size() - 1 && chainBuilt[0] == 0 && indexes.size() > 0) {
                        int index = indexes.removeLast();
                        other.add(index, chain.removeLast());
                        entries.removeLast();
                        a[0] = index;
                    }
                    chainBuilt[0] = 0;
                }
                return entries;
            }
            /**
             * Получает список путей связанных сущнсотей.
             * @param root корневая сущность.
             * @param endsName имя ключа концнв сущности в карте параметров.
             * @return список путей связанных сущнсотей.
             */
            private LinkedList<Path<IModel>> getPath(Root<T> root, String endsName) {
                LinkedList<Path<IModel>> paths = new LinkedList<>();
                if (params.containsKey(endsName) && !params.get(endsName).isEmpty()) {
                    LinkedList<Entry> entries = this.getChain(params.get(endsName).get(0));
                    for (int a = 0; a < entries.size(); a++) {
                        String attrName = entries.get(a).getAttrName();
                        if (a == 0) {
                            paths.add(root.get(attrName));
                        } else {
                            paths.add(paths.getLast().get(attrName));
                        }
                    }
                } else {
                    paths.add((Path<IModel>) root);
                }
                return paths;
            }
            /**
             * Возвращает предикат.
             * @param root корень.
             * @param cq зарос критерия.
             * @param cb построитель критерия.
             * @return предикат.
             */
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> p = new ArrayList<>();
                try {
                    /* Вариант соединения таблиц с использование join.
                    Join<Offer, Car> jo = root.join("car");
                    Join<Car, Brand> jo2 = jo.join("brand");
                    */
                    // Вариант соединения таблиц с использование Path<T>.
                    LinkedList<Path<IModel>> paths = new LinkedList<>();
                    if (params.containsKey("where") && !params.get("where").isEmpty()) {
                        paths = this.getPath(root, "whereEnds");
                        // item[0] - таблица в бд.
                        // item[1] - поле.
                        // item[2] - оператор.
                        // item[3] - значение.
                        for (String[] item : params.get("where")) {
                            if (item[2].equals("=")) {
                                p.add(cb.equal(paths.getLast().get(item[1]), item[3]));
                            } else if (item[2].equals("!=") || item[2].equals("<>")) {
                                p.add(cb.notEqual(paths.getLast().get(item[1]), item[3]));
                            } else {
                                Expression ex1 = paths.getLast().get(item[1]);
                                Class<?> type = ex1.getJavaType();
                                Expression ex2 = (Expression) type.cast(item[3]);
                                if (item[2].equals(">")) {
                                    p.add(cb.greaterThan(ex1, ex2));
                                } else if (item[2].equals(">=")) {
                                    p.add(cb.greaterThanOrEqualTo(ex1, ex2));
                                } else if (item[2].equals("<")) {
                                    p.add((cb.lessThan(ex1, ex2)));
                                } else if (item[2].equals("<=")) {
                                    p.add(cb.lessThanOrEqualTo(ex1, ex2));
                                }
                            }
                        }
                    }
                    paths.clear();
                    if (params.containsKey("order") && !params.get("order").isEmpty()) {
                        if (params.containsKey("orderEnds") && !params.get("orderEnds").isEmpty()) {
                            paths = this.getPath(root, "orderEnds");
                        } else {
                            paths.add((Path<IModel>) root);
                        }
                        for (String[] item : params.get("order")) {
                            // item[0] - поле сортировки.
                            // item[1] - направление сортировки.
                            if (item.length > 1 && item[1].equals("desc")) {
                                cq.orderBy(cb.desc(paths.getLast().get(item[0])));
                            } else {
                                cq.orderBy(cb.asc(paths.getLast().get(item[0])));
                            }
                        }
                    }
                } catch (Exception ex) {
                    logger.error("ERROR: " + ex);
                }
                return cb.and(p.toArray(new Predicate[p.size()]));
            }
        };
    }
}
