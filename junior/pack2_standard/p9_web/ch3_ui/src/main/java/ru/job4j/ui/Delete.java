package ru.job4j.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.SQLException;
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
 * @version 1
 * @since 2017-11-10
 */
public class Delete extends HttpServlet {
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
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch3_ui-1.0\WEB-INF\classes
			this.path = new File(Delete.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			this.path = this.path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(this.path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("JobsParserTestContext"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Delete");
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
	 * curl http://bot.net:8080/ch3_ui-1.0/delete/?id=100500
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			StringBuilder sb = new StringBuilder();
			User user = this.db.getUser(Integer.parseInt(req.getParameter("id")));
			sb.append("<!DOCTYPE html>\n");
			sb.append("<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>\n");
			sb.append("<head>\n");
			sb.append("    <meta http-equiv='Content-Type' content='text/html; charset=" + enc + "' />\n");
			sb.append("    <title>Удаление пользователя</title>\n");
			sb.append("</head>\n");
			sb.append("<body>\n");
			sb.append("    <h1>Удаление пользователя</h1>\n");
            if (user != null) {
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
				sb.append("        </tr>\n");
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
                sb.append("        </tr>\n");
				sb.append("    </table>\n");
                sb.append("    <form method='POST' action='");
                sb.append(String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
                sb.append("'>\n");
                sb.append("        <table>\n");
                sb.append("            <tr>\n");
                sb.append("                <td>\n");
                sb.append("                    <input type='submit' value='Удалить'>\n");
                sb.append("                </td>\n");
                sb.append("            </tr>\n");
                sb.append("        </table>\n");
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
        } catch (SQLException ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Обрабатывает POST-запросы.
     * http://bot.net:8080/ch3_ui-1.0/delete/
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
            resp.setContentType("text/html");
			String enc = Charset.defaultCharset().toString();
			resp.setCharacterEncoding(enc);
			PrintWriter writer = new PrintWriter(resp.getOutputStream());
			int id = Integer.parseInt(req.getParameter("id"));
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
			sb.append("<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>\n");
			sb.append("<head>\n");
			sb.append("    <meta http-equiv='Content-Type' content='text/html; charset=" + enc + "' />\n");
			sb.append("    <title>Удаление пользователя</title>\n");
			sb.append("</head>\n");
			sb.append("<body>\n");
			sb.append("    <h1>Удаление пользователя</h1>\n");
            if (this.db.deleteUser(id)) {
				sb.append(String.format("<p>Пользователь id:%d удалён.</p><br />\n", id));
			} else {
				sb.append(String.format("<p>Ошибка при удалении пользователя id:%d.</p><br />\n", id));
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