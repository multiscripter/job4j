package ru.job4j.services;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.models.Car;

/**
 * Класс CarRepository реализует репозиторий.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-10
 * @since 2019-06-01
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Long>, JpaSpecificationExecutor<Car> {
}
