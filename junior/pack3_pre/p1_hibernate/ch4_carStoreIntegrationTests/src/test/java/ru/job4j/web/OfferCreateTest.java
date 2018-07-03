package ru.job4j.web;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.output.ByteArrayOutputStream;
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
import ru.job4j.models.Car;
import ru.job4j.services.Repository;
import org.springframework.mock.web.MockHttpServletRequest;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
/**
 * Класс OfferCreateTest тестирует класс OfferCreate.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-03
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
    private Repository repo = new Repository();
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
                this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch4task1", "postgres",
 "postgresrootpass");
            }
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            this.path = path.replaceFirst("^/(.:/)", "$1");
            this.path = String.format("%s../../src/test/resources/junior.pack3.p1.ch4.task1.%s.sql", this.path, db);
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
    @Test
    public void testDoPost() {
        try {
            String path = new File(".").getCanonicalPath();
            path = String.format("%s/webapps/ch4_carStoreIntegrationTests/fotos", path);
            String fotosPath = Paths.get(path).normalize().toString();
            MockHttpServletRequest req = new MockHttpServletRequest(this.ctx);
            this.ctx = req.getServletContext();
            this.ctx.setAttribute("javax.servlet.context.tempdir", "jsctdir");
            File file = new File(fotosPath + "/no-photo.png");
            PostMethod method = new PostMethod();
            String name = "TestName";
            String carId = "1";
            String body = "2";
            String price = "200500";
            String status = "false";
            MultipartRequestEntity entity = new MultipartRequestEntity(
                new Part[] {
                        new StringPart("name", name, "ISO-8859-1"),
                        new StringPart("car", carId, "ISO-8859-1"),
                        new StringPart("body", body, "ISO-8859-1"),
                        new StringPart("price", price, "ISO-8859-1"),
                        new StringPart("status", status, "ISO-8859-1"),
                        new FilePart("foto", file.getName(), file, "image/jpeg", null)
                }, method.getParams()
            );
            method.setRequestEntity(entity);
            req.setContentType(entity.getContentType());
            // Serialize request body
            ByteArrayOutputStream requestContent = new ByteArrayOutputStream();
            entity.writeRequest(requestContent);
            req.setContent(requestContent.toByteArray());
            req.addHeader("Content-Type", entity.getContentType());
            HttpServletResponse resp = mock(HttpServletResponse.class);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"cars", "id", "=",  carId});
            params.put("where", where);
            Car car = (Car) this.repo.get("Car", params).get(0);
            String expected = String.format("%s, объявление о продаже %s %s за %s рублей создано.", name, car.getBrand().getName(), car.getName(), price);
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/offerCreatePost.jsp")).thenReturn(reqDesp);
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
            this.driver.executeSqlScript(this.path);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        } finally {
            this.repo.close();
        }
    }
}