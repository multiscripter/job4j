package ru.job4j.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.models.Item;
import ru.job4j.services.ItemDAO;
import ru.job4j.utils.Filter;
import ru.job4j.utils.Validation;
/**
 * Класс Delete реализует контроллер Удаление элементов TODO-листа.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-18
 * @since 2018-04-03
 */
public class Delete extends AbstractServlet {
    /**
     * Карта фильтров.
     */
    private final HashMap<String, Filter> filters = new HashMap();
    /**
     * ItemDAO.
     */
	private final ItemDAO idao = new ItemDAO();
    /**
     * Логгер.
     */
    private final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
	 * Инициализатор.
     * @throws javax.servlet.ServletException исключение сервлета.
	 */
	@Override
    public void init() throws ServletException {
    	try {
            super.init();
            this.filters.put("id", new Filter("id", new String[]{"isExists", "isFilled", "isDecimal"}));
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
     * Обрабатывает GET-запросы. http://bot.net:8080/ch1_task1-1.0/delete/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch1_task1-1.0/delete/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder jsonb = Json.createObjectBuilder();
        JsonObjectBuilder jsonErrors = Json.createObjectBuilder();
        String status = "";
        try {
            String idStr = req.getParameter("id");
            Validation va = new Validation();
            va.validate("id", idStr, this.filters.get("id").getFilters());
            if (va.hasErrors()) {
                status = "error";
                HashMap<String, String> errors = va.getErrors();
                Set<String> keys = errors.keySet();
                for (String key : keys) {
                    jsonErrors.add(key, errors.get(key));
                }
            } else {
                int id = Integer.parseInt(idStr);
                Item item = new Item(id, "", "", 0L, false);
                this.idao.delete(item);
                status = "ok";
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            status = "error";
            jsonErrors.add("exception", "delete");
        } finally {
            jsonb.add("status", status);
            jsonb.add("errors", jsonErrors);
            JsonObject json = jsonb.build();
            StringWriter strWriter = new StringWriter();
            try (JsonWriter jsonWriter = Json.createWriter(strWriter)) {
               jsonWriter.writeObject(json);
            }
            String jsonData = strWriter.toString();
            resp.setContentType("application/json");
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.append(jsonData);
            writer.flush();
        }
    }
    /**
	 * Вызывается при уничтожении сервлета.
	 */
    @Override
    public void destroy() {
        try {
            this.idao.close();
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
}