package ru.job4j.htmlcss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
 * Класс Create реализует функционал создания пользователя.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-09
 * @since 2017-11-09
 */
public class Create extends HttpServlet {
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
            String path = new File(Create.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            XmlConfigurationFactory xcf = new XmlConfigurationFactory();
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch8_html_css_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Create");
            this.cits = new CityService();
            this.cs = new CountryService();
            this.rls = new RoleService();
            this.us = new UserService();
            this.errmsgs = new PropertyLoader("errmsg.properties").getPropertiesList();
            this.filters = new HashMap<>();
            this.filters.put("name", new Filter("name", new String[]{"isFilled", "isName"}));
            this.filters.put("login", new Filter("login", new String[]{"isFilled"}));
            this.filters.put("email", new Filter("email", new String[]{"isFilled", "isEmail"}));
            this.filters.put("pass", new Filter("pass", new String[]{"isFilled"}));
            this.filters.put("role", new Filter("role", new String[]{"isFilled", "isDecimal"}));
            this.filters.put("country", new Filter("country", new String[]{"isFilled", "isDecimal"}));
            this.filters.put("city", new Filter("city", new String[]{"isFilled", "isDecimal"}));
        } catch (IllegalAccessException | InstantiationException | URISyntaxException | ClassNotFoundException | SQLException | IOException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы. http://bot.net:8080/ch8_html_css-1.0/create/
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
            req.setAttribute("cities", this.cits.getCities());
            req.setAttribute("countries", this.cs.getCountries());
            req.setAttribute("roles", this.rls.getRoles());
            req.setAttribute("action", String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
            req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/createGet.jsp").include(req, resp);
        } catch (SQLException | NullPointerException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch8_html_css-1.0/create/
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
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            String pass = req.getParameter("pass");
            String role = req.getParameter("role");
            String country = req.getParameter("country");
            String city = req.getParameter("city");
            Validation va = new Validation(this.logger, enc);
            va.validate("name", name, this.filters.get("name").getFilters());
            va.validate("login", login, this.filters.get("login").getFilters());
            va.validate("email", email, this.filters.get("email").getFilters());
            va.validate("pass", pass, this.filters.get("pass").getFilters());
            va.validate("role", role, this.filters.get("role").getFilters());
            va.validate("country", country, this.filters.get("country").getFilters());
            va.validate("city", city, this.filters.get("city").getFilters());
            if (va.hasErrors()) {
                req.setAttribute("name", name);
                req.setAttribute("login", login);
                req.setAttribute("email", email);
                req.setAttribute("role", role);
                req.setAttribute("country", country);
                req.setAttribute("city", city);
                req.setAttribute("cities", this.cits.getCities());
                req.setAttribute("countries", this.cs.getCountries());
                req.setAttribute("roles", this.rls.getRoles());
                req.setAttribute("errors", va.getResult());
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/createGet.jsp").include(req, resp);
            } else {
                name = new String(name.getBytes("ISO-8859-1"), enc);
                login = new String(login.getBytes("ISO-8859-1"), enc);
                email = new String(email.getBytes("ISO-8859-1"), enc);
                pass = new String(pass.getBytes("ISO-8859-1"), enc);
                User user = new User();
                user.setName(name);
                user.setLogin(login);
                user.setEmail(email);
                user.setDate(new GregorianCalendar());
                user.setPass(pass);
                user.setRole(this.rls.getRoleById(Integer.parseInt(role)));
                user.setCountry(this.cs.getCountryById(Integer.parseInt(country)));
                user.setCity(this.cits.getCityById(Integer.parseInt(city)));
                String message = String.format("Ошибка при добавлении пользователя %s.", user.getName());
                try {
                    if (this.us.addUser(user)) {
                        message = String.format("Пользователь %s добавлен. id: %s", user.getName(), user.getId());
                    }
                } catch (SQLException ex) {
                    this.logger.error("ERROR", ex);
                }
                req.setAttribute("message", message);
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/createPost.jsp").include(req, resp);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchAlgorithmException | NoSuchMethodException | ParseException | SQLException | NullPointerException ex) {
            this.logger.error("ERROR", ex);
        }
    }
}