package ru.job4j.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Класс Login реализует контроллер страницы входа.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-08-01
 */
@Controller
public class Login {
    /**
     * Обрабатывает GET-запросы к странице входа.
     * @param req объект запроса.
     * @param model держатель атрибутов модели.
     * @return имя вида (view) страницы входа.
     */
    @RequestMapping(value = "/login/", method = RequestMethod.GET)
    public String login(HttpServletRequest req, Model model) {
        model.addAttribute("refRoot", String.format("%s://%s:%s%s/", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()));
        return "login";
    }
}
