package ru.job4j.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.Car;
/**
 * Класс CarRepository реализует репозиторий для модели Car.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-01
 * @since 2018-04-27
 */
public class CarRepository {
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
    public CarRepository() {
    }
    /**
     * Закрывает фабрику.
     */
    public void close() {
        this.factory.close();
    }
    /**
     * Получает SQL-запрос, построеный по параметрам.
     * @param params карта с параметрами. имя: список массивов значений.
     * @return Строку с SQL-запросом.
     */
    private String getQuery(HashMap<String, List<String[]>> params) {
        String query = "select car from Car car";
        if (params.containsKey("where") && !params.get("where").isEmpty()) {
            String join = "";
            String where = "";
            for (String[] item : params.get("where")) {
                join = String.format("join car.%1$s %1$s_item ", item[0]);
                where = String.format("and %s_item.%s ", item[0], item[1]);
                if (item[2].equals("between")) {
                    where = String.format("%s between %s and %s ", where, item[3], item[4]);
                } else if (item[2].equals("in")) {
                    where = String.format("%s in(%s) ", where, String.join(", ", Arrays.copyOfRange(item, 3, item.length)));
                } else {
                    where = String.format("%s %s %s ", where, item[2], item[3]);
                }
            }
            query = String.format("%s %s where %s ", query, join, where.substring(4));
        }
        query = String.format("%s group by car.id ", query);
        if (params.containsKey("orderBy") && !params.get("orderBy").isEmpty()) {
            query = String.format("%s order by %s ", query, String.join(",", params.get("orderBy").get(0)));
            String order = params.containsKey("orderDir") && !params.get("orderDir").isEmpty() ? params.get("orderDir").get(0)[0] : "asc";
            query = String.format("%s %s", query, params.get("orderDir").get(0)[0]);
        }
        return query;
    }
    /**
     * Получает список машин по указаным параметрам.
     * @param params карта с параметрами. имя: список массивов значений.
     * где массив значений: new String[] {"table", "field name", "operator", "field value1"[, "field valueN"]}.
     * @return список машин.
     * @throws Exception исключение.
     */
    public List<Car> get(HashMap<String, List<String[]>> params) throws Exception {
        String query = this.getQuery(params);
        List<Car> cars;
        try (final Session session = this.factory.openSession()) {
            session.beginTransaction();
            cars = session.createQuery(query).list();
            try {
                session.getTransaction().commit();
            } catch (Exception ex) {
                this.logger.error("ERROR", ex);
                session.getTransaction().rollback();
            }
        }
        return cars;
    }
}