package ru.job4j.testing;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
/**
 * Класс UpdateTest тестирует класс Update.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-26
 * @since 2017-12-17
 */
public class UpdateTest {
    /**
     * Админ.
     */
    private User admin;
    /**
     * Атрибуты.
     */
    private ConcurrentHashMap<String, Object> attributes;
    /**
     * Заглушка конфига сервлета.
     */
    @Mock
    private ServletConfig conf;
    /**
     * Заглушка контекста сервлета.
     */
    @Mock
    private ServletContext ctx;
    /**
     * Кодировка окружения.
     */
    private String enc;
    /**
     * Заглушка диспатчера реквеста.
     */
    @Mock
    private RequestDispatcher reqDesp;
    /**
     * Сервлет.
     */
    @Mock
    private Update servlet;
    /**
     * Заглушка сессии.
     */
    @Mock
    private HttpSession sess;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            Prepare pre = new Prepare();
            pre.loadProperties("junior.pack2.p9.ch7.task1.properties");
            PgSQLJDBCDriver dbDriver = new PgSQLJDBCDriver(pre.getProperties());
            dbDriver.setup();
            pre.setDbDriver(dbDriver);
            pre.executeSql("junior.pack2.p9.ch7.task1.sql");
            MockitoAnnotations.initMocks(this);
            GregorianCalendar cal = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("1952-10-07");
            cal.setTime(date);
            this.admin = new User(1, "Путин", "president", "putin@kremlin.gov", cal, "ab788932cee4ff449d2ec584da8af2b7", new Role(1, "administrator"));
            this.attributes = new ConcurrentHashMap<>();
            this.servlet = new Update();
            this.servlet.init(conf);
            this.enc = Charset.defaultCharset().toString();
            UserService us = new UserService();
            us.setEncoding(this.enc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Установить хранилище атрибутов.
     * @param req HTTP-запрос.
     */
    private void setAttributeStorage(HttpServletRequest req) {
        // Mock setAttribute
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                Object value = invocation.getArgumentAt(1, Object.class);
                attributes.put(key, value);
                //System.out.println("put attribute key=" + key + ", value=" + value);
                return null;
            }
        }).when(req).setAttribute(Mockito.anyString(), Mockito.anyObject());
        // Mock getAttribute
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                Object value = attributes.get(key);
                //System.out.println("get attribute value for key=" + key + " : " + value);
                return value;
            }
        }).when(req).getAttribute(Mockito.anyString());
    }
    /**
     * Тестирует public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     */
    @Test
    public void testDoGet() {
        try {
            User expected = this.admin;
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn("1");
            when(req.getSession(false)).thenReturn(sess);
            doReturn(this.admin).when(sess).getAttribute("auth");
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/updateGet.jsp")).thenReturn(reqDesp);
            this.setAttributeStorage(req);
            servlet.doGet(req, resp);
            User actual = (User) req.getAttribute("user");
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     */
    @Test
    public void testDoPost() {
        try {
            int id = 3;
            String expected = String.format("<p>Пользователь id:%d отредактирован.</p><br />\n", id);
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("name")).thenReturn(new String("Жирик".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("login")).thenReturn(new String("fakelogin".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("email")).thenReturn(new String("fake@email.domain".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("pass")).thenReturn(new String("fakepass".getBytes(this.enc), "ISO-8859-1"));
            when(req.getSession(false)).thenReturn(sess);
            doReturn(this.admin).when(sess).getAttribute("auth");
            when(req.getParameter("id")).thenReturn(Integer.toString(id));
            when(req.getParameter("role")).thenReturn("2");
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/updatePost.jsp")).thenReturn(reqDesp);
            this.setAttributeStorage(req);
            servlet.doPost(req, resp);
            String actual = (String) req.getAttribute("message");
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}