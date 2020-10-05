package ru.job4j.web;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.models.Body;
import ru.job4j.models.Car;
import ru.job4j.models.Offer;
import ru.job4j.models.User;
import ru.job4j.services.BodyRepository;
import ru.job4j.services.BrandRepository;
import ru.job4j.services.CarRepository;
import ru.job4j.services.CarStoreSpecification;
import ru.job4j.services.OfferRepository;
import ru.job4j.utils.Handler;

/**
 * Класс OfferUpdate реализует контроллер Обновление объявления продажи автомобиля.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-02
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
     * Кодировка.
     */
    private String enc;
    /**
     * entityManagerFactory.
     */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
    /**
     * Обработчик с вспомогательными методами.
     */
    private Handler handler;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Репозиторий корпусов.
     */
    @Autowired
    private BodyRepository repoBody;
    /**
     * Репозиторий корпусов.
     */
    @Autowired
    private BrandRepository repoBrand;
    /**
     * Репозиторий корпусов.
     */
    @Autowired
    private CarRepository repoCar;
    /**
     * Репозиторий корпусов.
     */
    @Autowired
    private OfferRepository repoOffer;
    /**
     * Действия после создания бина.
     */
    @PostConstruct
    public void afterConstruct() {
        try {
            this.enc = "UTF-8";
            String path = context.getRealPath("/");
            path = String.format("%s/fotos", path);
            String fotosPath = Paths.get(path).normalize().toString();
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
            User user = (User) req.getSession(false).getAttribute("auth");
            model.addAttribute("user", user);
            String strId = req.getParameter("id");
            model.addAttribute("id", strId);
            Offer offer = this.getOffer(strId);
            model.addAttribute("offer", offer);
            if (offer != null && user.getId().equals(offer.getUser().getId())) {
                model.addAttribute("bodies", this.repoBody.findAll());
                model.addAttribute("brands", this.repoBrand.findAll());
                model.addAttribute("cars", this.repoCar.findAll());
            }
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
                    CarStoreSpecification<Car> cSspecCar = new CarStoreSpecification<>();
                    cSspecCar.setEntityManager(this.entityManager);
                    Specification<Car> specCar = cSspecCar.getSpec(params);
                    List<Car> cars = this.repoCar.findAll(specCar);
                    if (cars.size() == 1) {
                        car = cars.get(0);
                    }
                    offer.setCar(car);
                    Body body = new Body();
                    body.setId(Long.parseLong(fields.get("body")));
                    if (car.getBodies() != null) {
                        for (Body b : car.getBodies()) {
                            if (b.getId().equals(body.getId())) {
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
                    Long id = this.repoOffer.save(offer).getId();
                    offer.setId(id);
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
        CarStoreSpecification<Offer> cSspecOffer = new CarStoreSpecification<>();
        cSspecOffer.setEntityManager(this.entityManager);
        Specification<Offer> specOffer = cSspecOffer.getSpec(params);
        List<Offer> offers = this.repoOffer.findAll(specOffer);
        Offer offer = null;
        if (offers.size() == 1) {
            offer = offers.get(0);
        }
        return offer;
    }
}
