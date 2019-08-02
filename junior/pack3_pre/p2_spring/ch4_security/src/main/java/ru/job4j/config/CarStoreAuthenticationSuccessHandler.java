package ru.job4j.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.job4j.models.User;
import ru.job4j.services.CarStoreSpecification;
import ru.job4j.services.UserRepository;

/**
 * Класс CarStoreAuthenticationSuccessHandler реализует пользовательский
 * обработчик успешного входа.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-08-01
 */
public class CarStoreAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * entityManagerFactory.
     */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Репозиторий корпусов.
     */
    @Autowired
    private UserRepository repoUser;
    /**
     * Действия при успешном входе.
     * @param req запрос.
     * @param resp ответ.
     * @param auth объект аутентификации.
     * @throws IOException исключение ввода-вывода.
     * @throws ServletException исключение сервлета.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[] {"users", "name", "=", auth.getName()});
        params.put("where", where);
        CarStoreSpecification<User> cSspecUser = new CarStoreSpecification<>();
        cSspecUser.setEntityManager(this.entityManager);
        Specification<User> specUser = cSspecUser.getSpec(params);
        List<User> users = this.repoUser.findAll(specUser);
        req.getSession().setAttribute("auth", users.size() == 1 ? users.get(0) : null);
        resp.sendRedirect(String.format("%s", req.getContextPath()));
    }
}
