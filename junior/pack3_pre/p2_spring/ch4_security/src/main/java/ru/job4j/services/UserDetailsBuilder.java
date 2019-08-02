package ru.job4j.services;

import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.job4j.models.User;

/**
 *  Класс UserDetailsBuilder реализует UserDetails.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-07-30
 */
public class UserDetailsBuilder implements UserDetails {
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Пользователь.
     */
    private User user;
    /**
     * Конструктор.
     * @param user пользователь.
     */
    public UserDetailsBuilder(User user) {
        this.user = user;
    }
    /**
     * Получает коллекцию ролей пользователя.
     * @return коллекция ролей пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        this.logger.error("LOG: UserDetails.getAuthorities()");
        List<SimpleGrantedAuthority> auths = new java.util.ArrayList<>();
        auths.add(new SimpleGrantedAuthority("admin"));
        return auths;
    }
    /**
     * Получает хаш пароля пользователя.
     * @return хаш пароля пользователя.
     */
    @Override
    public String getPassword() {
        return this.user.getPass();
    }
    /**
     *  Получает имя пользователя.
     * @return имя пользователя.
     */
    @Override
    public String getUsername() {
        return this.user.getName();
    }
    /**
     * Проверяет аккаунт на истечение срока.
     * @return true если срок аккаунта не истёк. Иначе False.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Проверяет аккаунт на блокировку.
     * @return true если аккаунта не заблокирован. Иначе False.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Проверяет полномочия на истечение срока.
     * @return true если срок полномочий не истёк. Иначе False.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Проверяет активность аккаунт.
     * @return true если аккаунт активен. Иначе False.
     */
    @Override
    public boolean isEnabled() {
        return this.user.getActivity();
    }
}
