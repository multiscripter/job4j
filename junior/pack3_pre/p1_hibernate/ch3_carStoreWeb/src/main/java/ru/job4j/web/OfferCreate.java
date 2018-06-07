package ru.job4j.web;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.models.Body;
import ru.job4j.models.Brand;
import ru.job4j.models.Car;
import ru.job4j.models.IModel;
import ru.job4j.models.Offer;
import ru.job4j.models.User;
import ru.job4j.services.DAO;
import ru.job4j.services.Repository;
import ru.job4j.utils.Handler;
/**
 * Класс OfferCreate реализует контроллер Создание объявления продажи автомобиля.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-25
 * @since 2018-05-22
 */
public class OfferCreate extends AbstractServlet {
    /**
     * DAO.
     */
    private DAO dao = new DAO();
    /**
     * Кодировка.
     */
    private String enc;
    /**
     * Папка с картинками.
     */
    private String fotosPath;
    /**
     * Обработчик с вспомогательными методами.
     */
    private Handler handler;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Repository.
     */
    private Repository repo = new Repository();
    /**
     * Инициализатор.
     * @throws javax.servlet.ServletException исключение сервлета.
     */
    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.enc = Charset.defaultCharset().toString();
            String path = new File(".").getCanonicalPath();
            /**
             * Вызов this.getServletContext().getContextPath() в методе init()
             * не позволяет покрыть сервлет тестами из-за того,
             * что нельзя сделать mock для ServletContext
             * до вызова Servlet.init(conf); Поэтому пришлось хардкорно
             * прописать ContextPath (ch3_carStoreWeb) и объявить его в pom.xml.
             * К тому же это позволило избавиться от версии, добавляемой
             * Томкэтом в ContextPath.
             */
            path = String.format("%s/webapps/ch3_carStoreWeb/fotos", path);
            String fotosPath = Paths.get(path).normalize().toString();
            this.handler = new Handler(enc, fotosPath);
            //this.logger.error("tempdir: " + this.getServletContext().getAttribute("javax.servlet.context.tempdir"));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/ch3_carStoreWeb/offer-create/
     * @param req объект запроса.
     * @param resp объект ответа сервера.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Offer offer = new Offer();
            offer.setBody(new Body());
            offer.setCar(new Car());
            offer.setStatus(false);
            offer.setUser(new User());
            req.setAttribute("offer", offer);
            req.setAttribute("bodies", this.dao.read(new Body()));
            req.setAttribute("brands", this.dao.read(new Brand()));
            req.setAttribute("cars", this.dao.read(new Car()));
            req.setAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/offerCreateGet.jsp").include(req, resp);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы.
     * http://bot.net:8080/ch3_carStoreWeb/offer-create/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = "";
        String jsp = "offerCreateGet";
        try {
            HashMap<String, String> fields = new HashMap<>();
            List<FileItem> files = new ArrayList<>();
            this.handler.handleFormData(this, req, fields, files);
            Offer offer = new Offer();
            String name = fields.get("name");
            name = new String(name.getBytes("ISO-8859-1"), this.enc);
            User user = new User();
            user.setName(name);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"users", "name", "=", name});
            params.put("where", where);
            List<IModel> result = this.repo.get("User", params);
            if (result != null && result.size() == 1) {
                user = (User) result.get(0);
            } else {
                int id = this.dao.create(user);
                user.setId(id);
            }
            offer.setUser(user);
            where.clear();
            where.add(new String[] {"cars", "id", "=",  fields.get("car")});
            params.clear();
            params.put("where", where);
            Car car = new Car();
            result = this.repo.get("Car", params);
            if (result != null && result.size() == 1) {
                car = (Car) result.get(0);
            }
            offer.setCar(car);
            Body body = new Body();
            body.setId(Integer.parseInt(fields.get("body")));
            if (car.getBodies() != null) {
                for (Body b : car.getBodies()) {
                    if (b.getId() == body.getId()) {
                        body = b;
                        break;
                    }
                }
            }
            offer.setBody(body);
            int price = Integer.parseInt(fields.get("price"));
            offer.setPrice(price);
            Boolean status = "true".equals(fields.get("status"));
            offer.setStatus(status);
            // id возвращается даже если объявление не добавлено в бд.
            int id = this.dao.create(offer);
            offer.setId(id);
            if (id > 0) {
                jsp = "offerCreatePost";
                this.handler.handleFotos(id, files);
                msg = String.format("%s, объявление о продаже %s %s за %d рублей создано.", offer.getUser().getName(), offer.getCar().getBrand().getName(), offer.getCar().getName(), offer.getPrice());
            }
            req.setAttribute("offer", offer);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            msg = "Ошибка создания объявления.";
            jsp = "offerCreatePost";
        } finally {
            req.setAttribute("msg", msg);
            req.setAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            jsp = String.format("/WEB-INF/views/%s.jsp", jsp);
            this.getServletContext().getRequestDispatcher(jsp).include(req, resp);
        }
    }
}