package ru.job4j.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс EncodingFilter реализует фильтр кодировки.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-26
 * @since 2018-03-26
 */
public class EncodingFilter implements Filter {
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Инициализатор.
     * @param filterConfig конфигурация фильтра
     * @throws javax.servlet.ServletException исключение сервлета.
    */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            this.logger = LogManager.getLogger(this.getClass().getSimpleName());
			// /var/lib/tomcat8/webapps/ch1_task1-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch1_task1-1.0\WEB-INF\classes
            String path = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch9_control_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
		} catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Производит фильтрацию.
     * @param request запрос.
     * @param response ответ.
     * @param chain цепь.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
	 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String enc = Charset.defaultCharset().toString();
            response.setCharacterEncoding(enc);
            request.setAttribute("encoding", enc);
            chain.doFilter(request, response);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
	 * Вызывается при уничтожении сервлета.
	 */
    @Override
    public void destroy() {
    }
}