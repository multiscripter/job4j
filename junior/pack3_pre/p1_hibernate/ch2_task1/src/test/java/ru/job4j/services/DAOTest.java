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
import ru.job4j.models.Brand;
import ru.job4j.models.Car;
import ru.job4j.models.Founder;
import ru.job4j.models.IModel;

/**
 * Класс DAOTest тестирует класс DAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-15
 * @since 2018-05-15
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
            String query = String.format("select * from bodies where id = %d", expected.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            Body actual = new Body(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public int create(T obj) throws PersistenceException.
     * Тип: Brand.
     */
    @Test
    public void testCreateWithTypeBrand() {
        try {
            Founder f = new Founder(0, "ЦК КПСС", "Совет министров СССР");
            int id = this.dao.create(f);
            f.setId(id);
            Brand expected = new Brand(0, "ВАЗ", f);
            id = this.dao.create(expected);
            expected.setId(id);
            String query = String.format("select brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from brands, founders where brands.founder_id = founders.id and brands.id = %d", expected.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            Founder founder = new Founder();
            founder.setId(Integer.parseInt(result.get(0).get("founder_id")));
            founder.setNameLast(result.get(0).get("founder_name_last"));
            founder.setName(result.get(0).get("founder_name"));
            Brand actual = new Brand();
            actual.setId(Integer.parseInt(result.get(0).get("brand_id")));
            actual.setName(result.get(0).get("brand_name"));
            actual.setFounder(founder);
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
            Founder founder = new Founder(0, "ЦК КПСС", "Совет министров СССР");
            int id = this.dao.create(founder);
            founder.setId(id);
            Brand brand = new Brand(0, "ВАЗ", founder);
            id = this.dao.create(brand);
            brand.setId(id);
            expected.setBrand(brand);
            id = this.dao.create(expected);
            expected.setId(id);
            String query = String.format("select cars.id as car_id, cars.name as car_name, brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from cars, brands, founders where cars.brand_id = brands.id and brands.founder_id = founders.id and cars.id = %d", expected.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            Car actual = new Car();
            actual.setId(Integer.parseInt(result.get(0).get("car_id")));
            actual.setName(result.get(0).get("car_name"));
            Founder f = new Founder(Integer.parseInt(result.get(0).get("founder_id")), result.get(0).get("founder_name_last"), result.get(0).get("founder_name"));
            Brand b = new Brand(Integer.parseInt(result.get(0).get("brand_id")), result.get(0).get("brand_name"), f);
            actual.setBrand(b);
            query = String.format("select bodies.id, bodies.name from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", expected.getId());
            result = this.driver.select(query);
            List<Body> abodies = new ArrayList<>();
            abodies.add(new Body(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name")));
            actual.setBodies(abodies);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void delete(T obj) throws PersistenceException.
     * Тип: Founder.
     */
    @Test
    public void testDeleteWithTypeFounder() {
        try {
            Founder founder = new Founder(0, "ЦК КПСС", "Совет министров СССР");
            int id = this.dao.create(founder);
            founder.setId(id);
            // Удаляет запросом: delete from items where id=?
            this.dao.delete(founder);
            String query = String.format("select id from founders where id = %d", id);
            List<HashMap<String, String>> result = this.driver.select(query);
            assertTrue(result.isEmpty());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void delete(T obj) throws PersistenceException.
     * Тип: Brand.
     */
    @Test
    public void testDeleteWithTypeBrand() {
        try {
            Founder founder = new Founder(0, "ЦК КПСС", "Совет министров СССР");
            int id = this.dao.create(founder);
            founder.setId(id);
            String name = "ВАЗ";
            Brand brand = new Brand(0, name, founder);
            id = this.dao.create(brand);
            brand.setId(id);
            this.dao.delete(brand);
            String query = String.format("select name from brands where id = %d", id);
            List<HashMap<String, String>> resultBrand = this.driver.select(query);
            query = String.format("select name_last from founders where id = %d", founder.getId());
            List<HashMap<String, String>> resultFounder = this.driver.select(query);
            assertTrue(resultBrand.isEmpty() && resultFounder.isEmpty());
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
            Founder founder = new Founder(0, "ЦК КПСС", "Совет министров СССР");
            int id = this.dao.create(founder);
            founder.setId(id);
            String name = "ВАЗ";
            Brand brand = new Brand(0, name, founder);
            id = this.dao.create(brand);
            brand.setId(id);
            Car car = new Car();
            car.setId(0);
            car.setName("Vesta");
            car.setBrand(brand);
            List<Body> bodies = new ArrayList<>();
            bodies.add(new Body(1, "sedan"));
            bodies.add(new Body(4, "crossover"));
            car.setBodies(bodies);
            id = this.dao.create(car);
            car.setId(id);
            this.dao.delete(car);
            String query = String.format("select name from cars where id = %d", car.getId());
            List<HashMap<String, String>> resultCar = this.driver.select(query);
            query = String.format("select name from brands where id = %d", brand.getId());
            List<HashMap<String, String>> resultBrand = this.driver.select(query);
            query = String.format("select name_last from founders where id = %d", founder.getId());
            List<HashMap<String, String>> resultFounder = this.driver.select(query);
            assertTrue(resultCar.isEmpty() && resultBrand.isEmpty() && resultFounder.isEmpty());
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
     * Тип: Founder.
     */
    @Test
    public void testReadWithTypeFounder() {
        try {
            List<Founder> expected = new ArrayList<>();
            String query = "select * from founders order by name";
            List<HashMap<String, String>> result = this.driver.select(query);
            for (HashMap<String, String> item : result) {
                Founder founder = new Founder();
                founder.setId(Integer.parseInt(item.get("id")));
                founder.setNameLast(item.get("name_last"));
                founder.setName(item.get("name"));
                expected.add(founder);
            }
            List<IModel> actual = this.dao.read(new Founder());
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<T> read(T obj) throws PersistenceException.
     * Тип: Brand.
     */
    @Test
    public void testReadWithTypeBrand() {
        try {
            List<Brand> expected = new ArrayList<>();
            String query = "select brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from brands, founders where founder_id = founders.id order by brands.name";
            List<HashMap<String, String>> result = this.driver.select(query);
            for (HashMap<String, String> item : result) {
                Founder f = new Founder();
                f.setId(Integer.parseInt(item.get("founder_id")));
                f.setNameLast(item.get("founder_name_last"));
                f.setName(item.get("founder_name"));
                expected.add(new Brand(Integer.parseInt(item.get("brand_id")), item.get("brand_name"), f));
            }
            List<IModel> actual = this.dao.read(new Brand());
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public List<T> read(T obj) throws PersistenceException.
     * Тип: Car.
     */
    @Test
    public void testReadWithTypeCar() {
        try {
            List<Car> expected = new ArrayList<>();
            String query = "select cars.id as car_id, cars.name as car_name, brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from cars, brands, founders where cars.brand_id = brands.id and brands.founder_id = founders.id order by cars.name";
            List<HashMap<String, String>> result = this.driver.select(query);
            for (HashMap<String, String> item : result) {
                Founder founder = new Founder();
                founder.setId(Integer.parseInt(item.get("founder_id")));
                founder.setNameLast(item.get("founder_name_last"));
                founder.setName(item.get("founder_name"));
                Brand brand = new Brand();
                brand.setId(Integer.parseInt(item.get("brand_id")));
                brand.setName(item.get("brand_name"));
                brand.setFounder(founder);
                Car car = new Car();
                car.setId(Integer.parseInt(item.get("car_id")));
                car.setName(item.get("car_name"));
                car.setBrand(brand);
                query = String.format("select bodies.id, bodies.name from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", car.getId());
                List<HashMap<String, String>> resultBodies = this.driver.select(query);
                List<Body> bodies = new ArrayList<>();
                for (HashMap<String, String> resultBody : resultBodies) {
                    Body body = new Body();
                    body.setId(Integer.parseInt(resultBody.get("id")));
                    body.setName(resultBody.get("name"));
                    bodies.add(body);
                }
                car.setBodies(bodies);
                expected.add(car);
            }
            List<IModel> cars = this.dao.read(new Car());
            List<Car> actual = new ArrayList<>();
            for (IModel item : cars) {
                actual.add((Car) item);
            }
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void update(E obj).
     * Тип: Founder.
     */
    @Test
    public void testUpdateWithTypeFounder() {
        try {
            Founder expected = new Founder(0, "ЦК КПСС", "Совет министров СССР");
            int id = this.dao.create(expected);
            expected.setId(id);
            expected.setName("new name");
            this.dao.update(expected);
            String query = String.format("select * from founders where id = %d", id);
            List<HashMap<String, String>> result = this.driver.select(query);
            Founder actual = new Founder();
            actual.setId(Integer.parseInt(result.get(0).get("id")));
            actual.setNameLast(result.get(0).get("name_last"));
            actual.setName(result.get(0).get("name"));
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void update(E obj).
     * Тип: Brand.
     */
    @Test
    public void testUpdateWithTypeBrand() {
        try {
            Founder f = new Founder(0, "ЦК КПСС", "Совет министров СССР");
            int id = this.dao.create(f);
            f.setId(id);
            Brand expected = new Brand(0, "ВАЗ", f);
            id = this.dao.create(expected);
            expected.setId(id);
            expected.setName("Tatra");
            expected.getFounder().setName("Ignats");
            expected.getFounder().setNameLast("Shustala");
            this.dao.update(expected);
            String query = String.format("select brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from brands, founders where brands.founder_id = founders.id and brands.id = %d", expected.getId());
            List<HashMap<String, String>> result = this.driver.select(query);
            Founder founder = new Founder();
            founder.setId(Integer.parseInt(result.get(0).get("founder_id")));
            founder.setNameLast(result.get(0).get("founder_name_last"));
            founder.setName(result.get(0).get("founder_name"));
            Brand actual = new Brand();
            actual.setId(Integer.parseInt(result.get(0).get("brand_id")));
            actual.setName(result.get(0).get("brand_name"));
            actual.setFounder(founder);
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
             expected.setId(0);
             expected.setName("Niva");
             List<Body> ebodies = new ArrayList<>();
             ebodies.add(new Body(3, "universal"));
             expected.setBodies(ebodies);
             Founder founder = new Founder(0, "ЦК КПСС", "Совет министров СССР");
             int id = this.dao.create(founder);
             founder.setId(id);
             Brand brand = new Brand(0, "ВАЗ", founder);
             id = this.dao.create(brand);
             brand.setId(id);
             expected.setBrand(brand);
             id = this.dao.create(expected);
             expected.setId(id);
             expected.setName("613");
             List<Body> bodies = expected.getBodies();
             bodies.set(0, new Body(1, "sedan"));
             bodies.add(new Body(2, "hatchback"));
             expected.setBodies(bodies);
             expected.getBrand().setName("Tatra");
             expected.getBrand().getFounder().setName("Ignats");
             expected.getBrand().getFounder().setNameLast("Shustala");
             this.dao.update(expected);
             String query = String.format("select cars.id as car_id, cars.name as car_name, brands.id as brand_id, brands.name as brand_name, founders.id as founder_id, founders.name_last as founder_name_last, founders.name as founder_name from cars, brands, founders where cars.brand_id = brands.id and brands.founder_id = founders.id and cars.id = %d", expected.getId());
             List<HashMap<String, String>> result = this.driver.select(query);
             Car actual = new Car();
             actual.setId(Integer.parseInt(result.get(0).get("car_id")));
             actual.setName(result.get(0).get("car_name"));
             Founder f = new Founder(Integer.parseInt(result.get(0).get("founder_id")), result.get(0).get("founder_name_last"), result.get(0).get("founder_name"));
             Brand b = new Brand(Integer.parseInt(result.get(0).get("brand_id")), result.get(0).get("brand_name"), f);
             actual.setBrand(b);
             query = String.format("select bodies.id, bodies.name from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", expected.getId());
             result = this.driver.select(query);
             List<Body> abodies = new ArrayList<>();
             for (HashMap<String, String> item : result) {
                 Body body = new Body();
                 body.setId(Integer.parseInt(item.get("id")));
                 body.setName(item.get("name"));
                 abodies.add(body);
             }
             actual.setBodies(abodies);
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