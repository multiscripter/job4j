package ru.job4j.mvcjstl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
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
 * Класс Read реализует реализует функционал чтения пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 5
 * @since 2017-11-08
 */
public class Read extends HttpServlet {
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
			// /var/lib/tomcat8/webapps/ch5_mvc_jstl-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch5_mvc_jstl-1.0\WEB-INF\classes
			this.path = new File(Read.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch5_mvc_jstl_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Read");
            this.us = new UserService();
		} catch (URISyntaxException | IOException ex) {
			this.logger.error("ERROR", ex);
		}
    }
	/**
	 * Обрабатывает GET-запросы.
	 * http://bot.net:8080/ch5_mvc_jstl-1.0/
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
            req.setAttribute("refUpdate", String.format("%s://%s:%s%s/update/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refDelete", String.format("%s://%s:%s%s/delete/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refCreate", String.format("%s://%s:%s%s/create/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/readGet.jsp").include(req, resp);
			// или
			//this.getServletContext().getRequestDispatcher("/WEB-INF/views/readGet.jsp").forward(req, resp);
		} catch (SQLException | ParseException ex) {
			this.logger.error("ERROR", ex);
		}
	}
}
