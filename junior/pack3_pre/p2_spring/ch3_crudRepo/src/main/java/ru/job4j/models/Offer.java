package ru.job4j.models;

import java.util.HashMap;
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
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
/**
 * Класс Offer реализует сущность Объявление.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-26
 * @since 2018-05-10
 */
@Entity
@Table(name = "offers")
public class Offer implements IModel {
    /**
     * Кузов атомобиля.
     */
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "body_id")
    private Body body;
    /**
     * Модель автомобиля.
     */
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "car_id")
    private Car car;
    /**
     * Массив имён файлов изображений.
     * @Transient Указывает, что свойство или поле не являются постоянными.
     * https://docs.oracle.com/javaee/7/api/javax/persistence/Transient.html
     */
    @Transient
    private String[] fotos;
    /**
     * Идентификатор объявления.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;
    /**
     * Цена автомобиля.
     */
    @Column(name = "price")
    private int price;
    /**
     * Статус объявления (продано/не продано).
     */
    @Column(name = "status")
    private boolean status;
    /**
     * Идентификатор пользоваетля.
     */
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * Конструктор без параметров.
     */
    public Offer() {
    }
    /**
     * Конструктор.
     * @param id идентификатор объявления.
     * @param items карта объектов, реализующих IModel.
     * @param price цена автомобиля.
     * @param status статус объявления.
     */
    public Offer(final Long id, final HashMap<String, IModel> items, final int price, final boolean status) {
        this.body = (Body) items.get("body");
        this.car = (Car) items.get("car");
        this.id = id;
        this.price = price;
        this.status = status;
        this.user = (User) items.get("user");
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
        Offer offer = (Offer) o;
        return this.body.equals(offer.getBody()) && this.car.equals(offer.getCar()) &&  this.id.equals(offer.getId()) && this.price == offer.getPrice() && this.status == offer.getStatus() && this.user.equals(offer.getUser());
    }
    /**
     * Получет кузов.
     * @return кузов.
     */
    public Body getBody() {
        return this.body;
    }
    /**
     * Получет автомобиль.
     * @return автомобиль.
     */
    public Car getCar() {
        return this.car;
    }
    /**
     * Получает массив имён файлов изображений.
     * @return массив имён файлов изображений.
     */
    public String[] getFotos() {
        return this.fotos;
    }
    /**
     * Получет идентификатор объявления.
     * @return нидентификатор объявления.
     */
    public Long getId() {
        return this.id;
    }
    /**
     * Получет цену автомобиля.
     * @return цена автомобиля.
     */
    public int getPrice() {
        return this.price;
    }
    /**
     * Получет статус объявления.
     * @return статус объявления.
     */
    public boolean getStatus() {
        return this.status;
    }
    /**
     * Получет пользоваетля.
     * @return пользоваетля.
     */
    public User getUser() {
        return this.user;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумма объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.body, this.car, this.id, this.price, this.status, this.user);
    }
    /**
     * Устанавливает кузов.
     * @param body кузов.
     */
    public void setBody(Body body) {
        this.body = body;
    }
    /**
     * Устанавливает идентификатор автомобиля.
     * @param car автомобиль.
     */
    public void setCar(Car car) {
        this.car = car;
    }
    /**
     * Устанавливает массив имён файлов изображений.
     * @param fotos массив имён файлов изображений.
     */
    public void setFotos(String[] fotos) {
        this.fotos = fotos;
    }
    /**
     * Устанавливает идентификатор объявления.
     * @param id идентификатор объявления.
     */
    public void setId(final Long id) {
        this.id = id;
    }
    /**
     * Устанавливает цену автомобиля.
     * @param price цена автомобиля.
     */
    public void setPrice(final int price) {
        this.price = price;
    }
    /**
     * Устанавливает статус объявления.
     * @param status статус объявления.
     */
    public void setStatus(final boolean status) {
        this.status = status;
    }
    /**
     * Устанавливает пользоваетля.
     * @param user пользоваетля.
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * Переопределяет метод toString().
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Offer[");
        sb.append("id: ");
        sb.append(this.id);
        sb.append(", user: ");
        sb.append(this.user);
        sb.append(", car: ");
        sb.append(this.car);
        sb.append(", body: ");
        sb.append(this.body);
        sb.append(", price: ");
        sb.append(this.price);
        sb.append(", status: ");
        sb.append(this.status);
        sb.append("]");
        return sb.toString();
    }
}
