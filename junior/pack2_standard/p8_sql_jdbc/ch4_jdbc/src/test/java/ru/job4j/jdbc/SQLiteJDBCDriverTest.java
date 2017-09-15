package ru.job4j.jdbc;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
/**
 * Класс SQLiteJDBCDriverTest тестирует класс SQLiteJDBCDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-09-12
 */
public class SQLiteJDBCDriverTest {
    /**
     * Ожидаемое число.
     */
    private int expected;
    /**
     * Количество итераций.
     */
    private final int iters = 1_000_000;
    /**
     * Последнее время.
     */
    private long lastTime = 0;
    /**
     * Общее время.
     */
    private long totalTime = 0;
    /**
     * Устанавливает временную метку.
     * @param mark название метки.
     */
    public void setTimeMark(String mark) {
        long curTime = System.currentTimeMillis();
        long period = curTime - this.lastTime;
        this.lastTime = curTime;
        this.totalTime += period;
        System.out.printf(String.format("%-20s%d\n", mark, period));
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        for (int a = 0; a < this.iters; a++) {
            this.expected += a;
        }
    }
    /**
     * Тестирует public Connection getConnection().
     * @throws URISyntaxException ошибка синтаксиса URI.
     * @throws SQLException ошибка SQL.
     * @throws TransformerException ошибка преобразователя.
     * @throws ParserConfigurationException ошибка конфигурирования парсера.
     * @throws FileNotFoundException ошибка файл не найден.
     * @throws XMLStreamException ошибка xml-потока.
     */
    @Test
    public void testGetConnection() throws URISyntaxException, SQLException, TransformerException, ParserConfigurationException, FileNotFoundException, XMLStreamException {
        this.lastTime = System.currentTimeMillis();
        int expected = 0;
        JuniorPack2p8ch4 app = new JuniorPack2p8ch4();
        app.setConnection();
        this.setTimeMark("Prepare");
        app.createTable();
        this.setTimeMark("Create table");
        app.deleteData();
        this.setTimeMark("Delete data");
        app.insertData(this.iters);
        this.setTimeMark("Insert data");
        app.createXML();
        this.setTimeMark("Create xml");
        app.saveXML();
        this.setTimeMark("Write xml into file");
        app.xslt();
        this.setTimeMark("XSLT");
        int result = app.parseXML();
        this.setTimeMark("Parse xml");
        app.close();
        this.setTimeMark("Close resources");
        System.out.println("------------------------");
        System.out.printf("%-20s%d\n", "Total time", this.totalTime);
        System.out.printf("%-20s%d\n", "Elements", this.iters);
        System.out.printf("%-20s%d\n", "Sum", result);
        assertEquals(this.expected, result);
    }
}