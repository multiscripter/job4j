package ru.job4j.control.controller;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.control.service.User;
/**
 * Класс LoginAuthFilter реализует фильтр авторизации пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-23
 * @since 2018-01-23
 */
public class LoginAuthFilter extends AuthFilter {
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
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            String ctxURI = req.getRequestURI();
            HttpSession sess = req.getSession();
            User user = null;
            if (sess.getAttribute("auth") != null) {
                user = (User) sess.getAttribute("auth");
            }
            if (req.getParameter("auth") != null && req.getParameter("auth").equals("logout")) {
                sess.setAttribute("auth", null);
                if (req.getHeader("referer") != null) {
                    resp.sendRedirect(req.getHeader("referer"));
                } else {
                    resp.sendRedirect(String.format("%s/", req.getContextPath()));
                }
                return;
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
}