package ru.job4j.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
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
 * Класс Login реализует контроллер входа пользователя.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-11
 */
public class Login extends HttpServlet {
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
			// /var/lib/tomcat8/webapps/ch7_testing-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch7_testing-1.0\WEB-INF\classes
			this.path = new File(Login.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch7_testing_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Login");
            this.us = new UserService();
		} catch (URISyntaxException | IOException ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Обрабатывает GET-запросы.
	 * http://bot.net:8080/ch7_testing-1.0/login/
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String enc = Charset.defaultCharset().toString();
        resp.setCharacterEncoding(enc);
        req.setAttribute("encoding", enc);
        req.setAttribute("action", String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
        req.setAttribute("refLogout", String.format("%s://%s:%s%s%s?auth=logout", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
        req.setAttribute("refBack", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginGet.jsp").include(req, resp);
        // или
        //this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginGet.jsp").forward(req, resp);
	}
    /**
	 * Обрабатывает POST-запросы.
     * http://bot.net:8080/ch7_testing-1.0/login/
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
			req.setAttribute("encoding", enc);
            this.us.setEncoding(enc);
			String login = new String(req.getParameter("login").getBytes("ISO-8859-1"), enc);
			String pass = new String(req.getParameter("pass").getBytes("ISO-8859-1"), enc);
			User user = this.us.getUserByLogPass(login, pass);
            String message = "";
			if (user != null) {
                req.getSession().setAttribute("auth", user);
                req.setAttribute("title", String.format("Захади, дарагой %s!", user.getName()));
                message = String.format("С возвращением, %s", user.getName());
            } else {
                req.setAttribute("title", "Ты кто такой, дарагой?");
                message = String.format("Ошибка. Пользователя с таким логином и паролем нет.");
            }
            req.setAttribute("message", message);
            req.setAttribute("refBack", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refMain", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginPost.jsp").include(req, resp);
			// или
			//this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginPost.jsp").forward(req, resp);
		} catch (SQLException | ParseException | NoSuchAlgorithmException ex) {
			this.logger.error("ERROR", ex);
		}
	}
}