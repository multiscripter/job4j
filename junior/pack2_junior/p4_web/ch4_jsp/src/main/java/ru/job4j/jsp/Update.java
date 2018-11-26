package ru.job4j.jsp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.GregorianCalendar;
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
 * Класс Update реализует функционал обновления пользователя.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-11-09
 */
public class Update extends HttpServlet {
	/**
     * Логгер.
     */
    private Logger logger;
    /**
     * Путь до файла.
     */
    private String path;
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
			// /var/lib/tomcat8/webapps/ch3_ui-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch3_ui-1.0\WEB-INF\classes
			this.path = new File(Update.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch4_jsp_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Update");
			this.us = new UserService();
		} catch (URISyntaxException | IOException ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Обрабатывает GET-запросы.
	 * http://bot.net:8080/ch4_jsp-1.0/update/?id=100500
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
            req.setAttribute("encoding", enc);
            User user = null;
            if (req.getParameter("id") != null) {
                user = this.us.getUser(Integer.parseInt(req.getParameter("id")));
            }
            req.setAttribute("user", user);
            this.getServletContext().getRequestDispatcher("/jsp/updateGet.jsp").include(req, resp);
            //this.getServletContext().getRequestDispatcher("/jsp/updateGet.jsp").forward(req, resp);
		} catch (SQLException | ParseException ex) {
			this.logger.error("ERROR", ex);
		}
	}
    /**
	 * Обрабатывает POST-запросы.
     * http://bot.net:8080/ch4_jsp-1.0/update/
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
            resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
			String name = new String(req.getParameter("name").getBytes("ISO-8859-1"), enc);
			String login = new String(req.getParameter("login").getBytes("ISO-8859-1"), enc);
			String email = req.getParameter("email");
			User user = new User(Integer.parseInt(req.getParameter("id")), name, login, email, new GregorianCalendar());
            req.setAttribute("editUser", this.us.editUser(user));
			req.setAttribute("user", user);
            this.getServletContext().getRequestDispatcher("/jsp/updatePost.jsp").include(req, resp);
			//this.getServletContext().getRequestDispatcher("/jsp/updateGet.jsp").forward(req, resp);
        } catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
	}
}