package ru.job4j.web;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.models.Brand;
import ru.job4j.models.Offer;
import ru.job4j.models.User;
import ru.job4j.services.BrandRepository;
import ru.job4j.services.CarStoreSpecification;
import ru.job4j.services.OfferRepository;
import ru.job4j.services.UserRepository;
import ru.job4j.utils.Handler;

/**
 * Класс Index реализует контроллер Чтение и вывод элементов площадки продаж машин.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-05-01
 */
@Controller
public class Index {
    /**
     * Контекст.
     */
    @Autowired
    private ServletContext context;
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
     * Репозиторий Brand.
     */
    @Autowired
    private BrandRepository repoBrand;
    /**
     * Репозиторий Offer.
     */
    @Autowired
    private OfferRepository repoOffer;
    /**
     * Репозиторий корпусов.
     */
    @Autowired
    private UserRepository repoUser;
    /**
     * path.
     */
    private String path;
    /**
     * Действия после создания бина.
     */
    @PostConstruct
    public void afterConstruct() {
        try {
            // TODO: добавить favicon.ico в webapp.
            String path = getClass().getResource("/WEB-INF/web.xml").getPath();
            path = path.replaceFirst(".war!/WEB-INF/web.xml", "");
            path = path.replaceFirst("file:", "");
            path = path.replaceFirst("^/(.:/)", "$1");
            this.path = path;
            path = String.format("%s/resources/fotos", path);
            String fotosPath = Paths.get(path).normalize().toString();
            this.handler = new Handler("UTF-8", fotosPath);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы к главной странице.
     * Выбирает вид (view) для рендеринга главной страницы, возвращая его имя.
     * @param req объект запроса.
     * @param model держатель атрибутов модели.
     * @return имя вида (view) для рендеринга.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String processGet(HttpServletRequest req, Model model) {
        try {
            //this.setPrepare();
            CarStoreSpecification<Brand> brandsCsSpec = new CarStoreSpecification<>();
            brandsCsSpec.setEntityManager(this.entityManager);
            Specification<Brand> brandsSpec = brandsCsSpec.getSpec(new HashMap<>());
            List<Brand> brands = this.repoBrand.findAll(brandsSpec).stream().map(item -> item).collect(Collectors.toList());
            model.addAttribute("brands", brands);
            List<Offer> items = new ArrayList<>();
            String wfoto = req.getParameter("wfoto");
            String brandId = req.getParameter("brand");
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            if (brandId != null && !brandId.equals("0")) {
                where.add(new String[]{"brands", "id", "=", brandId.trim()});
            }
            if (where.size() > 0) {
                params.put("where", where);
            }
            List<String[]> ends = new ArrayList<>();
            ends.add(new String[] {Offer.class.getName(), Brand.class.getName()});
            params.put("whereEnds", ends);
            List<String[]> order = new ArrayList<>();
            order.add(new String[]{"id", "desc"});
            params.put("order", order);
            CarStoreSpecification<Offer> csSpec = new CarStoreSpecification<>();
            csSpec.setEntityManager(this.entityManager);
            Specification<Offer> spec = csSpec.getSpec(params);
            for (Offer offer : this.repoOffer.findAll(spec)) {
                offer.setFotos(this.handler.getFotos(Long.toString(offer.getId())));
                if (wfoto == null || (wfoto.equals("1") && offer.getFotos().length > 0)) {
                    items.add(offer);
                }
            }
            model.addAttribute("items", items);
            String root = String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath());
            model.addAttribute("refRoot", root);
            model.addAttribute("offerCreate", String.format("%soffer-create/", root));
            model.addAttribute("offerDelete", String.format("%soffer-delete/", root));
            model.addAttribute("offerUpdate", String.format("%soffer-update/", root));
            HttpSession sess = req.getSession();
            User user = null;
            if (sess.getAttribute("auth") != null) {
                user = (User) sess.getAttribute("auth");
            }
            model.addAttribute("user", user);
            model.addAttribute("path", this.path);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return "index";
    }
}
