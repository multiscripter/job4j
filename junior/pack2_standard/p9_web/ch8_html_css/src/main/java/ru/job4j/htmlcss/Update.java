package ru.job4j.htmlcss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
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
 * Класс Update реализует функционал обновления пользователя.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 6
 * @since 2017-11-09
 */
public class Update extends HttpServlet {
    /**
     * Роль администратора.
     */
    private Role adminRole;
    /**
     * CityService.
     */
    private CityService cits;
    /**
     * CountryService.
     */
    private CountryService cs;
    /**
     * Список сообщений об ошибках.
     */
    private LinkedList<Property> errmsgs;
    /**
     * Карта фильтров.
     */
    private HashMap<String, Filter> filters;
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
     * @throws javax.servlet.ServletException
     */
    @Override
    public void init() throws ServletException {
        try {
            // /var/lib/tomcat8/webapps/ch8_html_css-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch8_html_css-1.0\WEB-INF\classes
            this.path = new File(Update.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            this.path = this.path.replaceFirst("^/(.:/)", "$1");
            XmlConfigurationFactory xcf = new XmlConfigurationFactory();
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch8_html_css_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Update");
            this.cits = new CityService();
            this.cs = new CountryService();
            this.rls = new RoleService();
            this.us = new UserService();
            this.errmsgs = new PropertyLoader("junior.pack2.p9.ch8.task1.errmsg.properties").getPropertiesList();
            this.filters = new HashMap();
            this.filters.put("id", new Filter("id", new String[]{"isFilled", "isDecimal"}));
            this.filters.put("name", new Filter("name", new String[]{"isFilled", "isName"}));
            this.filters.put("login", new Filter("login", new String[]{"isFilled"}));
            this.filters.put("email", new Filter("email", new String[]{"isFilled", "isEmail"}));
            this.filters.put("pass", new Filter("pass", new String[]{"isFilled"}));
            this.filters.put("role", new Filter("role", new String[]{"isFilled", "isDecimal"}));
            this.filters.put("country", new Filter("country", new String[]{"isFilled", "isDecimal"}));
            this.filters.put("city", new Filter("city", new String[]{"isFilled", "isDecimal"}));
            this.adminRole = this.rls.getRoleByName("administrator");
        } catch (URISyntaxException | IOException | SQLException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/ch8_html_css-1.0/update/?id=100500
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
            req.setAttribute("errmsgs", this.errmsgs);
            req.setAttribute("filters", this.filters.values());
            String id = req.getParameter("id");
            Validation va = new Validation(this.logger, enc);
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
                } else {
                    req.setAttribute("adminRole", this.adminRole);
                    if (((User) req.getSession(false).getAttribute("auth")).getRoleId() == this.adminRole.getId()) {
                        req.setAttribute("roles", this.rls.getRoles());
                    }
                    req.setAttribute("cities", this.cits.getCities());
                    req.setAttribute("countries", this.cs.getCountries());
                }
            }
            req.setAttribute("action", String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            String referer = req.getHeader("referer");
            String refName = referer != null && referer.contains("update") ? "refHome" : "refBack";
            req.setAttribute(refName, String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("user", user);
            req.setAttribute("message", message);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/updateGet.jsp").include(req, resp);
            // или
            //this.getServletContext().getRequestDispatcher("/WEB-INF/views/updateGet.jsp").forward(req, resp);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchAlgorithmException | NoSuchMethodException | SQLException | ParseException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch8_html_css-1.0/update/
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
            req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            String pass = req.getParameter("pass");
            String role = req.getParameter("role");
            String country = req.getParameter("country");
            String city = req.getParameter("city");
            Validation va = new Validation(this.logger, enc);
            va.validate("id", id, this.filters.get("id").getFilters());
            va.validate("name", name, this.filters.get("name").getFilters());
            va.validate("login", login, this.filters.get("login").getFilters());
            va.validate("email", email, this.filters.get("email").getFilters());
            va.validate("pass", pass, this.filters.get("pass").getFilters());
            va.validate("role", role, this.filters.get("role").getFilters());
            va.validate("country", country, this.filters.get("country").getFilters());
            va.validate("city", city, this.filters.get("city").getFilters());
            User user = new User();
            if (!va.hasError("id")) {
                user.setId(Integer.parseInt(id));
            }
            if (!va.hasError("name")) {
                user.setName(new String(name.getBytes("ISO-8859-1"), enc));
            }
            if (!va.hasError("login")) {
                user.setLogin(new String(login.getBytes("ISO-8859-1"), enc));
            }
            if (!va.hasError("email")) {
                user.setEmail(new String(email.getBytes("ISO-8859-1"), enc));
            }
            if (!va.hasError("pass")) {
                user.setPass(new String(pass.getBytes("ISO-8859-1"), enc));
            }
            if (!va.hasError("role")) {
                user.setRole(this.rls.getRoleById(Integer.parseInt(role)));
            }
            if (!va.hasError("country")) {
                user.setCountry(this.cs.getCountryById(Integer.parseInt(country)));
            }
            if (!va.hasError("city")) {
                user.setCity(this.cits.getCityById(Integer.parseInt(city)));
            }
            req.setAttribute("adminRole", this.adminRole);
            String message = String.format("Ошибка при редактировании пользователя id:%d.", user.getId());
            if (va.hasErrors()) {
                if (va.hasError("id")) {
                    message = String.format("Ошибка при попытке получить пользователя с id: %s<br />Нет такого пользователя.", id);
                    req.setAttribute("message", message);
                } else {
                    req.setAttribute("cities", this.cits.getCities());
                    req.setAttribute("countries", this.cs.getCountries());
                    req.setAttribute("roles", this.rls.getRoles());
                    req.setAttribute("errors", va.getResult());
                    req.setAttribute("user", user);
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/updateGet.jsp").include(req, resp);
            } else {
                user.setDate(new GregorianCalendar());
                User auth = (User) req.getSession(false).getAttribute("auth");
                int roleId = auth.getRoleId();
                if (roleId == this.adminRole.getId()) {
                    roleId = Integer.parseInt(role);
                }
                try {
                    if (this.us.editUser(user)) {
                        message = String.format("Пользователь id:%d отредактирован.", user.getId());
                    }
                } catch (SQLException ex) {
                    this.logger.error("ERROR", ex);
                }
                req.setAttribute("message", message);
                req.setAttribute("refBack", String.format("%s://%s:%s%s/update/?id=%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), id));
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/updatePost.jsp").include(req, resp);
                // или
                //this.getServletContext().getRequestDispatcher("/WEB-INF/views/updatePost.jsp").forward(req, resp);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | SecurityException | SQLException | NoSuchAlgorithmException | NoSuchMethodException | UnsupportedEncodingException ex) {
            this.logger.error("ERROR", ex);
        }
    }
}