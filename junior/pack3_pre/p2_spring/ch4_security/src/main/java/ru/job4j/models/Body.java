package ru.job4j.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
/**
 * Класс Body реализует сущность Кузов.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-26
 * @since 2018-04-26
 */
@Entity
@Table(name = "bodies")
public class Body implements IModel {
    /**
     * Идентификатор кузова.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Название кузова.
     */
    @Column(name = "name")
    private String name;
    /**
     * Конструктор без параметров.
     */
    public Body() {
    }
    /**
     * Конструктор.
     * @param id идентификатор кузова.
     * @param name название кузова.
     */
    public Body(Long id, String name) {
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
        Body body = (Body) o;
        return this.id.equals(body.getId()) && this.name.equals(body.getName());
    }
    /**
     * Получет идентификатор кузова.
     * @return нидентификатор кузова.
     */
    public Long getId() {
        return this.id;
    }
    /**
     * Получет название кузова.
     * @return название кузова.
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
        return Objects.hash(this.id, this.name);
    }
    /**
     * Устанавливает идентификатор кузова.
     * @param id идентификатор кузова.
     */
    public void setId(final Long id) {
        this.id = id;
    }
    /**
     * Устанавливает название кузова.
     * @param name название кузова.
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
        return String.format("Body[id: %d, name: %s]", this.id, this.name);
    }
}