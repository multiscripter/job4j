package ru.job4j.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.SQLException;
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
 * Класс Create реализует функционал создания пользователя.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-09
 */
public class Create extends HttpServlet {
	/**
     * Драйвер бд.
     */
	private UserStore db;
	/**
     * Логгер.
     */
    private Logger logger;
    /**
     * Путь до файла.
     */
    private String path;
    /**
	 * Инициализатор.
	 */
	@Override
    public void init() throws ServletException {
    	try {
			Class.forName("org.postgresql.Driver").newInstance(); //load driver
			// /var/lib/tomcat8/webapps/ch3_ui-1.0/WEB-INF/classes
			this.path = new File(Create.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("JobsParserTestContext"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Create");
			this.db = new UserStore();
			this.db.loadProperties("junior.pack2.p9.ch3.task1.properties");
			this.db.setDbDriver();
			this.db.executeSql("junior.pack2.p9.ch3.task1.sql");
		} catch (URISyntaxException | IOException | SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Обрабатывает GET-запросы.
	 * curl http://bot.net:8080/ch3_ui-1.0/create/
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String enc = Charset.defaultCharset().toString();
		resp.setCharacterEncoding(enc);
		PrintWriter writer = new PrintWriter(resp.getOutputStream());
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>\n");
		sb.append("<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>\n");
		sb.append("<head>\n");
		sb.append("    <meta http-equiv='Content-Type' content='text/html; charset=" + enc + "' />\n");
		sb.append("    <title>Создание пользователя</title>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("    <h1>Создание пользователя</h1>\n");
		sb.append("    <form method='POST' action='");
        sb.append(String.format("%s://%s:%s%s/create/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
        sb.append("'>\n");
        sb.append("    <table>\n");
        sb.append("        <tr>\n");
        sb.append("            <td>\n");
        sb.append("                <label>Иия:<br />\n");
        sb.append("                    <input type='text' name='name' required='required'>\n");
        sb.append("                </label>\n");
        sb.append("            </td>\n");
        sb.append("        </tr>\n");
        sb.append("        <tr>\n");
        sb.append("            <td>\n");
        sb.append("                <label>Логин:<br />\n");
        sb.append("                    <input type='text' name='login' required='required'>\n");
        sb.append("                </label>\n");
        sb.append("            </td>\n");
        sb.append("        </tr>\n");
        sb.append("        <tr>\n");
        sb.append("            <td>\n");
        sb.append("                <label>Емэил:<br />\n");
        sb.append("                    <input type='email' name='email' required='required'>\n");
        sb.append("                </label>\n");
        sb.append("            </td>\n");
        sb.append("        </tr>\n");
        sb.append("        <tr>\n");
        sb.append("            <td>\n");
        sb.append("                <input type='submit' value='Создать'>\n");
        sb.append("            </td>\n");
        sb.append("        </tr>\n");
        sb.append("    </table>\n");
        sb.append("    <form>\n");
        sb.append(String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
		sb.append("</body>\n");
		writer.append(sb.toString());
		writer.flush();
	}
    /**
	 * Обрабатывает POST-запросы.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			String name = new String(req.getParameter("name").getBytes("ISO-8859-1"), enc);
			String login = new String(req.getParameter("login").getBytes("ISO-8859-1"), enc);
			String email = req.getParameter("email");
			User user = new User(0, name, login, email, new GregorianCalendar());
			StringBuilder sb = new StringBuilder();
			sb.append("<!DOCTYPE html>\n");
			sb.append("<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>\n");
			sb.append("<head>\n");
			sb.append("    <meta http-equiv='Content-Type' content='text/html; charset=" + enc + "' />\n");
			sb.append("    <title>Создание пользователя</title>\n");
			sb.append("</head>\n");
			sb.append("<body>\n");
			sb.append("    <h1>Создание пользователя</h1>\n");
			if (this.db.addUser(user)) {
				sb.append(String.format("<p>Пользователь %s добавлен. ID: %s</p><br />\n", name, user.getId()));
			} else {
				sb.append(String.format("<p>Ошибка при добавлении пользователя %s.</p><br />\n", name));
			}
			sb.append(String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
			sb.append("</body>\n");
			writer.append(sb.toString());
			writer.flush();
		} catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
	}
}
