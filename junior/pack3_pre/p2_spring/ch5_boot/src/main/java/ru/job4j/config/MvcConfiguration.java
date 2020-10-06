package ru.job4j.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Класс MvcConfiguration реализует конфигурацию MVC.
 *
 * @author multiscripter (mailto:ill-jah@yandex.ru)
 * @version 2020-10-06
 * @since 2020-10-04
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("classpath:application.properties")
    private Resource appProps;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    // Обработка запросов статики (css, js и т.д.).
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Добавить обработчик для статически загружаемых ресурсов (например картинок).
        String path = "";
        try {
            path = this.appProps.getURI().toString();
            path = path.replaceFirst(".war!.+", "");
            path = path.replaceFirst(".+:(?<!\\/)", "");
            path = path + "/resources/";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:" + path);
    }
}