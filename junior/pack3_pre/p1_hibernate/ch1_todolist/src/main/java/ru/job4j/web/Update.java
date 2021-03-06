package ru.job4j.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
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
import ru.job4j.services.ItemRepository;
import ru.job4j.utils.Filter;
import ru.job4j.utils.Validation;
/**
 * Класс Update реализует контроллер Обновление элементов TODO-листа.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-12
 * @since 2018-04-19
 */
public class Update extends AbstractServlet {
    /**
     * Карта фильтров.
     */
    private final HashMap<String, Filter> filters = new HashMap<>();
    /**
     * ItemDAO.
     */
	private final ItemDAO idao = new ItemDAO();
    /**
     * ItemRepository.
     */
	private ItemRepository ir = new ItemRepository();
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
            this.filters.put("name", new Filter("name", new String[]{"isExists", "isFilled"}));
            this.filters.put("descr", new Filter("descr", new String[]{"isExists", "isFilled"}));
            this.filters.put("created", new Filter("created", new String[]{"isExists", "isFilled"}));
            this.filters.put("done", new Filter("done", new String[]{"isExists", "isFilled"}));
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
     * Обрабатывает GET-запросы. http://bot.net:8080/ch1_todolist-1.0/update/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch1_todolist-1.0/update/
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
            String name = req.getParameter("name");
            String descr = req.getParameter("descr");
            String createdStr = req.getParameter("created");
            String doneStr = req.getParameter("done");
            /*String ajax = req.getParameter("type");
            if (ajax == null && !"ajax".equals(ajax.trim())) {
                String enc = (String) req.getAttribute("encoding");
                name = new String(name.getBytes("ISO-8859-1"), enc);
                descr = new String(descr.getBytes("ISO-8859-1"), enc);
            }*/
            Validation va = new Validation();
            va.validate("id", idStr, this.filters.get("id").getFilters());
            va.validate("name", name, this.filters.get("name").getFilters());
            va.validate("descr", descr, this.filters.get("descr").getFilters());
            va.validate("created", createdStr, this.filters.get("created").getFilters());
            if (!va.hasError("created")) {
                va.isDateFormat("created", "yyyy-MM-dd H:m:s", createdStr);
            }
            va.validate("done", doneStr, this.filters.get("done").getFilters());
            if (va.hasErrors()) {
                status = "error";
                HashMap<String, String> errors = va.getErrors();
                Set<String> keys = errors.keySet();
                for (String key : keys) {
                    jsonErrors.add(key, errors.get(key));
                }
            } else {
                HashMap<String, String> params = new HashMap<>();
                params.put("whereField", "id");
                params.put("whereOp", "=");
                params.put("whereVal", idStr);
                List<Item> items = this.ir.getItems(params);
                Item item = items.get(0);
                if (item != null) {
                    int id = Integer.parseInt(idStr);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
                    long created = sdf.parse(createdStr).getTime();
                    boolean done = Boolean.parseBoolean(doneStr);
                    item = new Item(id, name.trim(), descr.trim(), created, done);
                    this.idao.update(item);
                    status = "ok";
                } else {
                    status = "error";
                    jsonErrors.add("item", "notexists");
                }
            }
        } catch (Exception ex) {
            this.logger.error("ERROR", ex);
            status = "error";
            jsonErrors.add("exception", "update");
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