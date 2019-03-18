package ru.job4j.htmlcss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
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
 * Класс Delete реализует функционал удаления пользователя.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-18
 * @since 2017-11-10
 */
public class Delete extends HttpServlet {
    /**
     * Кодировка окружения.
     */
    private String enc = "UTF-8";
    /**
     * Карта фильтров.
     */
    private HashMap<String, Filter> filters;
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * UserService.
     */
    private UserService us;
    /**
     * Инициализатор.
     *
     * @throws javax.servlet.ServletException исключение сервлета.
     */
    @Override
    public void init() throws ServletException {
        try {
            // /var/lib/tomcat8/webapps/ch8_html_css-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch8_html_css-1.0\WEB-INF\classes
            String path = new File(Delete.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            XmlConfigurationFactory xcf = new XmlConfigurationFactory();
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch8_html_css_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Delete");
            this.us = new UserService();
            this.filters = new HashMap<>();
            this.filters.put("id", new Filter("id", new String[]{"isExists", "isFilled", "isDecimal"}));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/ch8_html_css-1.0/delete/?id=100500
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding(this.enc);
            req.setAttribute("encoding", this.enc);
            String id = req.getParameter("id");
            Validation va = new Validation(this.logger, this.enc);
            va.validate("id", id, this.filters.get("id").getFilters());
            String message = "";
            User user = null;
            if (va.hasErrors()) {
                HashMap<String, String> result = va.getResult();
                message = String.format("Ошибка при попытке получить пользователя с id: %s<br />Текст ошибки: %s", id,  result.get("id"));
            } else {
                user = this.us.getUserById(Integer.parseInt(req.getParameter("id")));
                if (user == null) {
                    message = String.format("Ошибка при попытке получить пользователя с id: %s<br />Нет такого пользователя.", id);
                }
            }
            req.setAttribute("action", String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
            req.setAttribute("refBack", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("user", user);
            req.setAttribute("message", message);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/deleteGet.jsp").include(req, resp);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch8_html_css-1.0/delete/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding(this.enc);
            req.setAttribute("encoding", this.enc);
            String id = req.getParameter("id");
            Validation va = new Validation(this.logger, this.enc);
            va.validate("id", id, this.filters.get("id").getFilters());
            req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            String message = String.format("Пользователь id:%s удалён.", id);
            if (va.hasErrors()) {
                HashMap<String, String> result = va.getResult();
                message = String.format("Ошибка при удалении пользователя id:%s. %s", id, result.get("id"));
            } else {
                if (!this.us.deleteUser(Integer.parseInt(id))) {
                    message = String.format("Ошибка при удалении пользователя id:%s.", id);
                }
            }
            req.setAttribute("message", message);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/deletePost.jsp").include(req, resp);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
}