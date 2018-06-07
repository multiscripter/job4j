package ru.job4j.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.hibernate.collection.internal.PersistentBag;
/**
 * Класс Car реализует сущность Модель автомобиля.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-14
 * @since 2018-04-26
 */
public class Car implements IModel {
    /**
     * Список типов кузова автомобиля.
     */
    private List<Body> bodies;
    /**
     * Брэнд.
     */
    private Brand brand;
    /**
     * Идентификатор автомобиля.
     */
    private int id;
    /**
     * Название автомобиля.
     */
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
        return this.id == car.getId() && this.name.equals(car.getName()) && this.bodies.equals(car.getBodies()) && this.brand.equals(car.getBrand());
    }
    /**
     * Получет список типов кузова автомобиля.
     * @return список типов кузова автомобиля.
     */
    public List<Body> getBodies() {
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
        return String.format("Car[id: %d, name: %s, bodies:[%s], brand: %s]", this.id, this.name, sbB.toString(), this.brand.toString());
    }
}