package ru.job4j.filter;

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
/**
 * Класс AuthFilter реализует функционал авторизации пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-12
 */
public class AuthFilter implements Filter {
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Путь до файла.
     */
    private String path;
    /**
     * Роль администратора.
     */
    private Role role;
    /**
	 * Инициализатор.
	 */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
			// /var/lib/tomcat8/webapps/ch6_filter-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch6_filter-1.0\WEB-INF\classes
			this.path = new File(Login.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch6_filter_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Login");
            this.role = (new RoleService()).getRoleByName("administrator");
		} catch (URISyntaxException | IOException | SQLException ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Производит фильтрацию.
	 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String ctxURI = req.getRequestURI();
        HttpSession sess = req.getSession(false);
        User user = null;
        if (sess != null && sess.getAttribute("auth") != null) {
            user = (User) sess.getAttribute("auth");
        }
        if (ctxURI.contains("/login")) {
            if (sess != null && req.getParameter("auth") != null && req.getParameter("auth").equals("logout")) {
                sess.setAttribute("auth", null);
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
        chain.doFilter(request, response);
    }
    /**
	 * Действия при уничтожении фильтра.
	 */
    @Override
    public void destroy() {
    }
}