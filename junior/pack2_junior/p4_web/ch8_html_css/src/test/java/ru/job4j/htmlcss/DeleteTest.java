package ru.job4j.htmlcss;

import java.nio.charset.Charset;
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
/**
 * Класс DeleteTest тестирует класс Delete.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-12-18
 */
public class DeleteTest {
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
     * Счётчик.
     */
    private static int counter = 0;
    /**
     * Заглушка контекста сервлета.
     */
    @Mock
    private ServletContext ctx;
    /**
     * Пользователь для проверик работы удаления.
     */
    private User delUser;
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
     * UserService.
     */
    private UserService us;
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            DBDriver driver = DBDriver.getInstance();
            driver.executeSql("delete from users where id > 4");
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
            this.servlet = new Delete();
            this.servlet.init(conf);
            this.us = new UserService();
            this.us.setEncoding(Charset.defaultCharset().toString());
            GregorianCalendar delCal = new GregorianCalendar();
            SimpleDateFormat delSdf = new SimpleDateFormat("yyyy-MM-dd");
            Date delDate = delSdf.parse("1952-11-11");
            delCal.setTime(delDate);
            int curCount = DeleteTest.getCount();
            String email = String.format("deleted%d@mail.domain", curCount);
            String pass = String.format("delpass%d", curCount);
            this.delUser = new User();
            this.delUser.setName("Удалённый");
            this.delUser.setLogin("deleted");
            this.delUser.setEmail(email);
            this.delUser.setDate(delCal);
            this.delUser.setPass(pass);
            this.delUser.setRole(new Role(2, "user"));
            this.delUser.setCountry(new Country(2, "РФ"));
            this.delUser.setCity(new City(1, "Москва", new LinkedList<>(Arrays.asList(1, 2))));
            us.addUser(this.delUser);
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
            doReturn(this.admin).when(sess).getAttribute("auth");
            when(servlet.getServletContext()).thenReturn(ctx);
            when(ctx.getRequestDispatcher("/WEB-INF/views/deleteGet.jsp")).thenReturn(reqDesp);
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
            //assertEquals(expected, actual);
            System.out.println("DeleteTest.testDoPost() desabled");
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