package ru.job4j.config;

import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders
        .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration
        .EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
        .WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication
        .AuthenticationSuccessHandler;
import ru.job4j.services.UserDetailsServiceDAO;
import ru.job4j.utils.CustomBCryptPasswordEncoder;

/**
 * Класс SecurityConfig реализует конфигурацию Security.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-08-01
 */
@Configuration
@ComponentScan("ru.job4j")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * DAO UserDetailsService.
     */
    @Autowired
    private UserDetailsServiceDAO dao;
    /**
     * Источник данных.
     */
    @Autowired
    private DataSource ds;
    /**
     * Логгер.
     */
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    /**
     * Конфигурирует AuthenticationManagerBuilder.
     * @param auth объект AuthenticationManagerBuilder.
     * @throws Exception исключение.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new CustomBCryptPasswordEncoder("saltsaltsaltsalt".getBytes(), 8);
        auth.userDetailsService(this.dao).passwordEncoder(encoder);
    }
    /**
     * Конфигурирует HttpSecurity.
     * @param http объект HttpSecurity.
     * @throws Exception исключение.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/offer-create/", "/resources/fotos/**", "/resources/static/**")
                .permitAll()
                .antMatchers("/offer-delete/", "/offer-update/")
                .authenticated();
        http
                .formLogin()
                .loginPage("/login/")
                .usernameParameter("name")
                .passwordParameter("pass")
                .successHandler(this.getAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout/")
                .logoutSuccessUrl("/login/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
    /**
     * Конфигурирует WebSecurity.
     * @param web объект WebSecurity.
     * @throws Exception исключение.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Spring Security будет игнорировать URL-адреса, заканчивающиеся на .html
        web.ignoring().antMatchers("/*.html");
    }
    /**
     * Получает пользовательский обработчик успешного входа.
     * @return пользовательский обработчик успешного входа.
     */
    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return new CarStoreAuthenticationSuccessHandler();
    }
}
