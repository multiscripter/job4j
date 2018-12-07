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
import ru.job4j.control.controller.Delete;
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.Address;
import ru.job4j.control.service.MusicType;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
/**
 * Класс DeleteTest тестирует класс Delete.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-23
 * @since 2018-01-12
 */
public class DeleteTest {
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
     * Счётчик.
     */
    private static int counter = 0;
    /**
     * Заглушка контекста сервлета.
     */
    @Mock
    private ServletContext ctx;
    /**
     * Пользователь для проверки работы удаления.
     */
    private User delUser;
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Заглушка диспатчера реквеста.
     */
    @Mock
    private RequestDispatcher reqDesp;
    /**
     * Сервлет.
     */
    private Delete servlet;
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
            this.driver.executeSql("delete from users where id > 4");
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
            this.servlet = new Delete();
            this.servlet.init(conf);
            this.us = new UserDAO();
            this.us.setEncoding(Charset.defaultCharset().toString());
            this.delUser = new User();
            this.delUser.setAddress(new Address(5, "РФ", "Москва", "ЛДПР"));
            this.delUser.setLogin("deleted");
            this.delUser.setPass(String.format("delpass%d", DeleteTest.getCount()));
            this.delUser.setRole(new Role(3, "user"));
            this.delUser.setMusicTypes(new LinkedList<>(Arrays.asList(new MusicType(3, "pop"))));
            this.us.addUser(this.delUser);
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
            User expected = this.delUser;
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn(Integer.toString(this.delUser.getId()));
            when(req.getSession(false)).thenReturn(sess);
            doReturn(this.us.getUserById(1)).when(sess).getAttribute("auth");
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/deleteGet.jsp")).thenReturn(reqDesp);
            this.setAttributeStorage(req);
            servlet.doGet(req, resp);
            User actual = (User) req.getAttribute("user");
            assertEquals(this.delUser, actual);
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
            int id = this.delUser.getId();
            String expected = String.format("Пользователь id:%d удалён.", id);
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn(Integer.toString(id));
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/deletePost.jsp")).thenReturn(reqDesp);
            this.setAttributeStorage(req);
            servlet.doPost(req, resp);
            String actual = (String) req.getAttribute("message");
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Увеличивает счётчик и возвращает новое значение.
     * @return новое значение.
     */
    public static int getCount() {
        return ++DeleteTest.counter;
    }
}