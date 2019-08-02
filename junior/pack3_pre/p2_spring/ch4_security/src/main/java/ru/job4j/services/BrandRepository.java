package ru.job4j.services;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.models.Brand;

/**
 * Класс BrandRepository реализует репозиторий.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-01
 * @since 2019-06-01
 */
@Repository
public interface BrandRepository extends CrudRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
}
