package ru.job4j.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс JobsParserTest тестирует класс JobsParser.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-23
 */
public class JobsParserTest {
    /**
     * Локальное имя файла свойств вида.
     */
    private String plfn;
    /**
     * Свойства вида.
     */
    private Properties props;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        String path = this.getClass().getResource(".").getPath().replaceFirst("^/(.:/)", "$1");
        path = String.format("%s../../../../../src/main/resources/", path);
        Logger logger = LogManager.getLogger("JobsParserTest");
        XmlConfigurationFactory xcf = new XmlConfigurationFactory();
        try {
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(path + "log4j2.xml")));
            Configuration conf = xcf.getConfiguration(new LoggerContext("JobsParserTestContext"), source);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
            ctx.stop();
            ctx.start(conf);
            this.plfn = "sql.ru.properties";
            Path fName = Paths.get(path + this.plfn);
            InputStream is = Files.newInputStream(fName);
            this.props = new Properties();
            this.props.load(is);
        } catch (IOException ex) {
            logger.error("ERROR", ex);
        }
    }
    /**
     * Проверяет работу JobsParser.
     */
    @Test
    public void checkJobsParser() {
        JobsParser parser = new JobsParser(this.props, 20);
        parser.begin();
    }
}