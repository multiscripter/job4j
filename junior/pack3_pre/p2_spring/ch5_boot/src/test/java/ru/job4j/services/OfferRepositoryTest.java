package ru.job4j.services;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Body;
import ru.job4j.models.Brand;
import ru.job4j.models.Car;
import ru.job4j.models.Founder;
import ru.job4j.models.Offer;
import ru.job4j.models.User;
import ru.job4j.utils.Handler;
import ru.job4j.utils.PropertyLoader;
import static org.junit.Assert.assertEquals;

/**
 * Класс OfferRepositoryTest тестирует класс OfferRepository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2018-06-26
 */
public class OfferRepositoryTest {
    /**
     * Драйвер бд.
     */
    private static DBDriver driver;
    /**
     * entityManager.
     */
    private EntityManager entityManager;
    /**
     * entityManagerFactory.
     */
    private static EntityManagerFactory emf;
    /**
     * Обработчик с вспомогательными методами.
     */
    private static Handler handler;
    /**
     * Путь файла sql.
     */
    private static String path;
    /**
     * Репозиторий объявлений.
     */
    private static OfferRepository repo;
    /**
     * Действия после теста.
     * @throws Exception исключение.
     */
    @After
    public void afterTest() throws Exception  {
        driver.executeSqlScript(path);
    }
    /**
     * Действия после всех тестов.
     * @throws Exception исключение.
     */
    @AfterClass
    public static void afterAllTests() throws Exception {
        driver.close();
    }
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @Before
    public void beforeTest() throws Exception {
        this.entityManager = emf.createEntityManager();
    }
    /**
     * Действия перед всеми тестами.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        path = UserRepositoryTest.class.getClassLoader().getResource(".").getPath();
        path = path.replaceFirst("^/(.:/)", "$1");
        String dbmsName = new PropertyLoader(String.format("%s%s", path, "activeDBMS.properties")).getPropValue("name");
        String contextLocalFileName = "servlet-context.xml";
        String persistenceUnitName = "persistenceUnitNameHibernate";
        if (!dbmsName.equals("PostgreSQL")) {
            contextLocalFileName = String.format("%s.servlet-context.xml", dbmsName);
            persistenceUnitName = String.format("persistenceUnitNameHibernate%s", dbmsName);
        }
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        driver = new DBDriver(path + dbmsName);
        path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        path = path.replaceFirst("^/(.:/)", "$1");
        path = String.format("%sjunior.pack3.p4.ch5.task1.%s.sql", path, dbmsName);
        driver.executeSqlScript(path);
        ctx.setConfigLocation("file:src/test/resources/" + contextLocalFileName);
        ctx.setServletContext(new MockServletContext());
        ctx.refresh();
        repo = ctx.getBean(OfferRepository.class);
        // Корнем web-приложения в тестах является папка test/resources/
        String ctxPath = ctx.getServletContext().getRealPath("/");
        ctxPath = String.format("%s/fotos", ctxPath);
        String fotosPath = Paths.get(ctxPath).normalize().toString();
        handler = new Handler("UTF-8", fotosPath);
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
            offer.setId(Long.parseLong(item.get("offer_id")));
            Car car = new Car();
            car.setId(Long.parseLong(item.get("car_id")));
            car.setName(item.get("car_name"));
            Founder founder = new Founder(Long.parseLong(item.get("founder_id")), item.get("founder_name_last"), item.get("founder_name"));
            Brand b = new Brand(Long.parseLong(item.get("brand_id")), item.get("brand_name"), founder);
            car.setBrand(b);
            Body body = new Body();
            body.setId(Long.parseLong(item.get("body_id")));
            body.setName(item.get("body_name"));
            User user = new User();
            user.setActivity(item.get("enabled").toUpperCase().startsWith("T"));
            user.setId(Long.parseLong(item.get("user_id")));
            user.setName(item.get("user_name"));
            user.setPass(item.get("pass"));
            offer.setUser(user);
            offer.setBody(body);
            offer.setPrice(Integer.parseInt(item.get("price")));
            offer.setStatus("t".equals(item.get("status")));
            String query = String.format("select bodies.id as \"id\", bodies.name as \"name\" from bodies, cars_bodies where bodies.id = cars_bodies.body_id and car_id = %d order by bodies.id", car.getId());
            List<HashMap<String, String>> result = driver.select(query);
            List<Body> carBodies = new ArrayList<>();
            for (HashMap<String, String> r : result) {
                carBodies.add(new Body(Long.parseLong(r.get("id")), r.get("name")));
            }
            car.setBodies(carBodies);
            offer.setCar(car);
            list.add(offer);
        }
    }
    /**
     * Тестирует List<T> findAll(@Nullable Specification<T> var1);.
     * Получение всех объявлений по идентификатору брэнда.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testFindAllOffersByBrandId() throws Exception {
        List<Offer> expected = new ArrayList<>();
        String brandId = "1";
        String orderBy = "offers.id";
        String orderDir = "desc";
        String query = String.format("select offers.id as \"offer_id\", users.id as \"user_id\", users.name as \"user_name\", users.password as \"pass\", users.enabled as \"enabled\", cars.id as \"car_id\", cars.name as \"car_name\", brands.id as \"brand_id\", brands.name as \"brand_name\", founders.id as \"founder_id\", founders.name_last as \"founder_name_last\", founders.name as \"founder_name\", bodies.id as \"body_id\", bodies.name as \"body_name\", price as \"price\", status as \"status\" from bodies, brands, cars, founders, offers, users where bodies.id = offers.body_id and users.id = offers.user_id and brands.id = cars.brand_id and founders.id = brands.founder_id and cars.id = offers.car_id and brands.id = %s group by offers.id, users.id, cars.id, brands.id, founders.id, bodies.id order by %s %s", brandId, orderBy, orderDir);
        List<HashMap<String, String>> result = driver.select(query);
        this.fillOfferList(expected, result);
        List<Offer> actual = new ArrayList<>();
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"brands", "id", "=", brandId});
        params.put("where", where);
        List<String[]> ends = new ArrayList<>();
        ends.add(new String[] {Offer.class.getName(), Brand.class.getName()});
        params.put("whereEnds", ends);
        List<String[]> order = new ArrayList<>();
        order.add(new String[]{"id", orderDir});
        params.put("order", order);
        CarStoreSpecification<Offer> csSpec = new CarStoreSpecification<>();
        csSpec.setEntityManager(this.entityManager);
        Specification<Offer> spec = csSpec.getSpec(params);
        for (Offer offer : repo.findAll(spec)) {
            offer.setFotos(handler.getFotos(Long.toString(offer.getId())));
            actual.add(offer);
        }
        assertEquals(expected, actual);
    }
}
