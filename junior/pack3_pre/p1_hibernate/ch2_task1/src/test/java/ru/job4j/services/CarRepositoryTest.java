package ru.job4j.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Body;
import ru.job4j.models.Car;
import ru.job4j.models.Engine;
import ru.job4j.models.Gearbox;

import static org.junit.Assert.assertEquals;
/**
 * Класс CarRepositoryTest тестирует класс CarRepository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-01
 * @since 2018-05-01
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
        } catch (IOException | SQLException | URISyntaxException ex) {
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
            car.setId(Integer.parseInt(item.get("id")));
            car.setName(item.get("name"));
            // Body.
            String query = String.format("select bodies.id, bodies.name from bodies, cars_bodies where bodies.id = body_id and car_id = %d order by bodies.id", car.getId());
            List<HashMap<String, String>> result2 = this.driver.select(query);
            ArrayList<Body> bodies = new ArrayList<>();
            for (HashMap<String, String> entry : result2) {
                Body body = new Body(Integer.parseInt(entry.get("id")), entry.get("name"));
                bodies.add(body);
            }
            car.setBodies(bodies);
            // Engine.
            query = String.format("select engines.id, engines.name from engines, cars_engines where engines.id = engine_id and car_id = %d order by engines.id", car.getId());
            result2 = this.driver.select(query);
            ArrayList<Engine> engines = new ArrayList<>();
            for (HashMap<String, String> entry : result2) {
                Engine engine = new Engine(Integer.parseInt(entry.get("id")), entry.get("name"));
                engines.add(engine);
            }
            car.setEngines(engines);
            // Gearbox.
            query = String.format("select gearboxes.id, gearboxes.name from gearboxes, cars_gearboxes where gearboxes.id = gearbox_id and car_id = %d order by gearboxes.id", car.getId());
            result2 = this.driver.select(query);
            ArrayList<Gearbox> gearboxes = new ArrayList<>();
            for (HashMap<String, String> entry : result2) {
                Gearbox gearbox = new Gearbox(Integer.parseInt(entry.get("id")), entry.get("name"));
                gearboxes.add(gearbox);
            }
            car.setGearboxes(gearboxes);
            expected.add(car);
        }
    }
    /**
     * Тестирует public List<Car> get(HashMap<String, List<String[]>> params).
     */
    @Test
    public void testGetWithBodyIdEquals1() {
        try {
            List<Car> expected = new ArrayList<>();
            int id = 1;
            String query = String.format("select cars.id, cars.name from cars, cars_bodies where cars.id = car_id and body_id = %d group by cars.id", id);
            List<HashMap<String, String>> result = this.driver.select(query);
            this.fillExpected(expected, result);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"bodies", "id", "=", Integer.toString(id)});
            params.put("where", where);
            List<Car> actual = this.repo.get(params);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<Car> get(HashMap<String, List<String[]>> params).
     */
    @Test
    public void testGetWithGreaboxIn() {
        try {
            List<Car> expected = new ArrayList<>();
            String ids = "1, 2";
            String query = String.format("select cars.id, cars.name from cars, cars_gearboxes where cars.id = car_id and gearbox_id in (%s) group by cars.id", ids);
            List<HashMap<String, String>> result = this.driver.select(query);
            this.fillExpected(expected, result);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"gearboxes", "id", "in", "1", "2"});
            params.put("where", where);
            List<Car> actual = this.repo.get(params);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<Car> get(HashMap<String, List<String[]>> params).
     */
    @Test
    public void testGetWithEngineBetween() {
        try {
            List<Car> expected = new ArrayList<>();
            int from = 4;
            int to = 7;
            String query = String.format("select cars.id, cars.name from cars, cars_engines where cars.id = car_id and engine_id between %d and %d group by cars.id", from, to);
            List<HashMap<String, String>> result = this.driver.select(query);
            this.fillExpected(expected, result);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"engines", "id", "between", Integer.toString(from), Integer.toString(to)});
            params.put("where", where);
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
        this.repo.close();
    }
}