package ru.job4j.web;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.models.Brand;
import ru.job4j.models.IModel;
import ru.job4j.models.Offer;
import ru.job4j.services.DAO;
import ru.job4j.services.Repository;
import ru.job4j.utils.Handler;

/**
 * Класс Index реализует контроллер Чтение и вывод элементов площадки продаж машин.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-02
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
     * DAO.
     */
    @Autowired
    private DAO dao;
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
            String path = context.getRealPath("/");
            path = String.format("%s/fotos", path);
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
            List<Offer> items = new ArrayList<>();
            String brandId = req.getParameter("brand");
            String wfoto = req.getParameter("wfoto");
            HashMap<String, List<String[]>> params = new HashMap<>();
            List<String[]> where = new ArrayList<>();
            if (brandId != null && !brandId.equals("0")) {
                where.add(new String[]{"brands", "id", "=", brandId.trim()});
            }
            if (where.size() > 0) {
                params.put("where", where);
            }
            for (IModel item : this.repo.get("Offer", params)) {
                Offer offer = (Offer) item;
                offer.setFotos(this.handler.getFotos(Integer.toString(offer.getId())));
                if (wfoto == null || (wfoto.equals("1") && offer.getFotos().length > 0)) {
                    items.add(offer);
                }
            }
            model.addAttribute("items", items);
            List<Brand> brands = this.dao.read(new Brand()).stream().map(item -> (Brand) item).collect(Collectors.toList());
            model.addAttribute("brands", brands);
            String root = String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath());
            model.addAttribute("refRoot", root);
            model.addAttribute("offerCreate", String.format("%soffer-create/", root));
            model.addAttribute("offerDelete", String.format("%soffer-delete/", root));
            model.addAttribute("offerUpdate", String.format("%soffer-update/", root));
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
        return "index";
    }
}
