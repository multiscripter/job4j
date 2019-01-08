package ru.job4j.htmlcss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс AuthFilter реализует функционал авторизации пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-08
 * @since 2017-12-12
 */
public class AuthFilter implements Filter {
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Роль администратора.
     */
    private Role role;
    /**
     * UserService.
     */
    private UserService us;
    /**
     * Инициализатор.
     * @param filterConfig конфигурация фильтра
     * @throws ServletException исключение сервлета.
    */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
			// /var/lib/tomcat8/webapps/ch8_html_css-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch8_html_css-1.0\WEB-INF\classes
            String path = new File(Login.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			path = path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch8_html_css_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Login");
            this.role = (new RoleService()).getRoleByName("administrator");
            this.us = new UserService();
		} catch (IllegalAccessException | InstantiationException | URISyntaxException | ClassNotFoundException | SQLException | IOException ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Производит фильтрацию.
     * @param request запрос.
     * @param response ответ.
     * @param chain цепочка.
     * @throws IOException исключение ввода-вывода.
     * @throws ServletException исключение сервлета.
	 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            String ctxURI = req.getRequestURI();
            if (!ctxURI.contains("/static/")) {
                HttpSession sess = req.getSession();
                User user = null;
                if (sess.getAttribute("auth") != null) {
                    user = (User) sess.getAttribute("auth");
                }
                if (user == null && !"logout".equals(req.getParameter("auth"))) {
                    Cookie[] cookies = req.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if ("auth".equals(cookie.getName())) {
                                user = this.us.getUserById(Integer.parseInt(cookie.getValue()));
                                if (user != null) {
                                    sess.setAttribute("auth", user);
                                }
                                break;
                            }
                        }
                    }
                }
                if (ctxURI.contains("/login")) {
                    if (req.getParameter("auth") != null && req.getParameter("auth").equals("logout")) {
                        sess.setAttribute("auth", null);
                        Cookie[] cookies = req.getCookies();
                        if (cookies != null) {
                            for (Cookie cookie : cookies) {
                                if ("auth".equals(cookie.getName())) {
                                    cookie.setMaxAge(0);
                                    cookie.setPath("/");
                                    cookie.setValue("");
                                    resp.addCookie(cookie);
                                }
                            }
                        }
                        resp.sendRedirect(req.getHeader("referer"));
                        return;
                    }
                } else if (ctxURI.contains("/create") || ctxURI.contains("/delete")) {
                    if (user == null || user.getRoleId() != this.role.getId()) {
                        resp.sendRedirect(String.format("%s/login/", req.getContextPath()));
                        return;
                    }
                } else if (ctxURI.contains("/update")) {
                    if (user == null || (user.getId() != Integer.parseInt(req.getParameter("id")) && user.getRoleId() != this.role.getId())) {
                        resp.sendRedirect(String.format("%s/login/", req.getContextPath()));
                        return;
                    }
                }
            }
            chain.doFilter(request, response);
        } catch (NoSuchAlgorithmException | ParseException | SQLException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
	 * Действия при уничтожении фильтра.
	 */
    @Override
    public void destroy() {
    }
}