package ru.job4j.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Body;
import ru.job4j.models.Brand;
import ru.job4j.models.Car;
import ru.job4j.models.Founder;
import ru.job4j.models.IModel;
import ru.job4j.models.Offer;
import ru.job4j.models.User;
import ru.job4j.utils.HibernateSessionFactory;
import ru.job4j.utils.PropertyLoader;

/**
 * Класс DAOTest тестирует класс DAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-14
 * @since 2018-05-15
 */
//@Ignore
public class DAOTest {
    /**
     * Локальное имя файла конфигурации Hibernate.
     */
    private static String cfgLocalFileName;
    /**
     * DAO моделей.
     */
    private DAO dao;
    /**
     * Название текущей СУБД.
     */
    private static String dbmsName;
    /**
     * Драйвер бд.
     */
    private static DBDriver driver;
    /**
     * Путь файла sql.
     */
    private static String path;
    /**
     * Действия перед всеми тестами.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        path = DAOTest.class.getClassLoader().getResource(".").getPath();
        path = path.replaceFirst("^/(.:/)", "$1");
        dbmsName = new PropertyLoader(String.format("%s%s", path, "activeDBMS.properties")).getPropValue("name");
        cfgLocalFileName = "hibernate.cfg.xml";
        if (!dbmsName.equals("PostgreSQL")) {
            cfgLocalFileName = String.format("hibernate.%s.cfg.xml", dbmsName);
        }
        driver = new DBDriver(path + dbmsName);
        path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
        path = path.replaceFirst("^/(.:/)", "$1");
        path = String.format("%sjunior.pack3.p4.ch2.task1.%s.sql", path, dbmsName);
    }
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @Before
    public void beforeTest()throws Exception {
        this.dao = new DAO(cfgLocalFileName);
        driver.executeSqlScript(path);
    }
    /**
     * Получает объявление.
     * @return объявление.
     * @throws Exception исключение.
     */
    private Offer getOffer() throws Exception {
        HashMap<String, IModel> items = new HashMap<>();
        items.put("body", new Body(1, "sedan"));
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body(1, "sedan"));
        bodies.add(new Body(2, "hatchback"));
        Founder founder = new Founder(0, "ЦК КПСС", "Совет министров СССР");
        int id = this.dao.create(founder);
        founder.setId(id);
        Brand brand = new Brand(0, "ВАЗ", founder);
        id = this.dao.create(brand);
        brand.setId(id);
        Car car = new Car(0, "Vesta", bodies, brand);
        id = this.dao.create(car);
        car.setId(id);
        items.put("car", car);
        User user = new User(1, "testUser1");
        items.put("user", user);
        return new Offer(0, items, 100500, false);
    }
    /**
     * Тестирует public int create(T obj) throws PersistenceException.
     * Тип: Body.
     * @throws Exception исключение.
     */
    @Test
    public void testCreateWithTypeBody() throws Exception {
        Body expected = new Body(0, "targa");
        int id = this.dao.create(expected);
        expected.setId(id);
        /**
         * В HSQLDB (HyperSQL) при создании таблицы имена таблицы, столбца, алиаса,
         * не заключенные в двойные кавычки, будут приведены к ВЕРХНЕМУ РЕГИСТРУ.
         * Если создать таблицу инструкцией:
         * create table tbl_name (
         *     col_name1 integer not null primary key,
         *     col_name2 varchar (16) not null
         * );
         * и сделать select-запрос, то результирующий набор будет такой:
         * запись 1[COL_NAME1=значение, COL_NAME2=значение, ...].
         * Соответственно обращение:
         * result.get(0).get("col_name1"); приведёт к ошибке и выкинет исключение.
         * Получить значения можно так:
         * result.get(0).get("COL_NAME1");
         *
         * Учитывая написанное выше алиас имени столбца нужно поместить в двойные кавычки,
         * которые нужно экранировать.
         */
        String query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies where id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        Body actual = new Body(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name"));
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public int create(T obj) throws PersistenceException.
     * Тип: Brand.
     * @throws Exception исключение.
     */
    @Test
    public void testCreateWithTypeBrand() throws Exception {
        Founder f = new Founder(0, "ЦК КПСС", "Совет министров СССР");
        int id = this.dao.create(f);
        f.setId(id);
        Brand expected = new Brand(0, "ВАЗ", f);
        id = this.dao.create(expected);
        expected.setId(id);
        String query = String.format("select brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\" from brands, founders where brands.founder_id = founders.id and brands.id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        Founder founder = new Founder();
        founder.setId(Integer.parseInt(result.get(0).get("founder_id")));
        founder.setNameLast(result.get(0).get("founder_name_last"));
        founder.setName(result.get(0).get("founder_name"));
        Brand actual = new Brand();
        actual.setId(Integer.parseInt(result.get(0).get("brand_id")));
        actual.setName(result.get(0).get("brand_name"));
        actual.setFounder(founder);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public int create(T obj) throws PersistenceException.
     * Тип: Car.
     * @throws Exception исключение.
     */
    @Test
    public void testCreateWithTypeCar() throws Exception {
        Car expected = new Car();
        expected.setId(0);
        expected.setName("Нива");
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
        String query = String.format("select cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\" from cars, brands, founders where cars.brand_id = brands.id and brands.founder_id = founders.id and cars.id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        Car actual = new Car();
        actual.setId(Integer.parseInt(result.get(0).get("car_id")));
        actual.setName(result.get(0).get("car_name"));
        Founder f = new Founder(Integer.parseInt(result.get(0).get("founder_id")), result.get(0).get("founder_name_last"), result.get(0).get("founder_name"));
        Brand b = new Brand(Integer.parseInt(result.get(0).get("brand_id")), result.get(0).get("brand_name"), f);
        actual.setBrand(b);
        query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", expected.getId());
        result = driver.select(query);
        List<Body> abodies = new ArrayList<>();
        abodies.add(new Body(Integer.parseInt(result.get(0).get("id")), result.get(0).get("name")));
        actual.setBodies(abodies);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public int create(T obj) throws PersistenceException.
     * Тип: Offer.
     * @throws Exception исключение.
     */
    @Test
    public void testCreateWithTypeOffer() throws Exception {
        Offer expected = this.getOffer();
        int id = this.dao.create(expected);
        expected.setId(id);
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id and offers.id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        Offer actual = new Offer();
        actual.setId(Integer.parseInt(result.get(0).get("offer_id")));
        Car c = new Car();
        c.setId(Integer.parseInt(result.get(0).get("car_id")));
        c.setName(result.get(0).get("car_name"));
        Founder f = new Founder(Integer.parseInt(result.get(0).get("founder_id")), result.get(0).get("founder_name_last"), result.get(0).get("founder_name"));
        Brand b = new Brand(Integer.parseInt(result.get(0).get("brand_id")), result.get(0).get("brand_name"), f);
        c.setBrand(b);
        Body abody = new Body();
        abody.setId(Integer.parseInt(result.get(0).get("body_id")));
        abody.setName(result.get(0).get("body_name"));
        User u = new User();
        u.setId(Integer.parseInt(result.get(0).get("user_id")));
        u.setName(result.get(0).get("user_name"));
        actual.setUser(u);
        actual.setBody(abody);
        actual.setPrice(Integer.parseInt(result.get(0).get("price")));
        actual.setStatus("t".equals(result.get(0).get("status")));
        query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", c.getId());
        result = driver.select(query);
        List<Body> carBodies = new ArrayList<>();
        for (HashMap<String, String> item : result) {
            carBodies.add(new Body(Integer.parseInt(item.get("id")), item.get("name")));
        }
        c.setBodies(carBodies);
        actual.setCar(c);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void delete(T obj) throws PersistenceException.
     * Тип: Founder.
     * @throws Exception исключение.
     */
    @Test
    public void testDeleteWithTypeFounder() throws Exception {
        Founder founder = new Founder(0, "ЦК КПСС", "Совет министров СССР");
        int id = this.dao.create(founder);
        founder.setId(id);
        // Удаляет запросом: delete from items where id=?
        this.dao.delete(founder);
        String query = String.format("select id from founders where id = %d", id);
        List<HashMap<String, String>> result = driver.select(query);
        assertTrue(result.isEmpty());
    }
    /**
     * Тестирует public void delete(T obj) throws PersistenceException.
     * Тип: Brand.
     * @throws Exception исключение.
     */
    @Test
    public void testDeleteWithTypeBrand() throws Exception {
        Founder founder = new Founder(0, "ЦК КПСС", "Совет министров СССР");
        int id = this.dao.create(founder);
        founder.setId(id);
        String name = "ВАЗ";
        Brand brand = new Brand(0, name, founder);
        id = this.dao.create(brand);
        brand.setId(id);
        this.dao.delete(brand);
        String query = String.format("select name from brands where id = %d", id);
        List<HashMap<String, String>> resultBrand = driver.select(query);
        query = String.format("select name_last from founders where id = %d", founder.getId());
        List<HashMap<String, String>> resultFounder = driver.select(query);
        assertTrue(resultBrand.isEmpty() && resultFounder.isEmpty());
    }
    /**
     * Тестирует public void delete(T obj) throws PersistenceException.
     * Тип: Car.
     * @throws Exception исключение.
     */
    @Test
    public void testDeleteWithTypeCar() throws Exception {
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
        List<HashMap<String, String>> resultCar = driver.select(query);
        query = String.format("select name from brands where id = %d", brand.getId());
        List<HashMap<String, String>> resultBrand = driver.select(query);
        query = String.format("select name_last from founders where id = %d", founder.getId());
        List<HashMap<String, String>> resultFounder = driver.select(query);
        assertTrue(resultCar.isEmpty() && resultBrand.isEmpty() && resultFounder.isEmpty());
    }
    /**
     * Тестирует public void delete(T obj) throws PersistenceException.
     * Тип: Offer.
     * @throws Exception исключение.
     */
    @Test
    public void testDeleteWithTypeOffer() throws Exception {
        Offer expected = this.getOffer();
        int id = this.dao.create(expected);
        expected.setId(id);
        this.dao.delete(expected);
        String query = String.format("select * from offers where id = %d", id);
        List<HashMap<String, String>> result = driver.select(query);
        assertTrue(result.isEmpty());
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
     * @throws Exception исключение.
     */
    @Test
    public void testReadWithTypeFounder() throws Exception {
        List<Founder> expected = new ArrayList<>();
        String query = "select id as \"id\", name_last as \"name_last\", name as \"name\" from founders order by name";
        List<HashMap<String, String>> result = driver.select(query);
        for (HashMap<String, String> item : result) {
            Founder founder = new Founder();
            founder.setId(Integer.parseInt(item.get("id")));
            founder.setNameLast(item.get("name_last"));
            founder.setName(item.get("name"));
            expected.add(founder);
        }
        List<IModel> actual = this.dao.read(new Founder());
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<T> read(T obj) throws PersistenceException.
     * Тип: Brand.
     * @throws Exception исключение.
     */
    @Test
    public void testReadWithTypeBrand() throws Exception {
        List<Brand> expected = new ArrayList<>();
        String query = "select brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\" from brands, founders where founder_id = founders.id order by brands.name";
        List<HashMap<String, String>> result = driver.select(query);
        for (HashMap<String, String> item : result) {
            Founder f = new Founder();
            f.setId(Integer.parseInt(item.get("founder_id")));
            f.setNameLast(item.get("founder_name_last"));
            f.setName(item.get("founder_name"));
            expected.add(new Brand(Integer.parseInt(item.get("brand_id")), item.get("brand_name"), f));
        }
        List<IModel> actual = this.dao.read(new Brand());
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<T> read(T obj) throws PersistenceException.
     * Тип: Car.
     * @throws Exception исключение.
     */
    @Test
    public void testReadWithTypeCar() throws Exception {
        List<Car> expected = new ArrayList<>();
        String query = "select cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\" from cars, brands, founders where cars.brand_id = brands.id and brands.founder_id = founders.id order by cars.name";
        List<HashMap<String, String>> result = driver.select(query);
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
            query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", car.getId());
            List<HashMap<String, String>> resultBodies = driver.select(query);
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
    }
    /**
     * Тестирует public void update(E obj).
     * Тип: Founder.
     * @throws Exception исключение.
     */
    @Test
    public void testUpdateWithTypeFounder() throws Exception {
        Founder expected = new Founder(0, "ЦК КПСС", "Совет министров СССР");
        int id = this.dao.create(expected);
        expected.setId(id);
        expected.setName("new name");
        this.dao.update(expected);
        String query = String.format("select id as \"id\", name_last as \"name_last\", name as \"name\" from founders where id = %d", id);
        List<HashMap<String, String>> result = driver.select(query);
        Founder actual = new Founder();
        actual.setId(Integer.parseInt(result.get(0).get("id")));
        actual.setNameLast(result.get(0).get("name_last"));
        actual.setName(result.get(0).get("name"));
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void update(E obj).
     * Тип: Brand.
     * @throws Exception исключение.
     */
    @Test
    public void testUpdateWithTypeBrand() throws Exception {
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
        String query = String.format("select brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\" from brands, founders where brands.founder_id = founders.id and brands.id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        Founder founder = new Founder();
        founder.setId(Integer.parseInt(result.get(0).get("founder_id")));
        founder.setNameLast(result.get(0).get("founder_name_last"));
        founder.setName(result.get(0).get("founder_name"));
        Brand actual = new Brand();
        actual.setId(Integer.parseInt(result.get(0).get("brand_id")));
        actual.setName(result.get(0).get("brand_name"));
        actual.setFounder(founder);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void update(E obj).
     * Тип: Car.
     * @throws Exception исключение.
     */
    @Test
    public void testUpdateWithTypeCar() throws Exception {
        Car expected = new Car();
        expected.setId(0);
        expected.setName("Нива");
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
        String query = String.format("select cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\" from cars, brands, founders where cars.brand_id = brands.id and brands.founder_id = founders.id and cars.id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        Car actual = new Car();
        actual.setId(Integer.parseInt(result.get(0).get("car_id")));
        actual.setName(result.get(0).get("car_name"));
        Founder f = new Founder(Integer.parseInt(result.get(0).get("founder_id")), result.get(0).get("founder_name_last"), result.get(0).get("founder_name"));
        Brand b = new Brand(Integer.parseInt(result.get(0).get("brand_id")), result.get(0).get("brand_name"), f);
        actual.setBrand(b);
        query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", expected.getId());
        result = driver.select(query);
        List<Body> abodies = new ArrayList<>();
        for (HashMap<String, String> item : result) {
            Body body = new Body();
            body.setId(Integer.parseInt(item.get("id")));
            body.setName(item.get("name"));
            abodies.add(body);
        }
        actual.setBodies(abodies);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public void update(E obj).
     * Тип: Offer.
     * @throws Exception исключение.
     */
    @Test
    public void testUpdateWithTypeOffer() throws Exception {
        Car car = new Car();
        car.setId(0);
        car.setName("Нива");
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body(3, "universal"));
        car.setBodies(bodies);
        Founder founder = new Founder(0, "ВСНХ", "Ford");
        int id = this.dao.create(founder);
        founder.setId(id);
        Brand brand = new Brand(0, "ГАЗ", founder);
        id = this.dao.create(brand);
        brand.setId(id);
        car.setBrand(brand);
        id = this.dao.create(car);
        car.setId(id);
        Offer expected = this.getOffer();
        id = this.dao.create(expected);
        expected.setId(id);
        expected.setBody(new Body(2, "hatchback"));
        expected.setPrice(999);
        expected.setStatus(true);
        expected.setUser(new User(2, "testUser2"));
        expected.setCar(car);
        this.dao.update(expected);
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id and offers.id = %d", expected.getId());
        List<HashMap<String, String>> result = driver.select(query);
        Offer actual = new Offer();
        actual.setId(Integer.parseInt(result.get(0).get("offer_id")));
        Car c = new Car();
        c.setId(Integer.parseInt(result.get(0).get("car_id")));
        c.setName(result.get(0).get("car_name"));
        Founder f = new Founder(Integer.parseInt(result.get(0).get("founder_id")), result.get(0).get("founder_name_last"), result.get(0).get("founder_name"));
        Brand b = new Brand(Integer.parseInt(result.get(0).get("brand_id")), result.get(0).get("brand_name"), f);
        c.setBrand(b);
        Body abody = new Body();
        abody.setId(Integer.parseInt(result.get(0).get("body_id")));
        abody.setName(result.get(0).get("body_name"));
        User u = new User();
        u.setId(Integer.parseInt(result.get(0).get("user_id")));
        u.setName(result.get(0).get("user_name"));
        actual.setUser(u);
        actual.setBody(abody);
        actual.setPrice(Integer.parseInt(result.get(0).get("price")));
        if (dbmsName.equals("HyperSQL")) {
            actual.setStatus("TRUE".equals(result.get(0).get("status")));
        } else if (dbmsName.equals("PostgreSQL")) {
            actual.setStatus("t".equals(result.get(0).get("status")));
        }
        query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", c.getId());
        result = driver.select(query);
        List<Body> carBodies = new ArrayList<>();
        for (HashMap<String, String> item : result) {
            carBodies.add(new Body(Integer.parseInt(item.get("id")), item.get("name")));
        }
        c.setBodies(carBodies);
        actual.setCar(c);
        assertEquals(expected, actual);
    }
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            driver.executeSqlScript(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.dao.close();
        }
    }
    /**
     * Действия после всех тестов.
     * @throws Exception исключение.
     */
    @AfterClass
    public static void beforeAllTest() throws Exception {
        driver.close();
        HibernateSessionFactory.get(cfgLocalFileName); //.close();
    }
}