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
import org.junit.After;
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
 * Класс DeleteTest тестирует класс Delete.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-26
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
    private User deletedUser;
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
            driver.executeSql("delete from users where id > 14");
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
            this.servlet = new Delete();
            this.servlet.init(conf);
            this.enc = Charset.defaultCharset().toString();
            this.us = new UserService();
            this.us.setEncoding(this.enc);
            GregorianCalendar delCal = new GregorianCalendar();
            SimpleDateFormat delSdf = new SimpleDateFormat("yyyy-MM-dd");
            Date delDate = delSdf.parse("1952-11-11");
            delCal.setTime(delDate);
            int curCount = DeleteTest.getCount();
            String email = String.format("deleted%d@mail.domain", curCount);
            String pass = String.format("delpass%d", curCount);
            this.deletedUser = new User(0, "Удалённый", "deleted", email, delCal, pass, new Role(2, "user"));
            this.us.addUser(this.deletedUser);
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
            User expected = this.deletedUser;
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn(Integer.toString(this.deletedUser.getId()));
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
            int id = this.deletedUser.getId();
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