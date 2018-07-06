package ru.job4j.web;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.models.IModel;
import ru.job4j.models.Offer;
import ru.job4j.services.DAO;
import ru.job4j.services.Repository;
import ru.job4j.utils.Handler;
/**
 * Класс OfferDelete реализует контроллер Удаление объявления продажи автомобиля.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-03
 * @since 2018-05-24
 */
public class OfferDelete extends AbstractServlet {
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
            path = String.format("%s/webapps/ch4_carStoreIntegrationTests/fotos", path);
            this.fotosPath = Paths.get(path).normalize().toString();
            this.handler = new Handler(this.enc, this.fotosPath);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/ch4_carStoreIntegrationTests/offer-delete/
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
            offer.setFotos(this.handler.getFotos(strId));
            req.setAttribute("offer", offer);
            req.setAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/offerDeleteGet.jsp").include(req, resp);
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы.
     * http://bot.net:8080/ch4_carStoreIntegrationTests/offer-delete/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = "";
        try {
            String strId = req.getParameter("id");
            req.setAttribute("id", strId);
            Offer offer = this.getOffer(strId);
            String name = new String(req.getParameter("name").getBytes("ISO-8859-1"), this.enc);
            if (offer != null && offer.getUser().getName().equals(name)) {
                this.dao.delete(offer);
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
            req.setAttribute("msg", msg);
            req.setAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/offerDeletePost.jsp").include(req, resp);
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
        Offer offer = null;
        List<IModel> result = this.repo.get("Offer", params);
        if (!result.isEmpty()) {
            offer = (Offer) result.get(0);
        }
        return offer;
    }
}