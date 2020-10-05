package ru.job4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Собирать командой: mvn clean package
 * Запускать командой: java -jar ./target/%локальное_имя_файла%.war
 * По умолчанию контекст приложения - это корень вэб-вервера.
 * Поэтому приложение открывается по адресу http://localhost:8080/
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    /**
     * Главный метод. Точка входа в приложение.
     * @param args массив аргументов запуска.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
