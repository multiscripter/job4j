package ru.job4j.control.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.RoleDAO;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
/**
 * Класс AuthFilter реализует фильтр авторизации пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-23
 * @since 2018-01-12
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
     * Инициализатор.
     * @param filterConfig конфигурация фильтра
     * @throws javax.servlet.ServletException исключение сервлета.
    */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            this.logger = LogManager.getLogger(this.getClass().getName());
			// /var/lib/tomcat8/webapps/ch9_control-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch9_control-1.0\WEB-INF\classes
            String path = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch9_control_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.role = (new RoleDAO()).getRoleByName("administrator");
		} catch (IOException | SQLException | URISyntaxException ex) {
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
            if (ctxURI.contains("/create") || ctxURI.contains("/delete")) {
                if (user == null || user.getRoleId() != this.role.getId()) {
                    resp.sendRedirect(String.format("%s/login/", req.getContextPath()));
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
	 * Вызывается при уничтожении сервлета.
	 */
    @Override
    public void destroy() {
        try {
            DBDriver.getInstance().close();
        } catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
    }
}