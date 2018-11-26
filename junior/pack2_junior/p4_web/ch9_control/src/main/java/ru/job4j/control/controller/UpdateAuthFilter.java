package ru.job4j.control.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import ru.job4j.control.persistence.RoleDAO;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
/**
 * Класс UpdateAuthFilter реализует фильтр авторизации пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-23
 * @since 2018-01-23
 */
public class UpdateAuthFilter extends AuthFilter {
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Роль администратора.
     */
    private Role role;
    /**
     * Инициализатор.
     * @param filterConfig конфигурация фильтра
     * @throws javax.servlet.ServletException исключение сервлета.
    */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            super.init(filterConfig);
            this.logger = LogManager.getLogger(this.getClass().getName());
            this.role = (new RoleDAO()).getRoleByName("administrator");
		} catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
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
            if (user == null || (user.getId() != Integer.parseInt(req.getParameter("id")) && user.getRoleId() != this.role.getId())) {
                resp.sendRedirect(String.format("%s/login/", req.getContextPath()));
                return;
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
}