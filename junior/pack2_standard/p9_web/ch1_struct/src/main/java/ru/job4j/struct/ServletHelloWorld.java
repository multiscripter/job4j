package ru.job4j.struct;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс ServletHelloWorld реализует сущность Сервлет ServletHelloWorld.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-02
 */
public class ServletHelloWorld extends HttpServlet {
	/**
	 * Логгер.
	 */
	private static final Logger LOGGER = LogManager.getLogger("ServletHelloWorld");
	/**
	 * Обрабатывает GET-запросы.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = new PrintWriter(resp.getOutputStream());
		writer.append("Hello, World!");
		writer.flush();
	}
}
