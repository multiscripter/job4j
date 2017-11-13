package ru.job4j.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.LinkedList;
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
 * Класс Read реализует реализует функционал чтения пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-08
 */
public class Read extends HttpServlet {
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
			this.path = new File(Read.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("JobsParserTestContext"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Read");
			this.db = UserStore.getInstance();
			this.db.loadProperties("junior.pack2.p9.ch3.task1.properties");
			this.db.setDbDriver();
			this.db.executeSql("junior.pack2.p9.ch3.task1.sql");
		} catch (URISyntaxException | IOException | SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			this.logger.error("ERROR", ex);
		}
    }
	/**
	 * Обрабатывает GET-запросы.
	 * curl http://bot.net:8080/ch3_ui-1.0/
     * Получить кодировку в консоле Хрома: document.characterSet
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			StringBuilder sb = new StringBuilder();
			LinkedList<User> users = this.db.getUsers("id", false);
			sb.append("<!DOCTYPE html>\n");
			sb.append("<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>\n");
			sb.append("<head>\n");
			sb.append("    <meta http-equiv='Content-Type' content='text/html; charset=" + enc + "' />\n");
			sb.append("    <title>Хранилище пользователей</title>\n");
			sb.append("</head>\n");
			sb.append("<body>\n");
			sb.append("    <h1>");
			sb.append("Хранилище пользователей");
			sb.append("</h1>\n");
			if (users.size() > 0) {
				sb.append("    <table>\n");
				sb.append("        <tr>\n");
				sb.append("            <th>");
				sb.append("ИД");
				sb.append("</th>\n");
				sb.append("            <th>");
				sb.append("Имя");
				sb.append("</th>\n");
				sb.append("            <th>");
				sb.append("Логин");
				sb.append("</th>\n");
				sb.append("            <th>");
				sb.append("Емэил");
				sb.append("</th>\n");
				sb.append("            <th>");
				sb.append("Дата создания");
				sb.append("</th>\n");
				sb.append("            <th></th>\n");
                sb.append("            <th></th>\n");
				sb.append("        </tr>\n");
				for (User user : users) {
					sb.append("        <tr>\n");
					sb.append("            <td>");
					sb.append(user.getId());
					sb.append("</td>\n");
					sb.append("            <td>");
					sb.append(user.getName());
					sb.append("</td>\n");
					sb.append("            <td>");
					sb.append(user.getLogin());
					sb.append("</td>\n");
					sb.append("            <td>");
					sb.append(user.getEmail());
					sb.append("</td>\n");
					sb.append("            <td>");
					sb.append(user.getDateStr());
					sb.append("</td>\n");
					sb.append("            <td>");
					sb.append(String.format("<a href='%s://%s:%s%s/update/?id=%d'>редактировать</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), user.getId()));
					sb.append("</td>\n");
                    sb.append("            <td>");
					sb.append(String.format("<a href='%s://%s:%s%s/delete/?id=%d'>удалить</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), user.getId()));
					sb.append("</td>\n");
					sb.append("        </tr>\n");
				}
				sb.append("    </table>\n");
			} else {
				sb.append("    <p>Никого нет</p>\n");
			}
			sb.append("</body>\n");
			sb.append(String.format("<a href='%s://%s:%s%s/create/'>Добавить нового пользователя</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
			writer.append(sb.toString());
			writer.flush();
		} catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
	}
}
