package ru.job4j.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Body;
import ru.job4j.models.Car;
import ru.job4j.models.Engine;
import ru.job4j.models.Gearbox;
import ru.job4j.models.IModel;

/**
 * Класс DAOTest тестирует класс DAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-01
 * @since 2018-04-27
 */
public class DAOTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * DAO моделей.
     */
    private DAO dao;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.dao = new DAO();
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
     * Тестирует public int create(T obj) throws PersistenceException.
     * Тип: Body.
     */
    @Test
    public void testCreateWithTypeBody() {
        try {
            Body expected = new Body(0, "Targa");
            int id = this.dao.create(expected);
            expected.setId(id);
            List<HashMap<String, String>> result = this.driver.select(String.format("select * from bodies where id = %d", expected.getId()));
            Body actual = new Body(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int create(T obj) throws PersistenceException.
     * Тип: Car.
     */
    @Test
    public void testCreateWithTypeCar() {
        try {
            Car expected = new Car();
            expected.setId(0);
            expected.setName("Niva");
            List<Body> ebodies = new ArrayList<>();
            ebodies.add(new Body(3, "universal"));
            expected.setBodies(ebodies);
            List<Engine> eengines = new ArrayList<>();
            eengines.add(new Engine(8, "VAZ-2121"));
            expected.setEngines(eengines);
            List<Gearbox> egearboxes = new ArrayList<>();
            egearboxes.add(new Gearbox(1, "manual"));
            expected.setGearboxes(egearboxes);
            int id = this.dao.create(expected);
            expected.setId(id);
            String query = String.format("select * from cars where id = %d", expected.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            Car actual = new Car();
            actual.setId(Integer.parseInt(result.get(0).get("id")));
            actual.setName(result.get(0).get("name"));
            result = this.driver.select(String.format("select bodies.id, bodies.name from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", expected.getId()));
            List<Body> abodies = new ArrayList<>();
            abodies.add(new Body(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name")));
            actual.setBodies(abodies);
            result = this.driver.select(String.format("select engines.id, engines.name from engines, cars_engines where engines.id = cars_engines.engine_id and car_id = %d order by engines.id", expected.getId()));
            List<Engine> aengines = new ArrayList<>();
            aengines.add(new Engine(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name")));
            actual.setEngines(aengines);
            result = this.driver.select(String.format("select gearboxes.id, gearboxes.name from gearboxes, cars_gearboxes where gearboxes.id = cars_gearboxes.gearbox_id and car_id = %d order by gearboxes.id", expected.getId()));
            List<Gearbox> agearboxes = new ArrayList<>();
            agearboxes.add(new Gearbox(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name")));
            actual.setGearboxes(agearboxes);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void delete(T obj) throws PersistenceException.
     * Тип: Engine.
     */
    @Test
    public void testDeleteWithTypeEngine() {
        try {
            int id = 1;
            Engine e = new Engine(id, "Fake engine name");
            // Удлаяет запросом: delete from items where id=?
            this.dao.delete(e);
            List<HashMap<String, String>> result = this.driver.select(String.format("select id from engines where id = %d", id));
            assertTrue(result.isEmpty());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void delete(T obj) throws PersistenceException.
     * Тип: Car.
     */
    @Test
    public void testDeleteWithTypeCar() {
        try {
            Car testCar = new Car();
            testCar.setId(0);
            testCar.setName("Vesta");
            List<Body> ebodies = new ArrayList<>();
            ebodies.add(new Body(1, "sedan"));
            ebodies.add(new Body(4, "crossover"));
            testCar.setBodies(ebodies);
            List<Engine> eengines = new ArrayList<>();
            eengines.add(new Engine(6, "VAZ-21129"));
            eengines.add(new Engine(7, "VAZ-21179"));
            testCar.setEngines(eengines);
            List<Gearbox> egearboxes = new ArrayList<>();
            egearboxes.add(new Gearbox(1, "manual"));
            egearboxes.add(new Gearbox(2, "auto"));
            testCar.setGearboxes(egearboxes);
            int id = this.dao.create(testCar);
            testCar.setId(id);
            this.dao.delete(testCar);
            String query = String.format("select * from cars where id = %d", testCar.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            assertTrue(result.isEmpty());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует private U U process(final Function<Session, U> command).
     * @throws java.lang.Exception исключение.
     */
    @Test(expected = Exception.class)
    public void testProcess() throws Exception {
        this.dao.create(null);
    }
    /**
     * Тестирует public List<T> read(T obj) throws PersistenceException.
     * Тип: Body.
     */
    @Test
    public void testReadWithTypeBody() {
        try {
            String query = "select * from bodies order by name";
            List<HashMap<String, String>> result = this.driver.select(query);
            List<Body> expected = new ArrayList<>();
            for (HashMap<String, String> item : result) {
                expected.add(new Body(Integer.parseInt(item.get("id")), item.get("name")));
            }
            List<IModel> actual = this.dao.read(new Body());
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void update(E obj).
     * Тип: Gearbox.
     */
    @Test
    public void testUpdateWithTypeGearbox() {
        try {
            Gearbox expected = new Gearbox(1, "manual");
            expected.setName("Smart");
            this.dao.update(expected);
            List<HashMap<String, String>> result = this.driver.select(String.format("select * from gearboxes where id = %d", expected.getId()));
            Gearbox actual = new Gearbox(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void update(E obj).
     * Тип: Gearbox.
     */
    @Test
    public void testUpdateWithTypeCar() {
        try {
            Car expected = new Car();
            expected.setId(4);
            expected.setName("Largus");
            List<Body> ebodies = new ArrayList<>();
            ebodies.add(new Body(3, "universal"));
            ebodies.add(new Body(4, "crossover"));
            ebodies.add(new Body(5, "van"));
            expected.setBodies(ebodies);
            List<Engine> eengines = new ArrayList<>();
            eengines.add(new Engine(9, "VAZ-11189"));
            eengines.add(new Engine(10, "VAZ-21129"));
            expected.setEngines(eengines);
            List<Gearbox> egearboxes = new ArrayList<>();
            egearboxes.add(new Gearbox(1, "manual"));
            egearboxes.add(new Gearbox(2, "auto"));
            expected.setGearboxes(egearboxes);
            this.dao.update(expected);
            String query = String.format("select * from cars where id = %d", expected.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            Car actual = new Car();
            actual.setId(Integer.parseInt(result.get(0).get("id")));
            actual.setName(result.get(0).get("name"));
            result = this.driver.select(String.format("select bodies.id, bodies.name from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", expected.getId()));
            List<Body> abodies = new ArrayList<>();
            for (HashMap<String, String> item : result) {
                abodies.add(new Body(Integer.parseInt(item.get("id")), item.get("name")));
            }
            actual.setBodies(abodies);
            result = this.driver.select(String.format("select engines.id, engines.name from engines, cars_engines where engines.id = cars_engines.engine_id and car_id = %d order by engines.id", expected.getId()));
            List<Engine> aengines = new ArrayList<>();
            for (HashMap<String, String> item : result) {
                aengines.add(new Engine(Integer.parseInt(item.get("id")), item.get("name")));
            }
            actual.setEngines(aengines);
            result = this.driver.select(String.format("select gearboxes.id, gearboxes.name from gearboxes, cars_gearboxes where gearboxes.id = cars_gearboxes.gearbox_id and car_id = %d order by gearboxes.id", expected.getId()));
            List<Gearbox> agearboxes = new ArrayList<>();
            for (HashMap<String, String> item : result) {
                agearboxes.add(new Gearbox(Integer.parseInt(item.get("id")), item.get("name")));
            }
            actual.setGearboxes(agearboxes);
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
        this.dao.close();
    }
}