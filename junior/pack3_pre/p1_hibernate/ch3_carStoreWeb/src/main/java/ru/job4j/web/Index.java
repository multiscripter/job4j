package ru.job4j.web;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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
 * @version 2018-06-06
 * @since 2018-03-21
 */
public class Index extends AbstractServlet {
    /**
     * DAO.
     */
    private DAO dao = new DAO();
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
	private Repository repo = new Repository();
    /**
	 * Инициализатор.
     * @throws javax.servlet.ServletException исключение сервлета.
	 */
	@Override
    public void init() throws ServletException {
    	try {
            super.init();
            String enc = Charset.defaultCharset().toString();
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
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Обрабатывает GET-запросы.
	 * http://bot.net:8080/ch3_carStoreWeb/
     * @param req объект запроса.
     * @param resp объект ответа сервера.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            req.setAttribute("items", items);
			List<Brand> brands = new ArrayList<>();
            for (IModel item : this.dao.read(new Brand())) {
                brands.add((Brand) item);
            }
            req.setAttribute("brands", brands);
			String root = String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath());
			req.setAttribute("refRoot", root);
            req.setAttribute("offerCreate", String.format("%soffer-create/", root));
			req.setAttribute("offerDelete", String.format("%soffer-delete/", root));
			req.setAttribute("offerUpdate", String.format("%soffer-update/", root));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").include(req, resp);
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
	}
    /**
     * Обрабатывает POST-запросы.
     * http://bot.net:8080/ch3_carStoreWeb/
     * @param req объект запроса.
     * @param resp объект ответа сервера.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        brand = brand != null ? brand.trim() : "";
        String wfoto = req.getParameter("wfoto");
        wfoto = wfoto != null ? wfoto.trim() : "";
        String url = String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath());
        if (brand.length() > 0 || wfoto.length() > 0) {
            String params = "?";
            if (brand.length() > 0) {
                params = String.format("%sbrand=%s&", params, brand);
            }
            if (wfoto.length() > 0) {
                params = String.format("%swfoto=%s&", params, wfoto);
            }
            url = String.format("%s%s", url, params.substring(0, params.length() - 1));
        }
        resp.sendRedirect(url);
    }
}