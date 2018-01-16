package ru.job4j.control.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.User;
/**
 * Класс Delete реализует контроллер Удаление пользователя.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2018-01-12
 */
public class Delete extends HttpServlet {
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
            this.logger = LogManager.getLogger("Delete");
            // /var/lib/tomcat8/webapps/ch9_control-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch9_control-1.0\WEB-INF\classes
            String path = new File(Delete.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
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
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/ch9_control-1.0/delete/?id=100500
     * @param req запрос.
     * @param resp ответ.
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
            String message = "";
            User user = this.us.getUserById(Integer.parseInt(req.getParameter("id")));
            if (user == null) {
                message = String.format("Ошибка при попытке получить пользователя с id: %s<br />Нет такого пользователя.", req.getParameter("id"));
            }
            req.setAttribute("action", String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
            req.setAttribute("refBack", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("user", user);
            req.setAttribute("message", message);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/deleteGet.jsp").include(req, resp);
        } catch (SecurityException | SQLException | ParseException | NumberFormatException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch9_control-1.0/delete/
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
            String id = req.getParameter("id");
            req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            String message = String.format("Пользователь id:%s удалён.", id);
            if (!this.us.deleteUser(Integer.parseInt(id))) {
                message = String.format("Ошибка при удалении пользователя id:%s.", id);
            }
            req.setAttribute("message", message);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/deletePost.jsp").include(req, resp);
        } catch (SQLException ex) {
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