package ru.job4j.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс PostgresDbConfig реализует конфигурацию источника данных PostgreSQL.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-08-01
 */
@Configuration
public class PostgresDbConfig {
    /**
     * Получает источник данных.
     * @return источник данных.
     * @throws Exception исключение.
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource dataSource() throws Exception {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/jpack3p4ch5task1");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgresrootpass");
        return dataSourceBuilder.build();
    }
}
