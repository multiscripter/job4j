package ru.job4j.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.models.Body;
import ru.job4j.models.Brand;
import ru.job4j.models.Car;
import ru.job4j.models.IModel;
import ru.job4j.models.Offer;
import ru.job4j.services.DAO;
import ru.job4j.services.Repository;
import ru.job4j.utils.Handler;
/**
 * Класс OfferUpdate реализует контроллер Обновление объявления продажи автомобиля.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-25
 * @since 2018-05-25
 */
public class OfferUpdate extends AbstractServlet {
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
            path = String.format("%s/webapps%s/fotos", path, this.getServletContext().getContextPath());
            this.fotosPath = Paths.get(path).normalize().toString();
            this.handler = new Handler(this.enc, this.fotosPath);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/ch4_carStoreIntegrationTests/offer-update/
     * @param req объект запроса.
     * @param resp объект ответа сервера.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String strId = req.getParameter("id");
            req.setAttribute("id", strId);
            Offer offer = this.getOffer(strId);
            req.setAttribute("offer", offer);
            req.setAttribute("bodies", this.dao.read(new Body()));
            req.setAttribute("brands", this.dao.read(new Brand()));
            req.setAttribute("cars", this.dao.read(new Car()));
            req.setAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/offerUpdateGet.jsp").include(req, resp);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы.
     * http://bot.net:8080/ch4_carStoreIntegrationTests/offer-update/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = "";
        String jsp = "offerUpdateGet";
        try {
            HashMap<String, String> fields = new HashMap<>();
            List<FileItem> files = new ArrayList<>();
            this.handler.handleFormData(this, req, fields, files);
            String strId = fields.get("id");
            Offer offer = this.getOffer(strId);
            if (offer != null) {
                String name = fields.get("name");
                name = new String(name.getBytes("ISO-8859-1"), this.enc);
                if (name.equals(offer.getUser().getName())) {
                    HashMap<String, List<String[]>> params = new HashMap<>();
                    List<String[]> where = new ArrayList<>();
                    where.add(new String[] {"cars", "id", "=",  fields.get("car")});
                    params.put("where", where);
                    Car car = new Car();
                    List<IModel> result = this.repo.get("Car", params);
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
                    this.dao.update(offer);
                    jsp = "offerUpdatePost";
                    this.handler.handleFotos(offer.getId(), files);
                    msg = String.format("%s, объявление о продаже %s %s за %d рублей обновлено.", offer.getUser().getName(), offer.getCar().getBrand().getName(), offer.getCar().getName(), offer.getPrice());
                } else {
                    msg = "У вас нет прав для изменения этого объявления.";
                    jsp = "offerUpdatePost";
                }
            } else {
                msg = String.format("Объявления с id: %s не существует.", strId);
                jsp = "offerUpdatePost";
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            msg = "Ошибка обновления объявления.";
            jsp = "offerUpdatePost";
        } finally {
            req.setAttribute("msg", msg);
            req.setAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            jsp = String.format("/WEB-INF/views/%s.jsp", jsp);
            this.getServletContext().getRequestDispatcher(jsp).include(req, resp);
        }
    }
    /**
     * Получает объявление по идентификатору.
     * @param id идентификатор.
     * @return объявление.
     * @throws Exception исключение.
     */
    private Offer getOffer(String id) throws Exception {
        Offer offer = null;
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[] {"offers", "id", "=", id});
        params.put("where", where);
        List<IModel> result = this.repo.get("Offer", params);
        if (result != null && result.size() == 1) {
            offer = (Offer) result.get(0);
        }
        return offer;
    }
}