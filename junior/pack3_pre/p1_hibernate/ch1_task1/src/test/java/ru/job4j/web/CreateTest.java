package ru.job4j.web;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.job4j.services.ItemDAO;
/**
 * Класс CreateTest тестирует класс Create.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-24
 * @since 2018-04-24
 */
public class CreateTest {
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
     * Сервлет.
     */
    private Create servlet;
    /**
     * ItemDAO.
     */
	private final ItemDAO idao = new ItemDAO();
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            MockitoAnnotations.initMocks(this);
            this.attributes = new ConcurrentHashMap<>();
            this.servlet = new Create();
            this.servlet.init(conf);
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
     * Тестирует public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     */
    @Test
    public void testDoPost() {
        try {
            StubServletOutputStream ssos = new StubServletOutputStream();
            String expected = "{\"id\":5,\"status\":\"ok\",\"errors\":{}}";
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("name")).thenReturn("CreateTestName");
            when(req.getParameter("descr")).thenReturn("CreateTestDescr");
            when(req.getParameter("created")).thenReturn("2018-04-24 18:26:00");
            when(req.getParameter("done")).thenReturn("false");
            when(resp.getOutputStream()).thenReturn(ssos);
            this.setAttributeStorage(req);
            servlet.doPost(req, resp);
            String actual = new String(ssos.getByteArray());
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException.
     * Есть ошибки валидации.
     */
    @Test
    public void testDoPostHasErrors() {
        try {
            StubServletOutputStream ssos = new StubServletOutputStream();
            String expected = "{\"status\":\"error\",\"errors\":{\"name\":\"isFilled\"}}";
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("name")).thenReturn("");
            when(req.getParameter("descr")).thenReturn("CreateTestDescr");
            when(req.getParameter("created")).thenReturn("2018-04-24 18:26:00");
            when(req.getParameter("done")).thenReturn("false");
            when(resp.getOutputStream()).thenReturn(ssos);
            this.setAttributeStorage(req);
            servlet.doPost(req, resp);
            String actual = new String(ssos.getByteArray());
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}