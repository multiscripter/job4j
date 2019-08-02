package ru.job4j.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.models.User;

/**
 *  Класс UserDetailsServiceDAO реализует DAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-07-30
 */
@Service
public class UserDetailsServiceDAO implements UserDetailsService {
    /**
     * entityManagerFactory.
     */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
    /**
     * Репозиторий пользователей.
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Загружает пользователя по имени.
     * @param username имя пользователя.
     * @return объект пользователя.
     * @throws UsernameNotFoundException исключение "Пользователь не найден".
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        CarStoreSpecification<User> usersSpec = new CarStoreSpecification<>();
        usersSpec.setEntityManager(this.entityManager);
        HashMap<String, List<String[]>> params = new HashMap<>();
        List<String[]> where = new ArrayList<>();
        where.add(new String[]{"users", "name", "=", username});
        params.put("where", where);
        Specification<User> userSpec = usersSpec.getSpec(params);
        User user = this.userRepository.findAll(userSpec).get(0);
        if (user.getName().equals("")) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return new UserDetailsBuilder(user);
    }
}
