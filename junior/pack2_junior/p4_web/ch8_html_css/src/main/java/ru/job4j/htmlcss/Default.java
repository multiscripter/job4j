package ru.job4j.htmlcss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
//import java.security.NoSuchAlgorithmException;
//import java.sql.SQLException;
//import java.text.ParseException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс Default реализует сервлет по умолчанию.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-08
 * @since 2017-11-19
 */
public class Default extends HttpServlet {
	/**
     * Логгер.
     */
    private Logger logger;
    /**
	 * Инициализатор.
	 */
	@Override
    public void init() throws ServletException {
    	try {
			// /var/lib/tomcat8/webapps/ch8_html_css-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch8_html_css-1.0\WEB-INF\classes
            String path = new File(Default.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			path = path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch8_html_css_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("Default");
		} catch (URISyntaxException | IOException ex) {
			this.logger.error("ERROR", ex);
		}
    }
	/**
	 * Обрабатывает GET-запросы.
	 * http://bot.net:8080/ch8_html_css-1.0/
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
            this.logger.error("INFO Default.doGet()");
            this.logger.error("INFO req.getServletPath(): " + req.getServletPath());
            this.logger.error("INFO req.getScheme(): " + req.getScheme());
            this.logger.error("INFO req.getServerName(): " + req.getServerName());
            this.logger.error("INFO req.getServerPort(): " + req.getServerPort());
            this.logger.error("INFO req.getContextPath(): " + req.getContextPath());
            RequestDispatcher rd = getServletContext().getNamedDispatcher("Default");
            HttpServletRequest wrapper = new HttpServletRequestWrapper(req) {
                public String getServletPath() {
                    return String.format("%s/static", req.getContextPath());
                }
            };
            rd.forward(wrapper, resp);
		} catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
	}
}
