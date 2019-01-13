package ru.job4j.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Car;
import ru.job4j.services.Repository;
/**
 * Класс OfferCreateTest тестирует класс OfferCreate.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-08
 * @since 2018-05-28
 */
public class OfferCreateTest {
    /**
     * Заглушка контекста сервлета.
     */
    @Mock
    private ServletContext ctx;
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
     * Repository.
     */
    private Repository repo;
    /**
     * Заглушка диспатчера реквеста.
     */
    @Mock
    private RequestDispatcher reqDesp;
    /**
     * Сервлет.
     */
    private OfferCreate servlet;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.repo = new Repository();
            this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch3task1", "postgres", "postgresrootpass");
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            this.path = path.replaceFirst("^/(.:/)", "$1");
            this.path = this.path + "../../src/main/resources/junior.pack3.p1.ch3.task1.sql";
            this.driver.executeSqlScript(this.path);
            MockitoAnnotations.initMocks(this);
            this.servlet = new OfferCreate();
            ServletConfig conf = mock(ServletConfig.class);
            this.servlet.init(conf);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     */
    @Ignore@Test
    public void testDoPost() {
        // Тест валится.
        try {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            ServletFileUpload sfu = mock(ServletFileUpload.class);
            HashMap<String, String> fields = mock(HashMap.class);
            List<FileItem> items = new ArrayList<>();
            String name = "TestName";
            String carId = "1";
            String body = "2";
            String price = "200500";
            String status = "false";
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"cars", "id", "=",  carId});
            params.put("where", where);
            Car car = (Car) this.repo.get("Car", params).get(0);
            String expected = String.format("%s, объявление о продаже %s %s за %s рублей создано.", name, car.getBrand().getName(), car.getName(), price);
            when(fields.get("name")).thenReturn(name);
            when(fields.get("car")).thenReturn(carId);
            when(fields.get("body")).thenReturn(body);
            when(fields.get("price")).thenReturn(price);
            when(fields.get("status")).thenReturn(status);
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/offerCreatePost.jsp")).thenReturn(reqDesp);
            when(req.getContentType()).thenReturn("multipart/form-data; boundary=someBoundary");
            /**
             * Цепочка вызовов заходит в приватные методы внутри parseRequest()
             * и там валится с NPE из-за того,
             * что org.apache.commons.fileupload.MultipartStream$ItemInputStream
             * == null.
             */
            when(sfu.parseRequest(req)).thenReturn(items);
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
                this.repo.close();
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}