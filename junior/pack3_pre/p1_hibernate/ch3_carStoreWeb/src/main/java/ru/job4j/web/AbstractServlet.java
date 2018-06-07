package ru.job4j.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.TimeZone;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * AbstractServlet реализует Абстрактный сервлет.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-22
 * @since 2018-05-10
 */
public abstract class AbstractServlet extends HttpServlet {
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
	 * Инициализатор.
     * @throws javax.servlet.ServletException исключение сервлета.
	 */
	@Override
    public void init() throws ServletException {
    	try {
            // Установить часовой пояс по умолчанию для всего приложения.
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            // /var/lib/tomcat8/webapps/ch3_carStoreWeb/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch3_carStoreWeb\WEB-INF\classes
            String path = new File(AbstractServlet.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			path = path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch3_carStoreWeb_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
        } catch (URISyntaxException | IOException ex) {
			this.logger.error("ERROR", ex);
		}
    }
}