package ru.job4j.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @version 2
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
			this.path = new File(Update.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("JobsParserTestContext"), source);
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
	 * curl http://bot.net:8080/ch3_ui-1.0/update/?id=100500
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			StringBuilder sb = new StringBuilder();
			User user = this.us.getUser(Integer.parseInt(req.getParameter("id")));
			sb.append("<!DOCTYPE html>\n");
			sb.append("<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>\n");
			sb.append("<head>\n");
			sb.append("    <meta http-equiv='Content-Type' content='text/html; charset=" + enc + "' />\n");
			sb.append("    <title>Редактирование пользователя</title>\n");
			sb.append("</head>\n");
			sb.append("<body>\n");
			sb.append("    <h1>Редактирование пользователя</h1>\n");
			if (user != null) {
                sb.append("    <form method='POST' action='");
                sb.append(String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
                sb.append("'>\n");
                sb.append("    <table>\n");
                sb.append("        <tr>\n");
                sb.append("            <td>\n");
                sb.append("                <label>Иия:<br />\n");
                sb.append("                    <input type='text' name='name' required='required' value='" + user.getName() + "' />\n");
                sb.append("                </label>\n");
                sb.append("            </td>\n");
                sb.append("        </tr>\n");
                sb.append("        <tr>\n");
                sb.append("            <td>\n");
                sb.append("                <label>Логин:<br />\n");
                sb.append("                    <input type='text' name='login' required='required' value='" + user.getLogin() + "' />\n");
                sb.append("                </label>\n");
                sb.append("            </td>\n");
                sb.append("        </tr>\n");
                sb.append("        <tr>\n");
                sb.append("            <td>\n");
                sb.append("                <label>Емэил:<br />\n");
                sb.append("                    <input type='email' name='email' required='required' value='" + user.getEmail() + "' />\n");
                sb.append("                </label>\n");
                sb.append("            </td>\n");
                sb.append("        </tr>\n");
                sb.append("        <tr>\n");
                sb.append("            <td>\n");
                sb.append("                <input type='submit' value='Отредактировать'>\n");
                sb.append("            </td>\n");
                sb.append("        </tr>\n");
                sb.append("    </table>\n");
                sb.append("        <input type='hidden' name='id' value='" + user.getId() + "' />\n");
                sb.append("    <form>\n");
			} else {
				sb.append("    <p>Нет такого пользователя</p>\n");
			}
            String ref = String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath());
			sb.append(ref);
			sb.append("</body>\n");
			writer.append(sb.toString());
			writer.flush();
		} catch (SQLException | ParseException ex) {
			this.logger.error("ERROR", ex);
		}
	}
    /**
	 * Обрабатывает POST-запросы.
     * http://bot.net:8080/ch3_ui-1.0/update/
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
			User user = new User(Integer.parseInt(req.getParameter("id")), name, login, email, new GregorianCalendar());
			StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
			sb.append("<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>\n");
			sb.append("<head>\n");
			sb.append("    <meta http-equiv='Content-Type' content='text/html; charset=" + enc + "' />\n");
			sb.append("    <title>Редактирование пользователя</title>\n");
			sb.append("</head>\n");
			sb.append("<body>\n");
			sb.append("    <h1>Редактирование пользователя</h1>\n");
            if (this.us.editUser(user)) {
				sb.append(String.format("<p>Пользователь id:%d отредактирован.</p><br />\n", user.getId()));
			} else {
				sb.append(String.format("<p>Ошибка при редактировании пользователя id:%d.</p><br />\n", user.getId()));
			}
            String ref = String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath());
			sb.append(ref);
			sb.append("</body>\n");
			writer.append(sb.toString());
			writer.flush();
        } catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
	}
}