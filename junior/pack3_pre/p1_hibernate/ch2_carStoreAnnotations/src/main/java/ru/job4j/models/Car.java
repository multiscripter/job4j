package ru.job4j.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.collection.internal.PersistentBag;
/**
 * Класс Car реализует сущность Модель автомобиля.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-16
 * @since 2018-04-26
 */
@Entity
@Table(name = "cars")
public class Car implements IModel {
    /**
     * Список типов кузова автомобиля.
     */
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cars_bodies",
            joinColumns = { @JoinColumn(name = "car_id") },
            inverseJoinColumns = { @JoinColumn(name = "body_id") }
    )
    private List<Body> bodies;
    /**
     * Брэнд.
     */
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    /**
     * Идентификатор автомобиля.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * Название автомобиля.
     */
    @Column(name = "name")
    private String name;
    /**
     * Конструктор без параметров.
     */
    public Car() {
    }
    /**
     * Конструктор.
     * @param id идентификатор автомобиля.
     * @param name название автомобиля.
     * @param bodies список типов кузова автомобиля.
     * @param brand брэнд.
     */
    public Car(int id, String name, List<Body> bodies, Brand brand) {
        this.bodies = bodies;
        this.brand = brand;
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
        Car car = (Car) o;
        return this.id == car.getId() && this.name.equals(car.getName()) && this.getBodies().equals(car.getBodies()) && this.brand.equals(car.getBrand());
    }
    /**
     * Получет список типов кузова автомобиля.
     * @return список типов кузова автомобиля.
     */
    public List<Body> getBodies() {
        if (this.bodies instanceof PersistentBag) {
            this.bodies = bodies.stream().collect(Collectors.toCollection(ArrayList::new));
        }
        return this.bodies;
    }
    /**
     * Получет брэнд.
     * @return брэнд.
     */
    public Brand getBrand() {
        return this.brand;
    }
    /**
     * Получет идентификатор автомобиля.
     * @return нидентификатор автомобиля.
     */
    public int getId() {
        return this.id;
    }
    /**
     * Получет название автомобиля.
     * @return название автомобиля.
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
        return Objects.hash(this.id, this.name, this.bodies, this.brand);
    }
    /**
     * Устанавливает список типов кузова автомобиля.
     * @param bodies список типов кузова автомобиля.
     */
    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
        if (bodies instanceof PersistentBag) {
            this.bodies = bodies.stream().collect(Collectors.toCollection(ArrayList::new));
        }
    }
    /**
     * Устанавливает брэнд.
     * @param brand брэнд.
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    /**
     * Устанавливает идентификатор автомобиля.
     * @param id идентификатор автомобиля.
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * Устанавливает название автомобиля.
     * @param name название автомобиля.
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
        StringBuilder sbB = new StringBuilder("");
        for (Body b : this.bodies) {
            sbB.append(b.toString());
        }
        return String.format("Car{id: %d, name: %s, bodies:[%s], brand: %s}", this.id, this.name, sbB.toString(), this.brand.toString());
    }
}