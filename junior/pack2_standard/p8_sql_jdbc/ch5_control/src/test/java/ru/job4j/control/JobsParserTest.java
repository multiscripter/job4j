package ru.job4j.control;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
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
        try {
            this.plfn = "sql.ru.properties";
            String path = this.getClass().getResource(".").getPath().replaceFirst("^/(.:/)", "$1");
            path = String.format("%s../../../../../src/main/resources/", path);
            Path fName = Paths.get(path + this.plfn);
            InputStream is = Files.newInputStream(fName);
            this.props = new Properties();
            this.props.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Проверяет работу JobsParser.
     */
    @Test
    public void checkJobsParser() {
        try {
            JobsParser parser = new JobsParser(this.props, 10);
            parser.parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}