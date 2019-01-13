package ru.job4j.web;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import ru.job4j.services.DAO;
/**
 * Класс OfferDeleteTest тестирует класс OfferDelete.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-08
 * @since 2018-05-29
 */
public class OfferDeleteTest {
    /**
     * Заглушка контекста сервлета.
     */
    @Mock
    private ServletContext ctx;
    /**
     * DAO.
     */
    private DAO dao;
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Путь файла sql.
     */
    private String path;
    /**
     * Заглушка диспатчера реквеста.
     */
    @Mock
    private RequestDispatcher reqDesp;
    /**
     * Сервлет.
     */
    private OfferDelete servlet;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            // При тестировании с PostgreSQL фпереименовать test/resources/hibernate.cfg.xml
            String db = "HyperSQL"; // HyperSQL | PostgreSQL
            if (db.equals("HyperSQL")) {
                /**
                 * По-умолчанию HSQLDB в запросах использует имёна столбцов из схемы таблицы, игнорируя алиасы !!!
                 * То есть запрос "select col_name as col_alias from . . . " вернёт в результирующем наборе
                 * col_name=значение, а не col_alias=значение. Выключается это совершенно дибильное поведение
                 * опцией get_column_name=false
                 */
                this.driver = new DBDriver("jdbc:hsqldb:mem:jpack3p1ch4task1;get_column_name=false", "SA", "");
            } else if (db.equals("PostgreSQL"))  {
                this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch4task1", "postgres", "postgresrootpass");
            }
            this.dao = new DAO();
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            this.path = path.replaceFirst("^/(.:/)", "$1");
            this.path = String.format("%s../../src/test/resources/junior.pack3.p1.ch4.task1.%s.sql", this.path, db);
            this.driver.executeSqlScript(this.path);
            MockitoAnnotations.initMocks(this);
            this.servlet = new OfferDelete();
            ServletConfig conf = mock(ServletConfig.class);
            this.servlet.init(conf);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * СОздаёт папку с заглушками изображений объявления.
     * @param id идентификатор объявления.
     * @throws Exception исключение.
     */
    private void createFakeFotos(int id) throws Exception {
        String path = new File(".").getCanonicalPath();
        path = String.format("%s/webapps/ch4_carStoreIntegrationTests/fotos", path);
        String fotosPath = Paths.get(path).normalize().toString();
        String dirName = String.format("%s/%d", fotosPath, id);
        File dir = new File(dirName);
        if (!dir.exists() && !dir.mkdir()) {
            throw new Exception(String.format("Folder %s not created.", dirName));
        }
        String name = String.format("%s/%d/%s", fotosPath, id, "fakeFoto1.jpg");
        File foto = new File(name);
        if (!foto.exists() && !foto.createNewFile()) {
            throw new Exception(String.format("%s not created.", name));
        }
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
     * Тестирует public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     */
    @Test
    public void testDoGet() {
        try {
            Offer expected = this.getOffer();
            int id = this.dao.create(expected);
            expected.setId(id);
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn(Integer.toString(id));
            when(this.servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/offerDeleteGet.jsp")).thenReturn(reqDesp);
            AttributeStorage storage = new AttributeStorage();
            storage.set(req);
            this.servlet.doGet(req, resp);
            Offer actual = (Offer) req.getAttribute("offer");
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     */
    @Test
    public void testDoPost() {
        try {
            Offer offer = this.getOffer();
            int id = this.dao.create(offer);
            offer.setId(id);
            this.createFakeFotos(id);
            // Удаление.
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            String expected = String.format("%s, объявление о продаже %s %s за %s рублей удалено.", offer.getUser().getName(), offer.getCar().getBrand().getName(), offer.getCar().getName(), offer.getPrice());
            when(req.getParameter("id")).thenReturn(Integer.toString(id));
            when(req.getParameter("name")).thenReturn("testUser1");
            when(this.servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/offerDeletePost.jsp")).thenReturn(reqDesp);
            AttributeStorage storage = new AttributeStorage();
            storage.set(req);
            this.servlet.doPost(req, resp);
            String actual = (String) req.getAttribute("msg");
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     * Ошибка. Объявление не существует.
     */
    @Test
    public void testDoPostErrorOfferNotExists() {
        try {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn("999");
            when(req.getParameter("name")).thenReturn("testUser1");
            when(this.servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/offerDeletePost.jsp")).thenReturn(reqDesp);
            AttributeStorage storage = new AttributeStorage();
            storage.set(req);
            this.servlet.doPost(req, resp);
            String expected = "Объявление уже удалено или не существует.";
            String actual = (String) req.getAttribute("msg");
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     * Ошибка. Пользователь не имеет прав для удаления объявления.
     */
    @Test
    public void testDoPostErrorUserHasNoRights() {
        try {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn("1");
            when(req.getParameter("name")).thenReturn("FakeUser");
            when(this.servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/offerDeletePost.jsp")).thenReturn(reqDesp);
            AttributeStorage storage = new AttributeStorage();
            storage.set(req);
            this.servlet.doPost(req, resp);
            String expected = "У вас нет прав для удаления этого объявления.";
            String actual = (String) req.getAttribute("msg");
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     * Фатальная ошибка.
     */
    @Test
    public void testDoPostFatalError() {
        try {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn("NotANumber");
            when(req.getParameter("name")).thenReturn("FakeUser");
            when(this.servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/offerDeletePost.jsp")).thenReturn(reqDesp);
            AttributeStorage storage = new AttributeStorage();
            storage.set(req);
            this.servlet.doPost(req, resp);
            String expected = "Ошибка удаления объявления.";
            String actual = (String) req.getAttribute("msg");
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
            try {
                this.driver.executeSqlScript(this.path);
            } catch (Exception ex) {
                this.logger.error("ERROR", ex);
                ex.printStackTrace();
            } finally {
                this.dao.close();
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}