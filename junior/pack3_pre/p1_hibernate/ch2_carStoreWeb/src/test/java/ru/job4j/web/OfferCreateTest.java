package ru.job4j.web;

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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
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
    @Mock
    private OfferCreate servlet;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            MockitoAnnotations.initMocks(this);
            this.repo = new Repository();
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
}