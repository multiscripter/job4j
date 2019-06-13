package ru.job4j.web;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.models.Offer;
import ru.job4j.services.CarStoreSpecification;
import ru.job4j.services.OfferRepository;
import ru.job4j.utils.Handler;

/**
 * Класс OfferDelete реализует контроллер Удаление объявления продажи автомобиля.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-11
 * @since 2019-05-04
 */
@Controller
public class OfferDelete {
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
            this.fotosPath = Paths.get(path).normalize().toString();
            this.handler = new Handler(enc, fotosPath);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы к странице удаления объявления автомобиля.
     * Возвращает имя вида (view) для рендеринга страницы.
     * @param req объект запроса.
     * @param model держатель атрибутов модели.
     * @return имя вида (view) для рендеринга.
     */
    @RequestMapping(value = "/offer-delete/", method = RequestMethod.GET)
    public String processGet(HttpServletRequest req, Model model) {
        try {
            String strId = req.getParameter("id");
            model.addAttribute("id", strId);
            Offer offer = this.getOffer(strId);
            offer.setFotos(this.handler.getFotos(strId));
            model.addAttribute("offer", offer);
            model.addAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return "offerDeleteGet";
    }
    /**
     * Обрабатывает POST-запросы от странице удаления объявления автомобиля.
     * Возвращает имя вида (view) для рендеринга страницы.
     * @param req объект запроса.
     * @param model держатель атрибутов модели.
     * @return имя вида (view) для рендеринга.
     */
    @RequestMapping(value = "/offer-delete/", method = RequestMethod.POST)
    public String processPost(HttpServletRequest req, Model model) {
        String msg = "";
        try {
            String strId = req.getParameter("id");
            model.addAttribute("id", strId);
            Offer offer = this.getOffer(strId);
            String name = req.getParameter("name");
            if (offer != null && offer.getUser().getName().equals(name)) {
                this.repoOffer.delete(offer);
                msg = String.format("%s, объявление о продаже %s %s за %d рублей удалено.", offer.getUser().getName(), offer.getCar().getBrand().getName(), offer.getCar().getName(), offer.getPrice());
                this.deleteFotos(strId);
            } else {
                if (offer == null) {
                    msg = "Объявление уже удалено или не существует.";
                } else if (!offer.getUser().getName().equals(name)) {
                    msg = "У вас нет прав для удаления этого объявления.";
                }
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            msg = "Ошибка удаления объявления.";
        } finally {
            model.addAttribute("msg", msg);
            model.addAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            return "offerDeletePost";
        }
    }
    /**
     * Удаляет картинки объявления.
     * @param id идентификатор объявления.
     */
    private void deleteFotos(String id) {
        try {
            File dir = new File(String.format("%s/%s", this.fotosPath, id));
            if (dir.exists() && dir.isDirectory()) {
                String[] files = dir.list();
                for (String file: files) {
                    File currentFile = new File(dir.getPath(), file);
                    currentFile.delete();
                }
                dir.delete();
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
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
