package ru.job4j.models;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
/**
 * Класс Brand реализует сущность Брэнд.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-26
 * @since 2018-05-14
 */
@Entity
@Table(name = "brands")
public class Brand implements IModel {
    /**
     * Основатель брэнда.
     */
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "founder_id")
    private Founder founder;
    /**
     * Идентификатор брэнда.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Название брэнда.
     */
    @Column(name = "name")
    private String name;
    /**
     * Конструктор без параметров.
     */
    public Brand() {
    }
    /**
     * Конструктор.
     * @param founder основатель брэнда.
     * @param id идентификатор брэнда.
     * @param name название брэнда.
     */
    public Brand(Long id, String name, Founder founder) {
        this.founder = founder;
        this.id = id;
        this.name = name;
    }
    /**
     * Переопределяет метод equals().
     * @return true если объекты равны. Иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Brand brand = (Brand) o;
        return this.id.equals(brand.getId()) && this.name.equals(brand.getName()) && this.founder.equals(brand.getFounder());
    }
    /**
     * Получет основателя брэнда.
     * @return основателя брэнда.
     */
    public Founder getFounder() {
        return this.founder;
    }
    /**
     * Получет идентификатор брэнда.
     * @return нидентификатор брэнда.
     */
    public Long getId() {
        return this.id;
    }
    /**
     * Получет название брэнда.
     * @return название брэнда.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумма объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.founder, this.id, this.name);
    }
    /**
     * Устанавливает основателя брэнда.
     * @param founder основатель брэнда.
     */
    public void setFounder(Founder founder) {
        this.founder = founder;
    }
    /**
     * Устанавливает идентификатор брэнда.
     * @param id идентификатор брэнда.
     */
    public void setId(final Long id) {
        this.id = id;
    }
    /**
     * Устанавливает название брэнда.
     * @param name название брэнда.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Переопределяет метод toString().
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("Brand[id: %d, name: %s, founder: %s]", this.id, this.name, this.founder);
    }
}