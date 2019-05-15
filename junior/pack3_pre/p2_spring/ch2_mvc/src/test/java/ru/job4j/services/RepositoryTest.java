package ru.job4j.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
 * Класс RepositoryTest тестирует класс Repository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-15
 * @since 2018-05-10
 */
//@Ignore
public class RepositoryTest {
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
     * Репозиторий машинок.
     */
    private static Repository repo;
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
        repo = new Repository(cfgLocalFileName);
    }
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @Before
    public void beforeTest() throws Exception {
        driver.executeSqlScript(path);
    }
    /**
     * Заполняет список автомобилями.
     * @param expected список автомобилей.
     * @param dataList список результатов выборки из бд.
     * @throws Exception исключение.
     */
    private void fillCarList(List<Car> expected, List<HashMap<String, String>> dataList) throws Exception {
        for (HashMap<String, String> item : dataList) {
            Car car = new Car();
            car.setId(Integer.parseInt(item.get("car_id")));
            car.setName(item.get("car_name"));
            Founder founder = new Founder(Integer.parseInt(item.get("founder_id")), item.get("founder_name_last"), item.get("founder_name"));
            Brand brand = new Brand(Integer.parseInt(item.get("brand_id")), item.get("brand_name"), founder);
            car.setBrand(brand);
            // Body.
            String query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = body_id and car_id = %d order by bodies.id", car.getId());
            List<HashMap<String, String>> result = driver.select(query);
            ArrayList<Body> bodies = new ArrayList<>();
            for (HashMap<String, String> entry : result) {
                Body body = new Body(Integer.parseInt(entry.get("id")), entry.get("name"));
                bodies.add(body);
            }
            car.setBodies(bodies);
            expected.add(car);
        }
    }
    /**
     * Заполняет список объявлений.
     * @param list заполняемый список.
     * @param data список с картами данных.
     * @throws Exception исключение.
     */
    private void fillOfferList(List<Offer> list, List<HashMap<String, String>> data) throws Exception {
        for (HashMap<String, String> item : data) {
            Offer offer = new Offer();
            offer.setId(Integer.parseInt(item.get("offer_id")));
            Car car = new Car();
            car.setId(Integer.parseInt(item.get("car_id")));
            car.setName(item.get("car_name"));
            Founder founder = new Founder(Integer.parseInt(item.get("founder_id")), item.get("founder_name_last"), item.get("founder_name"));
            Brand b = new Brand(Integer.parseInt(item.get("brand_id")), item.get("brand_name"), founder);
            car.setBrand(b);
            Body body = new Body();
            body.setId(Integer.parseInt(item.get("body_id")));
            body.setName(item.get("body_name"));
            User user = new User();
            user.setId(Integer.parseInt(item.get("user_id")));
            user.setName(item.get("user_name"));
            offer.setUser(user);
            offer.setBody(body);
            offer.setPrice(Integer.parseInt(item.get("price")));
            offer.setStatus("t".equals(item.get("status")));
            String query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", car.getId());
            List<HashMap<String, String>> result = driver.select(query);
            List<Body> carBodies = new ArrayList<>();
            for (HashMap<String, String> r : result) {
                carBodies.add(new Body(Integer.parseInt(r.get("id")), r.get("name")));
            }
            car.setBodies(carBodies);
            offer.setCar(car);
            list.add(offer);
        }
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * Тип: User.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetByIdWithTypeUser() throws Exception  {
        int id = 1;
        String query = String.format("select id as \"id\", name as \"name\" from users where id = %d", id);

        HashMap<String, String> result = driver.select(query).get(0);
        User expected = new User();
        expected.setId(Integer.parseInt(result.get("id")));
        expected.setName(result.get("name"));
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"users", "id", "=", Integer.toString(id)});
        params.put("where", where);
        User actual = (User) repo.get("User", params).get(0);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetByIdWithTypeBrand() throws Exception {
        int id = 1;
        String query = String.format("select brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name as \"founder_name\", founders.name_last as \"founder_name_last\" from brands, founders where brands.founder_id = founders.id and brands.id = %d", id);

        HashMap<String, String> result = driver.select(query).get(0);
        Founder founder = new Founder();
        founder.setId(Integer.parseInt(result.get("founder_id")));
        founder.setName(result.get("founder_name"));
        founder.setNameLast(result.get("founder_name_last"));
        Brand expected = new Brand();
        expected.setId(Integer.parseInt(result.get("brand_id")));
        expected.setName(result.get("brand_name"));
        expected.setFounder(founder);
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"brands", "id", "=", Integer.toString(id)});
        params.put("where", where);
        Brand actual = (Brand) repo.get(Brand.class.getSimpleName(), params).get(0);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetCarByIdWithTypeOffer() throws Exception {
        int id = 1;
        Offer expected = new Offer();
        expected.setId(id);
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id and offers.id = %d", id);

        HashMap<String, String> result = driver.select(query).get(0);
        Founder founder = new Founder(Integer.parseInt(result.get("founder_id")), result.get("founder_name_last"), result.get("founder_name"));
        Brand brand = new Brand(Integer.parseInt(result.get("brand_id")), result.get("brand_name"), founder);
        Car car = new Car();
        car.setId(Integer.parseInt(result.get("car_id")));
        car.setName(result.get("car_name"));
        car.setBrand(brand);
        User user = new User();
        user.setId(Integer.parseInt(result.get("user_id")));
        user.setName(result.get("user_name"));
        expected.setUser(user);
        Body abody = new Body();
        abody.setId(Integer.parseInt(result.get("body_id")));
        abody.setName(result.get("body_name"));
        expected.setBody(abody);
        query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", car.getId());
        List<HashMap<String, String>> bodies = driver.select(query);
        List<Body> carBodies = new ArrayList<>();
        for (HashMap<String, String> item : bodies) {
            carBodies.add(new Body(Integer.parseInt(item.get("id")), item.get("name")));
        }
        car.setBodies(carBodies);
        expected.setCar(car);
        expected.setPrice(Integer.parseInt(result.get("price")));
        expected.setStatus("t".equals(result.get("status")));
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"cars", "id", "=", Integer.toString(id)});
        params.put("where", where);
        Offer actual = (Offer) repo.get(Offer.class.getSimpleName(), params).get(0);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetOfferListByBrandId() throws Exception {
        int brandId = 1;
        List<Offer> expected = new ArrayList<>();
        List<Offer> actual = new ArrayList<>();
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id and brands.id = %d", brandId);

        List<HashMap<String, String>> result = driver.select(query);
        for (HashMap<String, String> item : result) {
            Offer offer = new Offer();
            offer.setId(Integer.parseInt(item.get("offer_id")));
            Founder founder = new Founder(Integer.parseInt(item.get("founder_id")), item.get("founder_name_last"), item.get("founder_name"));
            Brand brand = new Brand(Integer.parseInt(item.get("brand_id")), item.get("brand_name"), founder);
            Car car = new Car();
            car.setId(Integer.parseInt(item.get("car_id")));
            car.setName(item.get("car_name"));
            car.setBrand(brand);
            User user = new User();
            user.setId(Integer.parseInt(item.get("user_id")));
            user.setName(item.get("user_name"));
            offer.setUser(user);
            Body abody = new Body();
            abody.setId(Integer.parseInt(item.get("body_id")));
            abody.setName(item.get("body_name"));
            offer.setBody(abody);
            query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", car.getId());
            List<HashMap<String, String>> bodies = driver.select(query);
            List<Body> carBodies = new ArrayList<>();
            for (HashMap<String, String> body : bodies) {
                carBodies.add(new Body(Integer.parseInt(body.get("id")), body.get("name")));
            }
            car.setBodies(carBodies);
            offer.setCar(car);
            offer.setPrice(Integer.parseInt(item.get("price")));
            offer.setStatus("t".equals(item.get("status")));
            expected.add(offer);
        }
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"brands", "id", "=", Integer.toString(brandId)});
        params.put("where", where);
        for (IModel item : repo.get(Offer.class.getSimpleName(), params)) {
            actual.add((Offer) item);
        }
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetByNameWithTypeUser() throws Exception {
        String name = "testUser1";
        String query = String.format("select id as \"id\", name as \"name\" from users where name = '%s'", name);

        List<HashMap<String, String>> result = driver.select(query);
        User expected = new User();
        expected.setId(Integer.parseInt(result.get(0).get("id")));
        expected.setName(result.get(0).get("name"));
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"users", "name", "=", name});
        params.put("where", where);
        User actual = (User) repo.get("User", params).get(0);
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetCarListByBodyIdEquals1() throws Exception {
        List<Car> expected = new ArrayList<>();
        int id = 1;
        String query = String.format("select cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name as \"founder_name\", founders.name_last as \"founder_name_last\" from cars, cars_bodies, brands, founders where brands.founder_id = founders.id and cars.brand_id = brands.id and cars.id = car_id and body_id = %d group by cars.id, brands.id, founders.id", id);

        List<HashMap<String, String>> result = driver.select(query);
        this.fillCarList(expected, result);
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"bodies", "id", "=", Integer.toString(id)});
        params.put("where", where);
        List<Car> actual = new ArrayList<>();
        for (IModel item : repo.get("Car", params)) {
            actual.add((Car) item);
        }
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetOfferListByIdIn() throws Exception {
        List<Offer> expected = new ArrayList<>();
        String ids = "1, 3";
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id and offers.id in (%s) group by offers.id, users.id, cars.id, brands.id, founders.id, bodies.id", ids);

        List<HashMap<String, String>> result = driver.select(query);
        this.fillOfferList(expected, result);
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"offers", "id", "in", "1", "3"});
        params.put("where", where);
        List<Offer> actual = new ArrayList<>();
        for (IModel item : repo.get("Offer", params)) {
            actual.add((Offer) item);
        }
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetOfferListByIdBetween() throws Exception {
        List<Offer> expected = new ArrayList<>();
        int from = 2;
        int to = 3;
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id and offers.id between %d and %d group by offers.id, users.id, cars.id, brands.id, founders.id, bodies.id", from, to);

        List<HashMap<String, String>> result = driver.select(query);
        this.fillOfferList(expected, result);
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"offers", "id", "between", Integer.toString(from), Integer.toString(to)});
        params.put("where", where);
        List<Offer> actual = new ArrayList<>();
        for (IModel item : repo.get("Offer", params)) {
            actual.add((Offer) item);
        }
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<IModel> get(final String type, HashMap<String, List<String[]>> params) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetWithOfferOrderBy() throws Exception {
        List<Offer> expected = new ArrayList<>();
        String orderBy = "price";
        String orderDir = "desc";
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id group by offers.id, users.id, cars.id, brands.id, founders.id, bodies.id order by %s %s", orderBy, orderDir);

        List<HashMap<String, String>> result = driver.select(query);
        this.fillOfferList(expected, result);
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> order = new ArrayList<>();
        order.add(new String[]{orderBy, orderDir});
        params.put("order", order);
        List<Offer> actual = new ArrayList<>();
        for (IModel item : repo.get("Offer", params)) {
            actual.add((Offer) item);
        }
        assertEquals(expected, actual);
    }
    /**
     * Тестирует public List<Offer> getOffersByBrand(String orderDir) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetOffersByBrand() throws Exception {
        List<Offer> expected = new ArrayList<>();
        String brandId = "1";
        String orderBy = "offers.id";
        String orderDir = "desc";
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id and brands.id = %s group by offers.id, users.id, cars.id, brands.id, founders.id, bodies.id order by %s %s", brandId, orderBy, orderDir);

        List<HashMap<String, String>> result = driver.select(query);
        this.fillOfferList(expected, result);
        List<Offer> actual = repo.getOffersByBrandId("1", "desc");
        assertEquals(expected, actual);
    }
    /**
     * Тестирует private List<IModel> getResult(String query) throws Exception.
     * @throws java.lang.Exception исключение.
     */
    @Test(expected = Exception.class)
    public void testGetResultThrowsException() throws Exception {
        Repository mockedRepo = mock(Repository.class);
        SessionFactory mockedFactory = mock(SessionFactory.class);
        Session mockedSession = mock(Session.class);
        Transaction mockerTransaction = mock(Transaction.class);
        when(mockedFactory.openSession()).thenReturn(mockedSession);
        when(mockedSession.getTransaction()).thenReturn(mockerTransaction);
        doThrow(new Exception()).when(mockerTransaction).commit();
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[] {"bodies", "id", "=", "1"});
        params.put("where", where);
        mockedRepo.get("Body", params);
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
        }
    }
    /**
     * Действия после всех тестов.
     * @throws Exception исключение.
     */
    @AfterClass
    public static void beforeAllTest() throws Exception {
        driver.close();
        repo.close();
        HibernateSessionFactory.get(cfgLocalFileName); //.close();
    }
}