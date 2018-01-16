package ru.job4j.control.controller;

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
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.MusicTypeDAO;
import ru.job4j.control.persistence.RoleDAO;
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.Address;
import ru.job4j.control.service.User;
/**
 * Класс Create реализует контроллер Создание пользователя.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2018-01-12
 */
public class Create extends HttpServlet {
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * MusicTypeDAO.
     */
    private MusicTypeDAO mts;
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
            this.logger = LogManager.getLogger("Create");
            // /var/lib/tomcat8/webapps/ch9_control-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch9_control-1.0\WEB-INF\classes
            String path = new File(Create.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            XmlConfigurationFactory xcf = new XmlConfigurationFactory();
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch9_control_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.mts = new MusicTypeDAO();
            this.rls = new RoleDAO();
            this.us = new UserDAO();
        } catch (URISyntaxException | IOException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы. http://bot.net:8080/ch9_control-1.0/create/
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
            req.setAttribute("mtypes", this.mts.getMusicTypes());
            req.setAttribute("roles", this.rls.getRoles());
            req.setAttribute("action", String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
            req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/createGet.jsp").include(req, resp);
        } catch (SQLException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch9_control-1.0/create/
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
            req.setAttribute("refBack", String.format("%s://%s:%s%s/create/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            String login = new String(req.getParameter("login").getBytes("ISO-8859-1"), enc);
            String pass = new String(req.getParameter("pass").getBytes("ISO-8859-1"), enc);
            String role = new String(req.getParameter("role").getBytes("ISO-8859-1"), enc);
            String country = new String(req.getParameter("country").getBytes("ISO-8859-1"), enc);
            String city = new String(req.getParameter("city").getBytes("ISO-8859-1"), enc);
            String addr = new String(req.getParameter("addr").getBytes("ISO-8859-1"), enc);
            User user = new User();
            user.setAddress(new Address(0, country, city, addr));
            user.setLogin(login);
            user.setPass(pass);
            user.setRole(this.rls.getRoleById(Integer.parseInt(role)));
            String[] mtypeIds = req.getParameterValues("mtypes");
            user.setMusicTypes(this.mts.getMusicTypesByIds(mtypeIds));
            String message = String.format("Ошибка при добавлении пользователя %s.", user.getLogin());
            try {
                if (this.us.addUser(user)) {
                    message = String.format("Пользователь %s добавлен. id: %s", user.getLogin(), user.getId());
                }
            } catch (SQLException ex) {
                this.logger.error("ERROR", ex);
            }
            req.setAttribute("message", message);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/createPost.jsp").include(req, resp);
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