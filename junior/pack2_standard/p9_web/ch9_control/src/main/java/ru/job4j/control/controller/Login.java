package ru.job4j.control.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
//import javax.servlet.http.Cookie;
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
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.User;
/**
 * Класс Login реализует контроллер входа пользователя.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2018-01-12
 */
public class Login extends HttpServlet {
    /**
     * Логгер.
     */
    private Logger logger;
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
            this.logger = LogManager.getLogger("Login");
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
            this.us = new UserDAO();
        } catch (URISyntaxException | IOException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы. http://bot.net:8080/ch9_control-1.0/login/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String enc = Charset.defaultCharset().toString();
        resp.setCharacterEncoding(enc);
        req.setAttribute("encoding", enc);
        req.setAttribute("action", String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
        req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
        req.setAttribute("refLogout", String.format("%s://%s:%s%s%s?auth=logout", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginGet.jsp").include(req, resp);
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch9_control-1.0/login/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            String enc = Charset.defaultCharset().toString();
            resp.setCharacterEncoding(enc);
            req.setAttribute("encoding", enc);
            this.us.setEncoding(enc);
            String login = req.getParameter("login");
            String pass = req.getParameter("pass");
            login = new String(login.getBytes("ISO-8859-1"), enc);
            pass = new String(pass.getBytes("ISO-8859-1"), enc);
            User user = this.us.getUserByLogPass(login, pass);
            String message;
            if (user != null) {
                req.getSession().setAttribute("auth", user);
                req.setAttribute("title", "Захади, дарагой!");
                message = String.format("С возвращением, %s!", user.getLogin());
                //Cookie cookie = new Cookie("auth", Integer.toString(user.getId()));
                //cookie.setPath("/");
                //cookie.setMaxAge(60 * 60);
                //resp.addCookie(cookie);
            } else {
                req.setAttribute("title", "Ты кто такой, дарагой?");
                message = "Ошибка. Пользователя с таким логином и паролем нет.";
            }
            req.setAttribute("message", message);
            req.setAttribute("refBack", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginPost.jsp").include(req, resp);
        } catch (NoSuchAlgorithmException | ParseException | SQLException ex) {
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