package ru.job4j.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Body;
import ru.job4j.models.Brand;
import ru.job4j.models.Car;
import ru.job4j.models.Founder;

import static org.junit.Assert.assertEquals;
/**
 * Класс CarRepositoryTest тестирует класс CarRepository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-08
 * @since 2018-05-14
 */
public class CarRepositoryTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Логгер.
     */
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    /**
     * Репозиторий машинок.
     */
    private CarRepository repo;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.repo = new CarRepository();
            this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch2task1", "postgres", "postgresrootpass");
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            this.driver.executeSqlScript(path + "../../src/main/resources/junior.pack3.p1.ch2.task1.sql");
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Заполняет список автомобилями.
     * @param expected список автомобилей.
     * @param result список результатов выборки из бд.
     * @throws Exception исключение.
     */
    private void fillExpected(List<Car> expected, List<HashMap<String, String>> result) throws Exception {
        for (HashMap<String, String> item : result) {
            Car car = new Car();
            car.setId(Integer.parseInt(item.get("car_id")));
            car.setName(item.get("car_name"));
            Founder founder = new Founder();
            founder.setId(Integer.parseInt(item.get("founder_id")));
            founder.setNameLast(item.get("founder_name_last"));
            founder.setName(item.get("founder_name"));
            Brand brand = new Brand();
            brand.setId(Integer.parseInt(item.get("brand_id")));
            brand.setName(item.get("brand_name"));
            brand.setFounder(founder);
            car.setBrand(brand);
            String query = String.format("select bodies.id, bodies.name from bodies, cars_bodies where bodies.id = body_id and car_id = %d order by bodies.id", car.getId());
            List<HashMap<String, String>> result2 = this.driver.select(query);
            ArrayList<Body> bodies = new ArrayList<>();
            for (HashMap<String, String> entry : result2) {
                Body body = new Body(Integer.parseInt(entry.get("id")), entry.get("name"));
                bodies.add(body);
            }
            car.setBodies(bodies);
            expected.add(car);
        }
    }
    /**
     * Тестирует public List<Car> get(HashMap<String, List<String[]>> params).
     * Тип: Car.
     */
    @Test
    public void testGetWithBodyIdEquals1() {
        try {
            List<Car> expected = new ArrayList<>();
            int id = 1;
            String query = String.format("select cars.id as car_id, cars.name as car_name, brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from cars, brands, founders, cars_bodies where cars.brand_id = brands.id and brands.founder_id = founders.id and cars.id = cars_bodies.car_id and cars_bodies.body_id = %d group by cars.id, brands.id, founders.id order by car_id", id);
            List<HashMap<String, String>> result = this.driver.select(query);
            this.fillExpected(expected, result);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"bodies", "id", "=", Integer.toString(id)});
            params.put("where", where);
            List<String[]> orderBy = new ArrayList<>();
            orderBy.add(new String[] {"id"});
            params.put("orderBy", orderBy);
            List<String[]> orderDir = new ArrayList<>();
            orderDir.add(new String[] {"asc"});
            params.put("orderDir", orderDir);
            List<Car> actual = this.repo.get(params);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<Car> get(HashMap<String, List<String[]>> params).
     * Тип: Car.
     */
    @Test
    public void testGetWithBodyIn() {
        try {
            List<Car> expected = new ArrayList<>();
            String ids = "1, 2";
            String query = String.format("select cars.id as car_id, cars.name as car_name, brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from cars, brands, founders, cars_bodies where cars.brand_id = brands.id and brands.founder_id = founders.id and cars.id = cars_bodies.car_id and cars_bodies.body_id in (%s) group by cars.id, brands.id, founders.id order by car_id", ids);
            List<HashMap<String, String>> result = this.driver.select(query);
            this.fillExpected(expected, result);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"bodies", "id", "in", "1", "2"});
            params.put("where", where);
            List<String[]> orderBy = new ArrayList<>();
            orderBy.add(new String[] {"id"});
            params.put("orderBy", orderBy);
            List<String[]> orderDir = new ArrayList<>();
            orderDir.add(new String[] {"asc"});
            params.put("orderDir", orderDir);
            List<Car> actual = this.repo.get(params);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<Car> get(HashMap<String, List<String[]>> params).
     * Тип: Car.
     */
    @Test
    public void testGetWithBodyBetween() {
        try {
            List<Car> expected = new ArrayList<>();
            int from = 1;
            int to = 7;
            String query = String.format("select cars.id as car_id, cars.name as car_name, brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from cars, brands, founders, cars_bodies where cars.brand_id = brands.id and brands.founder_id = founders.id and cars.id = cars_bodies.car_id and cars_bodies.body_id between %d and %d group by cars.id, brands.id, founders.id order by car_id", from, to);
            List<HashMap<String, String>> result = this.driver.select(query);
            this.fillExpected(expected, result);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"bodies", "id", "between", Integer.toString(from), Integer.toString(to)});
            params.put("where", where);
            List<String[]> orderBy = new ArrayList<>();
            orderBy.add(new String[] {"id"});
            params.put("orderBy", orderBy);
            List<String[]> orderDir = new ArrayList<>();
            orderDir.add(new String[] {"asc"});
            params.put("orderDir", orderDir);
            List<Car> actual = this.repo.get(params);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.repo.close();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}