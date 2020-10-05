package ru.job4j.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Класс EntityManagerFactoryConfig конфигурирует EntityManagerFactory.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-08-01
 */
@Configuration
@ComponentScan("ru.job4j")
public class EntityManagerFactoryConfig {
    /**
     * Источник данных.
     */
    @Autowired
    private DataSource ds;
    /**
     * Получает бин EntityManagerFactory.
     * @return бин EntityManagerFactory.
     */
    @Bean(name = "entityManagerFactory")
    LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(this.ds);
        emfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emfb.setPackagesToScan("ru.job4j.models");
        Properties jpaProps = new Properties();
        jpaProps.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        jpaProps.setProperty("generateDdl", "false");
        emfb.setJpaProperties(jpaProps);
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        emfb.setJpaVendorAdapter(adapter);
        return emfb;
    }
}
