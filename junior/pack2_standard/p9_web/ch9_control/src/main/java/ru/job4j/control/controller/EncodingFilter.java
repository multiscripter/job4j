package ru.job4j.control.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс EncodingFilter реализует фильтр кодировки.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-23
 * @since 2018-01-23
 */
public class EncodingFilter extends AuthFilter {
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Инициализатор.
     * @param filterConfig конфигурация фильтра
     * @throws javax.servlet.ServletException исключение сервлета.
    */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        this.logger = LogManager.getLogger(this.getClass().getName());
    }
    /**
	 * Производит фильтрацию.
     * @param request запрос.
     * @param response ответ.
     * @param chain цепь.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
	 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String enc = Charset.defaultCharset().toString();
            response.setCharacterEncoding(enc);
            request.setAttribute("encoding", enc);
            chain.doFilter(request, response);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
}