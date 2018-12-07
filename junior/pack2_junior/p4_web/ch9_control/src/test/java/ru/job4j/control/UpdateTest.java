package ru.job4j.control;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
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
import ru.job4j.control.controller.Update;
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.Address;
import ru.job4j.control.service.MusicType;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
/**
 * Класс UpdateTest тестирует класс Update.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2018-01-14
 */
public class UpdateTest {
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
     * Драйвер бд.
     */
    private DBDriver driver;
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
    private Update servlet;
    /**
     * Заглушка сессии.
     */
    @Mock
    private HttpSession sess;
    /**
     * Пользователь для проверки работы редактирования.
     */
    private User testUser;
    /**
     * UserDAO.
     */
    private UserDAO us;
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.us.deleteUser(this.testUser.getId());
            this.driver.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.driver = DBDriver.getInstance();
            if (!this.driver.isDBDriverSet()) {
                this.driver.setDbDriver();
            }
            MockitoAnnotations.initMocks(this);
            this.attributes = new ConcurrentHashMap<>();
            this.servlet = new Update();
            this.servlet.init(conf);
            this.enc = Charset.defaultCharset().toString();
            this.attributes.put("encoding", this.enc);
            this.us = new UserDAO();
            this.us.setEncoding(this.enc);
            this.testUser = new User();
            this.testUser.setAddress(new Address(0, "Никарагуа", "Манагуа", "ччч"));
            this.testUser.setLogin("updated");
            this.testUser.setPass("updated");
            this.testUser.setRole(new Role(3, "user"));
            this.testUser.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"))));
            this.us.addUser(this.testUser);
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
            when(req.getParameter("id")).thenReturn(Integer.toString(this.testUser.getId()));
            when(req.getSession(false)).thenReturn(sess);
            doReturn(this.us.getUserById(1)).when(sess).getAttribute("auth");
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/updateGet.jsp")).thenReturn(reqDesp);
            this.setAttributeStorage(req);
            servlet.doGet(req, resp);
            User actual = (User) req.getAttribute("user");
            assertEquals(this.testUser, actual);
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
            int id = this.testUser.getId();
            String expected = String.format("Пользователь id: %d отредактирован.", id);
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getAttribute("encoding")).thenReturn(this.enc);
            when(req.getParameter("id")).thenReturn(Integer.toString(id));
            when(req.getParameter("role")).thenReturn("2");
            when(req.getParameter("login")).thenReturn(new String("fakelogin".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("pass")).thenReturn(new String("fakepass".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("country")).thenReturn(new String("UpdateTestCountry".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("city")).thenReturn(new String("UpdateTestCity".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameter("addr")).thenReturn(new String("UpdateTestAddr".getBytes(this.enc), "ISO-8859-1"));
            when(req.getParameterValues("mtypes")).thenReturn(new String[]{"1", "2"});
            when(req.getSession(false)).thenReturn(sess);
            doReturn(this.us.getUserById(1)).when(sess).getAttribute("auth");
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