package ru.job4j.web;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
/**
 * Класс AttributeStorage реалиует сущность Хранилище атрибутов.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-28
 * @since 2018-05-28
 */
public class AttributeStorage {
    /**
     * Атрибуты.
     */
    private ConcurrentHashMap<String, Object> attributes = new ConcurrentHashMap<>();
    /**
     * Установить хранилище атрибутов.
     * @param req HTTP-запрос.
     */
    public void set(HttpServletRequest req) {
        // Mock setAttribute
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                Object value = invocation.getArgumentAt(1, Object.class);
                attributes.put(key, value);
                //System.err.println("Storage <<< key: " + key + ", val:" + value);
                return null;
            }
        }).when(req).setAttribute(Mockito.anyString(), Mockito.anyObject());
        // Mock getAttribute
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                Object value = attributes.get(key);
                //System.err.println("Storage >>> key: " + key + ", val: " + value);
                return value;
            }
        }).when(req).getAttribute(Mockito.anyString());
    }
}