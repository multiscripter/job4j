package ru.job4j.services;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import ru.job4j.checking.DBDriver;
import ru.job4j.models.Offer;
import ru.job4j.utils.Handler;
import ru.job4j.utils.PropertyLoader;

/**
 * Класс OfferRepositoryTest тестирует класс OfferRepository.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-26
 * @since 2018-06-26
 */
public class OfferRepositoryTest {
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
     * Обработчик с вспомогательными методами.
     */
    private static Handler handler;
    /**
     * Путь файла sql.
     */
    private static String path;
    /**
     * Репозиторий объявлений.
     */
    private static OfferRepository repo;
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
        path = UserRepositoryTest.class.getClassLoader().getResource(".").getPath();
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
        path = String.format("%sjunior.pack3.p4.ch3.task1.%s.sql", path, dbmsName);
        ctx.setConfigLocation("file:src/test/resources/" + contextLocalFileName);
        ctx.setServletContext(new MockServletContext());
        ctx.refresh();
        repo = ctx.getBean(OfferRepository.class);
        // Корнем web-приложения в тестах является папка test/resources/
        String ctxPath = ctx.getServletContext().getRealPath("/");
        ctxPath = String.format("%s/fotos", ctxPath);
        String fotosPath = Paths.get(ctxPath).normalize().toString();
        System.err.println("fotosPath: " + fotosPath);
        handler = new Handler("UTF-8", fotosPath);
    }
    /**
     * Тестирует Optional<T> findById(ID var1).
     * Тип: Offer.
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testFindAll() throws Exception {
        List<Offer> actual = new ArrayList<>();
        // Параметр фильтра "объявления только с фотографиями".
        /*String wfoto = null;
        for (Offer offer : repo.findAll(spec)) {
            offer.setFotos(handler.getFotos(Long.toString(offer.getId())));
            if (wfoto == null || (wfoto.equals("1") && offer.getFotos().length > 0)) {
                actual.add(offer);
            }
        }*/
    }
}
