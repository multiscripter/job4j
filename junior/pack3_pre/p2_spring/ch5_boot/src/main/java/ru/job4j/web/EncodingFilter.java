package ru.job4j.web;

import java.io.IOException;
import java.util.TimeZone;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Класс EncodingFilter реализует фильтр кодировки.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-02
 * @since 2018-05-10
 */
public class EncodingFilter implements Filter {
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Инициализатор.
     * @param filterConfig конфигурация фильтра.
     * @throws javax.servlet.ServletException исключение сервлета.
    */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Установить часовой пояс по умолчанию для всего приложения.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    /**
	 * Производит фильтрацию.
     * @param req запрос.
     * @param resp ответ.
     * @param chain цепь.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
	 */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            resp.setContentType("text/html");
            String enc = "UTF-8";
            resp.setCharacterEncoding(enc);
            req.setAttribute("encoding", enc);
            chain.doFilter(req, resp);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
	 * Вызывается при уничтожении сервлета.
	 */
    @Override
    public void destroy() {
    }
}