package ru.job4j.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Класс ImageServlet реализует контроллер, обрабатывающий http-запросы изображений.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-06-19
 * @since 2018-06-19
 */
public class ImageServlet extends AbstractServlet {
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
     * @throws javax.servlet.ServletException исключение сервлета.
     */
    @Override
    public void init() throws ServletException {
        try {
            super.init();
            String path = new File(".").getCanonicalPath();
            /**
             * Вызов this.getServletContext().getContextPath() в методе init()
             * не позволяет покрыть сервлет тестами из-за того,
             * что нельзя сделать mock для ServletContext
             * до вызова Servlet.init(conf); Поэтому пришлось хардкорно
             * прописать ContextPath (ch2_carStoreWeb) и объявить его в pom.xml.
             * К тому же это позволило избавиться от версии, добавляемой
             * Томкэтом в ContextPath.
             */
            path = String.format("%s/webapps", path);
            this.fotosPath = Paths.get(path).normalize().toString();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/ch2_carStoreWeb/fotos/*
     * @param req объект запроса.
     * @param resp объект ответа сервера.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] reqUriArr = req.getRequestURI().split("\\.");
            String ext = reqUriArr[reqUriArr.length - 1];
            String fName = Paths.get(this.fotosPath + req.getRequestURI()).normalize().toString();
            File file = new File(fName);
            if (!file.isFile()) {
                ext = "png";
                String[] fNameArr = fName.split("/");
                fNameArr[fNameArr.length - 1] = "no-photo.png";
                fName = String.join("/", fNameArr);
            }
            if (ext.equals("png")) {
                resp.setContentType("image/png");
            } else if (ext.equals("jpg") || ext.equals("jpeg")) {
                resp.setContentType("image/jpeg");
            }
            file = new File(fName);
            BufferedImage bi = ImageIO.read(file);
            OutputStream out = resp.getOutputStream();
            ImageIO.write(bi, ext, out);
            out.close();
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
}