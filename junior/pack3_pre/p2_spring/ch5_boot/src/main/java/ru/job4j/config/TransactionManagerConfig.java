package ru.job4j.config;

import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 * Класс TransactionManagerConfig конфигурирует JpaTransactionManager.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-08-01
 * @since 2019-08-01
 */
@Configuration
@ComponentScan("ru.job4j")
public class TransactionManagerConfig {
    /**
     * Бин управляющего сущностями.
     */
    @Autowired
    private EntityManagerFactory emfb;
    /**
     * Получает управляющего транзакциями JPA.
     * @return управляющий транзакциями JPA.
     */
    @Bean(name = "transactionManager")
    public JpaTransactionManager getJpaTransactionManager() {
        return new JpaTransactionManager(this.emfb);
    }
}
