package ru.job4j.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.collection.internal.PersistentBag;
/**
 * Класс Car реализует сущность Автомобиль.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-01
 * @since 2018-04-26
 */
public class Car implements IModel {
    /**
     * Список типов кузова автомобиля.
     */
    private List<Body> bodies;
    /**
     * Список моделей двигателя автомобиля.
     */
    private List<Engine> engines;
    /**
     * Список типов коробки передач автомобиля.
     */
    private List<Gearbox> gearboxes;
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
     * @param engines список моделей двигателя автомобиля.
     * @param gearboxes список типов коробки передач автомобиля.
     */
    public Car(int id, String name, List<Body> bodies, List<Engine> engines, List<Gearbox> gearboxes) {
        this.bodies = bodies;
        this.engines = engines;
        this.gearboxes = gearboxes;
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
        return this.id == car.getId() && this.name.equals(car.getName()) && this.bodies.equals(car.getBodies()) && this.engines.equals(car.getEngines()) && this.gearboxes.equals(car.getGearboxes());
    }
    /**
     * Получет список типов кузова автомобиля.
     * @return список типов кузова автомобиля.
     */
    public List<Body> getBodies() {
        return bodies;
    }
    /**
     * Получет список моделей двигателя автомобиля.
     * @return список моделей двигателя автомобиля.
     */
    public List<Engine> getEngines() {
        return engines;
    }
    /**
     * Получет список типов коробки передач автомобиля.
     * @return список типов коробки передач автомобиля.
     */
    public List<Gearbox> getGearboxes() {
        return gearboxes;
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
        return Objects.hash(this.id, this.name, this.bodies, this.engines, this.gearboxes);
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
     * Устанавливает список моделей двигателя автомобиля.
     * @param engines список моделей двигателя автомобиля.
     */
    public void setEngines(List<Engine> engines) {
        this.engines = engines;
        if (engines instanceof PersistentBag) {
            this.engines = engines.stream().collect(Collectors.toCollection(ArrayList::new));
        }
    }
    /**
     * Устанавливает список типов коробки передач автомобиля.
     * @param gearboxes список типов коробки передач автомобиля.
     */
    public void setGearboxes(List<Gearbox> gearboxes) {
        this.gearboxes = gearboxes;
        if (gearboxes instanceof PersistentBag) {
            this.gearboxes = gearboxes.stream().collect(Collectors.toCollection(ArrayList::new));
        }
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
        StringBuilder sbE = new StringBuilder("");
        for (Engine e : this.engines) {
            sbE.append(e.toString());
        }
        StringBuilder sbG = new StringBuilder("");
        for (Gearbox g : this.gearboxes) {
            sbG.append(g.toString());
        }
        return String.format("Car{id: %d, name: %s, bodies:[%s], engines:[%s], gearboxes:[%s]}", this.id, this.name, sbB.toString(), sbE.toString(), sbG.toString());
    }
}