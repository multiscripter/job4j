package ru.job4j.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Item;
/**
 * Класс ItemDAOTest тестирует класс ItemDAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-04
 * @since 2018-04-04
 */
public class ItemDAOTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        // Установить часовой пояс по умолчанию для всего приложения.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        this.logger = LogManager.getLogger(this.getClass().getSimpleName());
        try {
            this.driver = new DBDriver("jdbc:postgresql://localhost:5432/jpack3p1ch1task1", "postgres", "postgresrootpass");
            String path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
            path = path.replaceFirst("^/(.:/)", "$1");
            this.driver.executeSqlScript(path + "../../src/main/resources/junior.pack3.p1.ch1.task1.sql");
        } catch (IOException | SQLException | URISyntaxException ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void create(Item obj) throws PersistenceException.
     */
    @Test
    public void testCreate() {
        try {
            String item = "Заголовок тестовой заявки ItemDAOTest.testCreate()";
            String descr = "Текст заявки ItemDAOTest.testCreate()";
            //2018-04-04 20:13:14
            Item expected = new Item(0, item, descr, 1522872794000L, false);
            ItemDAO idao = new ItemDAO();
            idao.create(expected);
            idao.close();
            List<HashMap<String, String>> result = this.driver.select(String.format("select * from todolist where id = %d", expected.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            long created = sdf.parse(result.get(0).get("created")).getTime();
            Item actual = new Item(Integer.parseInt(result.get(0).get("id")), result.get(0).get("item"), result.get(0).get("descr"), created, result.get(0).get("done").equals("t") ? true : false);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void delete(Item obj) throws PersistenceException.
     */
    @Test
    public void testDelete() {
        try {
            int id = 1;
            Item item = new Item(id, "Fake", "Fake", 0L, false);
            ItemDAO idao = new ItemDAO();
            idao.delete(item); // Удлаяет запросом: delete from items where id=?
            idao.close();
            List<HashMap<String, String>> result = this.driver.select(String.format("select id from todolist where id = %d", id));
            assertTrue(result.isEmpty());
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public void update(E obj).
     */
    @Test
    public void testUpdate() {
        try {
            String name = "testUpdate name";
            String descr = "testUpdate descr";
            // 2018-04-04 20:37:53
            Item expected = new Item(0, name, descr, 1522874273000L, false);
            ItemDAO idao = new ItemDAO();
            idao.create(expected);
            expected.setItem("Updated item title.");
            expected.setDescr("Updated item text.");
            expected.setDone(true);
            idao.update(expected);
            idao.close();
            List<HashMap<String, String>> result = this.driver.select(String.format("select * from todolist where id = %d", expected.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            long created = sdf.parse(result.get(0).get("created")).getTime();
            Item actual = new Item(Integer.parseInt(result.get(0).get("id")), result.get(0).get("item"), result.get(0).get("descr"), created, result.get(0).get("done").equals("t") ? true : false);
            assertEquals(expected, actual);
        } catch (NumberFormatException | ParseException | SQLException ex) {
            this.logger.error("ERROR", ex);
            ex.printStackTrace();
        }
    }
}