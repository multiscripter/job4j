package ru.job4j.htmlcss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
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
 * Класс DefaultFilter реализует функционал фильтрации по умолчанию.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-08
 * @since 2017-12-19
 */
public class DefaultFilter implements Filter {
    /**
     * Логгер.
     */
    private Logger logger;
    /**
	 * Инициализатор.
	 */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
			// /var/lib/tomcat8/webapps/ch8_html_css-1.0/WEB-INF/classes
            // \Program FIles\Apache Software Foundation\Tomcat 8.5\webapps\ch8_html_css-1.0\WEB-INF\classes
            String path = new File(DefaultFilter.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
			path = path.replaceFirst("^/(.:/)", "$1");
			XmlConfigurationFactory xcf = new XmlConfigurationFactory();
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("ch8_html_css_context"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.logger = LogManager.getLogger("DefaultFilter");
		} catch (URISyntaxException | IOException ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Производит фильтрацию.
	 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String reqPath = req.getServletPath();
        //this.logger.error("INFO req.getServletPath(): " + req.getServletPath());
        //this.logger.error("INFO req.getScheme(): " + req.getScheme());
        //this.logger.error("INFO req.getServerName(): " + req.getServerName());
        //this.logger.error("INFO req.getServerPort(): " + req.getServerPort());
        //this.logger.error("INFO req.getContextPath(): " + req.getContextPath());// /ch8_html_css-1.0
        if (reqPath.contains("/static/")) {
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(req) {
                public String getServletPath() {
                    String reqPath = req.getServletPath();
                    return reqPath.substring(reqPath.indexOf("/static/"));
                }
            };
            chain.doFilter(wrapper, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }
    /**
	 * Действия при уничтожении фильтра.
	 */
    @Override
    public void destroy() {
    }
}