package ru.job4j.control;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.job4j.control.controller.Read;
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.User;
/**
 * Класс ReadTest тестирует класс Read.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-15
 * @since 2018-01-12
 */
public class ReadTest {
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
    private Read servlet;
    /**
     * Заглушка сессии.
     */
    @Mock
    private HttpSession sess;
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
            this.driver.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.driver = DBDriver.getInstance("junior.pack2.p9.ch9.task1");
            if (!this.driver.isDBDriverSet()) {
                this.driver.setDbDriver();
            }
            MockitoAnnotations.initMocks(this);
            this.attributes = new ConcurrentHashMap<>();
            this.servlet = new Read();
            this.servlet.init(conf);
            this.enc = Charset.defaultCharset().toString();
            this.us = new UserDAO();
            this.us.setEncoding(this.enc);
        } catch (ServletException ex) {
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
            LinkedList<User> expected = this.us.getUsers("id", false);
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(this.servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/readGet.jsp")).thenReturn(reqDesp);
            this.setAttributeStorage(req);
            this.servlet.doGet(req, resp);
            LinkedList<User> actual = (LinkedList<User>) req.getAttribute("users");
            assertEquals(expected, actual);
        } catch (IOException | NoSuchAlgorithmException | ParseException | ServletException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}