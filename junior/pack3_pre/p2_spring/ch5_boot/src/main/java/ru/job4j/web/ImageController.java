package ru.job4j.web;

import java.io.File;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;
/**
 * Класс ImageController реализует контроллер, обрабатывающий http-запросы изображений.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2020-10-05
 * @since 2018-06-19
 */
@Controller
@RequestMapping("/resources/fotos")
public class ImageController {
    /**
     * Контекст.
     */
    @Autowired
    private ServletContext ctx;
    /**
     * Директория с фотографиями объявлений.
     */
    private String fotosPath;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Инициализатор.
     */
    @PostConstruct
    public void afterConstruct() {
        try {
            String path = getClass().getResource("/WEB-INF").getPath();
            path = path.replaceFirst(".war!/WEB-INF", "");
            path = path.replaceFirst("file:", "");
            path = path.replaceFirst("^/(.:/)", "$1");
            path = String.format("%s/resources/fotos", path);
            this.fotosPath = Paths.get(path).normalize().toString();
            System.err.println(this.fotosPath);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/resources/fotos/{offerId}/{fotoName}
     * @throws javax.lang.Exception исключение.
     */
    @RequestMapping(value = "/{offerId}/{fotoName}", method = RequestMethod.GET)
    public void getImage(@PathVariable String ownerId, @PathVariable String fotoName) throws Exception {
        this.logger.error("gahsgfagshdfgsafkassaaadfjasfs");
        System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.err.println(ownerId);
        System.err.println(fotoName);
        HttpHeaders headers = new HttpHeaders();
        //Resource resource = new ServletContextResource(ctx, "/resources/fotos/no-photo.png");
        //new ResponseEntity<>(resource, headers, HttpStatus.OK);
        //return null;
    }
}