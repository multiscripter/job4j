package ru.job4j.control.controller;

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
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.RoleDAO;
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
/**
 * Класс Read реализует контроллер Чтение пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2018-01-12
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
     * RoleDAO.
     */
    private RoleDAO rls;
    /**
     * UserDAO.
     */
	private UserDAO us;
    /**
	 * Инициализатор.
     * @throws javax.servlet.ServletException исключение сервлета.
	 */
	@Override
    public void init() throws ServletException {
    	try {
            this.logger = LogManager.getLogger("Read");
            // /var/lib/tomcat8/webapps/ch9_control-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch9_control-1.0\WEB-INF\classes
            String path = new File(Read.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			path = path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch9_control_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.rls = new RoleDAO();
            this.us = new UserDAO();
            this.adminRole = this.rls.getRoleByName("administrator");
        } catch (URISyntaxException | IOException | SQLException ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Обрабатывает GET-запросы.
	 * http://bot.net:8080/ch9_control-1.0/
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
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
        } catch (SQLException | ParseException | NoSuchAlgorithmException ex) {
			this.logger.error("ERROR", ex);
		}
	}
    /**
	 * Вызывается при уничтожении сервлета.
	 */
    @Override
    public void destroy() {
        try {
            DBDriver.getInstance("junior.pack2.p9.ch9.task1").close();
        } catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
    }
}