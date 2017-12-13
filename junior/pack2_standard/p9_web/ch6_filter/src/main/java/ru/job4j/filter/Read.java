package ru.job4j.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс Read реализует функционал чтения пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 6
 * @since 2017-11-08
 */
public class Read extends HttpServlet {
    /**
     * Роль администратора.
     */
    private Role adminRole;
	/**
     * Логгер.
     */
    private Logger logger;
    /**
     * Путь до файла.
     */
    private String path;
    /**
     * RoleService.
     */
    private RoleService rls;
    /**
     * UserService.
     */
	private UserService us;
    /**
	 * Инициализатор.
	 */
	@Override
    public void init() throws ServletException {
    	try {
			// /var/lib/tomcat8/webapps/ch6_filter-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch6_filter-1.0\WEB-INF\classes
			this.path = new File(Read.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch6_filter_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Read");
            this.rls = new RoleService();
            this.us = new UserService();
            this.adminRole = this.rls.getRoleByName("administrator");
		} catch (URISyntaxException | IOException | SQLException ex) {
			this.logger.error("ERROR", ex);
		}
    }
	/**
	 * Обрабатывает GET-запросы.
	 * http://bot.net:8080/ch6_filter-1.0/
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
			req.setAttribute("encoding", enc);
			LinkedList<User> users = this.us.getUsers("id", false);
			req.setAttribute("users", users);
            req.setAttribute("adminRole", this.adminRole);
            req.setAttribute("refUpdate", String.format("%s://%s:%s%s/update/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refDelete", String.format("%s://%s:%s%s/delete/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refCreate", String.format("%s://%s:%s%s/create/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/readGet.jsp").include(req, resp);
			// или
			//this.getServletContext().getRequestDispatcher("/WEB-INF/views/readGet.jsp").forward(req, resp);
		} catch (SQLException | ParseException | NoSuchAlgorithmException ex) {
			this.logger.error("ERROR", ex);
		}
	}
}
