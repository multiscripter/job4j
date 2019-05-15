package ru.job4j.web;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * @version 2019-05-04
 * @since 2019-05-04
 */
@Controller
public class OfferUpdate {
    /**
     * Контекст.
     */
    @Autowired
    private ServletContext context;
    /**
     * DAO.
     */
    @Autowired
    private DAO dao;
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
     * Репозиторий.
     */
    @Autowired
    private Repository repo;
    /**
     * Действия после создания бина.
     */
    @PostConstruct
    public void afterConstruct() {
        try {
            this.enc = "UTF-8";
            String path = context.getRealPath("/");
            path = String.format("%s/fotos", path);
            this.fotosPath = Paths.get(path).normalize().toString();
            this.handler = new Handler(enc, fotosPath);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы к странице обновления объявления автомобиля.
     * Возвращает имя вида (view) для рендеринга страницы.
     * @param req объект запроса.
     * @param model держатель атрибутов модели.
     * @return имя вида (view) для рендеринга.
     */
    @RequestMapping(value = "/offer-update/", method = RequestMethod.GET)
    public String processGet(HttpServletRequest req, Model model) {
        try {
            String strId = req.getParameter("id");
            model.addAttribute("id", strId);
            Offer offer = this.getOffer(strId);
            model.addAttribute("offer", offer);
            model.addAttribute("bodies", this.dao.read(new Body()));
            model.addAttribute("brands", this.dao.read(new Brand()));
            model.addAttribute("cars", this.dao.read(new Car()));
            model.addAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return "offerUpdateGet";
    }
    /**
     * Обрабатывает POST-запросы от странице обновления объявления автомобиля.
     * Возвращает имя вида (view) для рендеринга страницы.
     * @param req объект запроса.
     * @param model держатель атрибутов модели.
     * @return имя вида (view) для рендеринга.
     */
    @RequestMapping(value = "/offer-update/", method = RequestMethod.POST)
    public String processPost(HttpServletRequest req, Model model) {
        String msg = "";
        String jsp = "offerUpdateGet";
        try {
            HashMap<String, String> fields = new HashMap<>();
            List<FileItem> files = new ArrayList<>();
            this.handler.handleFormData(context, req, fields, files);
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
            msg = "Ошибка создания объявления.";
            jsp = "offerUpdatePost";
        } finally {
            model.addAttribute("msg", msg);
            model.addAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            return jsp;
        }
    }
    /**
     * Получает объявление по идентификатору.
     * @param id идентификатор объявления.
     * @return объявление.
     * @throws Exception исключение.
     */
    private Offer getOffer(String id) throws Exception {
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[] {"offers", "id", "=", id});
        params.put("where", where);
        Offer offer = null;
        List<IModel> result = this.repo.get("Offer", params);
        if (!result.isEmpty()) {
            offer = (Offer) result.get(0);
        }
        return offer;
    }
}
