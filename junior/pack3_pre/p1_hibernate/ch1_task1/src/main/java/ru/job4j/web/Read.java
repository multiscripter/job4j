package ru.job4j.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.models.Item;
import ru.job4j.services.ItemRepository;
import ru.job4j.utils.Property;
import ru.job4j.utils.PropertyLoader;
/**
 * Класс Read реализует контроллер Чтение элементов TODO-листа.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-19
 * @since 2018-03-21
 */
public class Read extends AbstractServlet {
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Список сообщений об ошибках.
     */
    private LinkedList<Property> messages;
    /**
     * ItemRepository.
     */
	private ItemRepository ir = new ItemRepository();
    /**
	 * Инициализатор.
     * @throws javax.servlet.ServletException исключение сервлета.
	 */
	@Override
    public void init() throws ServletException {
    	try {
            super.init();
            this.messages = new PropertyLoader("junior.pack3.p1.ch1.task1.msgs.properties").getPropertiesList();
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
    }
    /**
	 * Обрабатывает GET-запросы.
	 * http://bot.net:8080/ch1_task1-1.0/
     * @param req объект запроса.
     * @param resp объект ответа сервера.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
            resp.setContentType("text/html");
            List<Item> items = this.ir.getItems(new HashMap<>());
            req.setAttribute("items", items);
            req.setAttribute("messages", this.messages);
            req.setAttribute("action", String.format("%s://%s:%s%s%s/create/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").include(req, resp);
        } catch (Exception ex) {
			this.logger.error("ERROR", ex);
		}
	}
}