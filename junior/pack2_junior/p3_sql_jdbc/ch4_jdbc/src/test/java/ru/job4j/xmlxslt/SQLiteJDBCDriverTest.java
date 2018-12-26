package ru.job4j.xmlxslt;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 * Класс SQLiteJDBCDriverTest тестирует класс SQLiteJDBCDriver.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-19
 * @since 2017-09-12
 */
public class SQLiteJDBCDriverTest {
    /**
     * Логгер.
     */
    private Logger logger;
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
        this.logger = LogManager.getLogger(this.getClass().getSimpleName());
        for (int a = 0; a < this.iters; a++) {
            this.expected += a;
        }
    }
    /**
     * Тестирует работу приложения.
     * @throws Exception исключение.
     */
    @Test
    public void checkApplication() throws Exception {
        try {
            this.lastTime = System.currentTimeMillis();
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
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует public Connection getConnection(String url).
     * @throws Exception исключение.
     */
    @Test
    public void testGetConnection() throws Exception {
        try {
            SQLiteJDBCDriver driver = new SQLiteJDBCDriver();
            Connection con = driver.getConnection("testGetConnection.db");
            assertNotNull(con);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            throw new Exception(ex);
        }
    }
    /**
     * Тестирует public Connection getConnection(String url).
     * @throws java.sql.SQLException исключение базы данных.
     */
    @Test(expected = SQLException.class)
    public void testGetConnectionThrowsSQLException() throws SQLException {
        SQLiteJDBCDriver driver = new SQLiteJDBCDriver();
        driver.getConnection("wrongPath");
    }
}