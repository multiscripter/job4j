package ru.job4j.htmlcss;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
//import org.junit.Ignore;
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
 * Класс CreateTest тестирует класс Create.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-18
 * @since 2017-12-18
 */
public class CreateTest {
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
    private String enc = "UTF-8";
    /**
     * Заглушка диспатчера реквеста.
     */
    @Mock
    private RequestDispatcher reqDesp;
    /**
     * Сервлет.
     */
    @Mock
    private Create servlet;
    /**
     * Заглушка сессии.
     */
    @Mock
    private HttpSession session;
    /**
     * UserService.
     */
    private UserService us;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            DBDriver driver = DBDriver.getInstance();
            driver.executeSqlScript("initial.sql");
            MockitoAnnotations.initMocks(this);
            GregorianCalendar cal = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("1952-10-07");
            cal.setTime(date);
            this.admin = new User();
            this.admin.setId(1);
            this.admin.setName("Путин");
            this.admin.setLogin("president");
            this.admin.setEmail("putin@kremlin.gov");
            this.admin.setDate(cal);
            this.admin.setPass("ab788932cee4ff449d2ec584da8af2b7");
            this.admin.setRole(new Role(1, "administrator"));
            this.admin.setCountry(new Country(2, "РФ"));
            this.admin.setCity(new City(1, "Москва", new LinkedList<>(Arrays.asList(1, 2))));
            this.attributes = new ConcurrentHashMap<>();
            this.servlet = new Create();
            this.servlet.init(conf);
            this.us = new UserService();
            this.us.setEncoding(this.enc);
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
                //System.out.println("get attribute value for key=" + key + " : " + attributes.get(key));
                return attributes.get(key);
            }
        }).when(req).getAttribute(Mockito.anyString());
    }
    /**
     * Тестирует public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     */
    @Test
    public void testDoGet() {
        try {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/createGet.jsp")).thenReturn(reqDesp);
            this.setAttributeStorage(req);
            servlet.doGet(req, resp);
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
            String login = "createfakelogin";
            String pass = "createfakepass";
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getSession(false)).thenReturn(this.session);
            doReturn(this.admin).when(this.session).getAttribute("auth");
            when(req.getParameter("name")).thenReturn(new String("Псевдоимя".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("login")).thenReturn(new String(login.getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("email")).thenReturn(new String("createfake@email.domain".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("pass")).thenReturn(new String(pass.getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("role")).thenReturn(new String("2".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("country")).thenReturn(new String("2".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("city")).thenReturn(new String("1".getBytes(this.enc), "ISO-8859-1"));
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/createGet.jsp")).thenReturn(reqDesp);
            when(ctx.getRequestDispatcher("/WEB-INF/views/createPost.jsp")).thenReturn(reqDesp);
            this.setAttributeStorage(req);
            servlet.doPost(req, resp);
            User user = this.us.getUserByLogPass(login, pass);
            String expected = String.format("Пользователь %s добавлен. id: %s", user.getName(), user.getId());
            String actual = (String) req.getAttribute("message");
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}