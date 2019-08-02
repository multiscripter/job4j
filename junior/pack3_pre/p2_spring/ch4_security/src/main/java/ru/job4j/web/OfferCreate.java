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
import ru.job4j.services.UserRepository;
import ru.job4j.utils.Handler;

/**
 * Класс OfferCreate реализует контроллер Создание объявления продажи автомобиля.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-11
 * @since 2019-05-02
 */
@Controller
public class OfferCreate {
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
     * Репозиторий корпусов.
     */
    @Autowired
    private UserRepository repoUser;
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
     * Обрабатывает GET-запросы к странице создания объявления автомобиля.
     * Возвращает имя вида (view) для рендеринга страницы.
     * @param req объект запроса.
     * @param model держатель атрибутов модели.
     * @return имя вида (view) для рендеринга.
     */
    @RequestMapping(value = "/offer-create/", method = RequestMethod.GET)
    public String processGet(HttpServletRequest req, Model model) {
        try {
            Offer offer = new Offer();
            offer.setBody(new Body());
            offer.setCar(new Car());
            offer.setStatus(false);
            offer.setUser(new User());
            model.addAttribute("offer", offer);
            model.addAttribute("bodies", this.repoBody.findAll());
            model.addAttribute("brands", this.repoBrand.findAll());
            model.addAttribute("cars", this.repoCar.findAll());
            model.addAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return "offerCreateGet";
    }
    /**
     * Обрабатывает POST-запросы от странице создания объявления автомобиля.
     * Возвращает имя вида (view) для рендеринга страницы.
     * @param req объект запроса.
     * @param model держатель атрибутов модели.
     * @return имя вида (view) для рендеринга.
     */
    @RequestMapping(value = "/offer-create/", method = RequestMethod.POST)
    public String processPost(HttpServletRequest req, Model model) {
        String msg = "";
        String jsp = "offerCreateGet";
        try {
            HashMap<String, String> fields = new HashMap<>();
            List<FileItem> files = new ArrayList<>();
            this.handler.handleFormData(context, req, fields, files);
            Offer offer = new Offer();
            String name = fields.get("name");
            name = new String(name.getBytes("ISO-8859-1"), this.enc);
            User user = new User();
            user.setName(name);
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            where.add(new String[] {"users", "name", "=", name});
            params.put("where", where);
            CarStoreSpecification<User> cSspecUser = new CarStoreSpecification<>();
            cSspecUser.setEntityManager(this.entityManager);
            Specification<User> specUser = cSspecUser.getSpec(params);
            List<User> users = this.repoUser.findAll(specUser);
            if (users.size() == 1) {
                user = users.get(0);
            } else {
                Long id = this.repoUser.save(user).getId();
                user.setId(id);
            }
            offer.setUser(user);
            where.clear();
            where.add(new String[] {"cars", "id", "=",  fields.get("car")});
            params.clear();
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
            if (id > 0) {
                jsp = "offerCreatePost";
                this.handler.handleFotos(id, files);
                msg = String.format("%s, объявление о продаже %s %s за %d рублей создано.", offer.getUser().getName(), offer.getCar().getBrand().getName(), offer.getCar().getName(), offer.getPrice());
            }
            model.addAttribute("offer", offer);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            msg = "Ошибка создания объявления.";
            jsp = "offerCreatePost";
        } finally {
            model.addAttribute("msg", msg);
            model.addAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            return jsp;
        }
    }
}
