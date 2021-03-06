package ru.job4j.control.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.control.persistence.MusicTypeDAO;
import ru.job4j.control.persistence.RoleDAO;
import ru.job4j.control.persistence.UserDAO;
import ru.job4j.control.service.Address;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
/**
 * Класс Update реализует контроллер обновления пользователя.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-23
 * @since 2018-01-14
 */
public class Update extends AbstractServlet {
    /**
     * Роль администратора.
     */
    private Role adminRole;
    /**
     * Логгер.
     */
    private Logger logger;
    /**
     * MusicTypeDAO.
     */
    private MusicTypeDAO mts;
    /**
     * RoleDAO.
     */
    private RoleDAO rls;
    /**
     * UserDAO.
     */
    private UserDAO us;
    /**
     * Инициализатор.
     * @throws javax.servlet.ServletException исключение сервлета.
     */
    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.logger = LogManager.getLogger("Create");
            this.mts = new MusicTypeDAO();
            this.rls = new RoleDAO();
            this.us = new UserDAO();
            this.adminRole = this.rls.getRoleByName("administrator");
        } catch (SQLException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает GET-запросы.
     * http://bot.net:8080/ch9_control-1.0/update/?id=100500
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            String id = req.getParameter("id");
            String message = "";
            User user = this.us.getUserById(Integer.parseInt(req.getParameter("id")));
            if (user == null) {
                message = String.format("Ошибка при попытке получить пользователя с id: %s<br />Нет такого пользователя.", id);
            } else {
                req.setAttribute("adminRole", this.adminRole);
                if (((User) req.getSession(false).getAttribute("auth")).getRoleId() == this.adminRole.getId()) {
                    req.setAttribute("roles", this.rls.getRoles());
                }
                req.setAttribute("mtypes", this.mts.getMusicTypes());
            }
            req.setAttribute("action", String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            String referer = req.getHeader("referer");
            String refName = referer != null && referer.contains("update") ? "refHome" : "refBack";
            req.setAttribute(refName, String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("user", user);
            req.setAttribute("message", message);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/updateGet.jsp").include(req, resp);
        } catch (NoSuchAlgorithmException | ParseException | SQLException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Обрабатывает POST-запросы. http://bot.net:8080/ch9_control-1.0/update/
     * @param req запрос.
     * @param resp ответ.
     * @throws javax.servlet.ServletException исключение сервлета.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            String enc = (String) req.getAttribute("encoding");
            this.us.setEncoding(enc);
            req.setAttribute("refHome", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            req.setAttribute("refLogin", String.format("%s://%s:%s%s/login/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
            String id = req.getParameter("id");
            String role = req.getParameter("role");
            User user = this.us.getUserById(Integer.parseInt(id));
            user.setLogin(new String(req.getParameter("login").getBytes("ISO-8859-1"), enc));
            user.setPass(new String(req.getParameter("pass").getBytes("ISO-8859-1"), enc));
            Address address = user.getAddress();
            address.setCountry(new String(req.getParameter("country").getBytes("ISO-8859-1"), enc));
            address.setCity(new String(req.getParameter("city").getBytes("ISO-8859-1"), enc));
            address.setAddr(new String(req.getParameter("addr").getBytes("ISO-8859-1"), enc));
            user.setAddress(address);
            String[] mtypeIds = req.getParameterValues("mtypes");
            user.setMusicTypes(this.mts.getMusicTypesByIds(mtypeIds));
            String message = String.format("Ошибка при редактировании пользователя id: %d.", user.getId());
            User auth = (User) req.getSession(false).getAttribute("auth");
            if (auth.getRoleId() == this.adminRole.getId()) {
                user.setRole(this.rls.getRoleById(Integer.parseInt(role)));
            }
            try {
                if (this.us.editUser(user)) {
                    message = String.format("Пользователь id: %d отредактирован.", user.getId());
                }
            } catch (SQLException ex) {
                this.logger.error("ERROR", ex);
            }
            req.setAttribute("message", message);
            req.setAttribute("refBack", String.format("%s://%s:%s%s/update/?id=%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), id));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/updatePost.jsp").include(req, resp);
        } catch (NoSuchAlgorithmException | ParseException | SecurityException | SQLException | UnsupportedEncodingException ex) {
            this.logger.error("ERROR", ex);
        }
    }
}