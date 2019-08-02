package ru.job4j.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Body;
import ru.job4j.utils.PropertyLoader;
import static org.junit.Assert.assertEquals;

/**
 * Класс BodyRepositoryTest тестирует класс BodyRepository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-26
 * @since 2018-06-24
 */
public class BodyRepositoryTest {
    /**
     * Локальное имя файла конфигурации Hibernate.
     */
    private static String contextLocalFileName;
    /**
     * Название текущей СУБД.
     */
    private static String dbmsName;
    /**
     * Драйвер бд.
     */
    private static DBDriver driver;
    /**
     * Путь файла sql.
     */
    private static String path;
    /**
     * Репозиторий типов корпусов автомобилей.
     */
    private static BodyRepository repo;
    /**
     * Действия после теста.
     * @throws Exception исключение.
     */
    @After
    public void afterTest() throws Exception  {
        driver.executeSqlScript(path);
    }
    /**
     * Действия после всех тестов.
     * @throws Exception исключение.
     */
    @AfterClass
    public static void afterAllTests() throws Exception {
        driver.close();
    }
    /**
     * Действия перед всеми тестами.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        path = BodyRepositoryTest.class.getClassLoader().getResource(".").getPath();
        path = path.replaceFirst("^/(.:/)", "$1");
        dbmsName = new PropertyLoader(String.format("%s%s", path, "activeDBMS.properties")).getPropValue("name");
        contextLocalFileName = "servlet-context.xml";
        if (!dbmsName.equals("PostgreSQL")) {
            contextLocalFileName = String.format("%s.servlet-context.xml", dbmsName);
        }
        driver = new DBDriver(path + dbmsName);
        path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        path = path.replaceFirst("^/(.:/)", "$1");
        path = String.format("%sjunior.pack3.p4.ch4.task1.%s.sql", path, dbmsName);
        ctx.setConfigLocation("file:src/test/resources/" + contextLocalFileName);
        ctx.setServletContext(new MockServletContext());
        ctx.refresh();
        repo = ctx.getBean(BodyRepository.class);
    }
    /**
     * Тестирует List<T> findAll(@Nullable Specification<T> var1).
     * Тип: Body.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testFindAll() throws Exception {
        String query = String.format("select * from bodies");
        List<HashMap<String, String>> bodies = driver.select(query);
        List<Body> expected = new ArrayList<>();
        for (HashMap<String, String> body : bodies) {
            expected.add(new Body(Long.parseLong(body.get("id")), body.get("name")));
        }
        List<Body> actual = (List<Body>) repo.findAll();
        assertEquals(expected, actual);
    }
}
