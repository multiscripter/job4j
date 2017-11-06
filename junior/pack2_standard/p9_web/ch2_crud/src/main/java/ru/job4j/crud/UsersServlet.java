package ru.job4j.crud;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Scanner;
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
 * Класс UsersServlet реализует сущность Сервлет Пользователь.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-05
 */
public class UsersServlet extends HttpServlet {
	/**
     * Драйвер бд.
     */
	private UsersDB db;
	/**
     * Логгер.
     */
    private Logger logger;
    /**
     * Путь до файла.
     */
    private String path;
	/**
	 * Конструктор.
	 */
	public UsersServlet() {
		try {
			Class.forName("org.postgresql.Driver").newInstance(); //load driver
			// /var/lib/tomcat8/webapps/ch2_crud-1.0/WEB-INF/classes
			this.path = new File(UsersServlet.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("JobsParserTestContext"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("UsersServlet");
			this.db = new UsersDB();
			this.db.loadProperties("junior.pack2.p9.ch2.task1.properties");
			this.db.setDbDriver(new PgSQLJDBCDriver());
			this.db.executeSql("junior.pack2.p9.ch2.task1.sql");
		} catch (URISyntaxException | IOException | SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			this.logger.error("ERROR", ex);
		}
	}
	/**
	 * Обрабатывает DELETE-запросы.
	 * curl -X DELETE http://bot.net:8080/ch2_crud-1.0/user/delete/5
	 */
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			int id = this.getId(req);
			if (this.db.deleteUser(id)) {
				writer.append(String.format("Пользователь с id:%d удалён.<br />\n", id));
			} else {
				writer.append(String.format("Ошибка при удалении пользователя с id: %d.<br />\n", id));
			}
			writer.flush();
		} catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
	}
	/**
	 * Обрабатывает GET-запросы.
	 * curl http://bot.net:8080/ch2_crud-1.0/
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			LinkedList<User> users = this.db.getUsers("id", false);
			for (User user : users) {
				writer.append(user + "<br />");
			}
			writer.flush();
		} catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
	}
	/**
	 * Обрабатывает POST-запросы.
	 * curl -d "name=%D0%A1%D0%BE%D0%B1%D1%87%D0%B0%D1%87%D0%BA%D0%B0&login=forsegirl&email=horse@mail.ru" -H "Content-Type: application/x-www-form-urlencoded; charset=utf-8" -X POST http://bot.net:8080/ch2_crud-1.0/
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			String name = req.getParameter("name");
			String login = req.getParameter("login");
			String email = req.getParameter("email");
			GregorianCalendar date = new GregorianCalendar();
			User user = new User(0, name, login, email, date);
			if (this.db.addUser(user)) {
				writer.append(String.format("Пользователь %s добавлен. ID: %s<br />\n", name, user.getId()));
			} else {
				writer.append(String.format("Ошибка при добавлении пользователя %s.<br />\n", name));
			}
			writer.flush();
		} catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
	}
	/**
	 * Обрабатывает PUT-запросы.
	 * Отредактировать запись с id = 4. Данные считать из файла data.txt
	 * curl -T /home/cyberbotx/java/JavaCore/src/job4j/junior/pack2_standard/p9_web/ch2_crud/src/main/resources/data.txt -X PUT http://bot.net:8080/ch2_crud-1.0/user/edit/4
	 */
	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			int id = this.getId(req);
			Scanner scanner = new Scanner(req.getInputStream(), "UTF-8");
			String body = scanner.next();
			if (this.db.editUser(id, body)) {
				writer.append(String.format("Пользователь с id:%d отредактирован.<br />\n", id));
			} else {
				writer.append(String.format("Ошибка при редактировании пользователя %d. Тело: %s<br />\n", id, body));
			}
			writer.flush();
		} catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
	}
	/**
	 * Получает id.
	 * @param req запрос.
	 * @return идентификатор.
	 */
	private int getId(HttpServletRequest req) {
		this.logger.error(req.getPathInfo());
		String[] path = req.getPathInfo().split("/");
		return Integer.parseInt(path[path.length - 1]);
	}
}