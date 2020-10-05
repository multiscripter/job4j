package ru.job4j.services;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.models.Offer;

/**
 * Класс OfferRepository реализует репозиторий.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-01
 * @since 2019-06-01
 */
@Repository
public interface OfferRepository extends CrudRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {
}
