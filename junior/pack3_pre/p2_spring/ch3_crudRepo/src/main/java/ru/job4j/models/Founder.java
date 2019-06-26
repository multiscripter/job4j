package ru.job4j.models;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Класс Founder реализует сущность Основатель брэнда.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-26
 * @since 2018-05-14
 */
@Entity
@Table(name = "founders")
public class Founder implements IModel {
    /**
     * Идентификатор основателя брэнда.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Имя основателя брэнда.
     */
    @Column(name = "name")
    private String name;
    /**
     * Фамилия основателя брэнда.
     */
    @Column(name = "name_last")
    private String nameLast;
    /**
     * Конструктор без параметров.
     */
    public Founder() {
    }
    /**
     * Конструктор.
     * @param id идентификатор основателя брэнда.
     * @param nameLast фамилия основателя брэнда.
     * @param name имя основателя брэнда.
     */
    public Founder(Long id, String nameLast, String name) {
        this.id = id;
        this.name = name;
        this.nameLast = nameLast;
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
        Founder founder = (Founder) o;
        return this.id.equals(founder.getId()) && this.name.equals(founder.getName()) && this.nameLast.equals(founder.getNameLast());
    }
    /**
     * Получет идентификатор кузова.
     * @return нидентификатор кузова.
     */
    public Long getId() {
        return this.id;
    }
    /**
     * Получет имя основателя брэнда.
     * @return имя основателя брэнда.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Получет фамилию основателя брэнда.
     * @return фамилию основателя брэнда.
     */
    public String getNameLast() {
        return this.nameLast;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумма объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.nameLast);
    }
    /**
     * Устанавливает идентификатор кузова.
     * @param id идентификатор кузова.
     */
    public void setId(final Long id) {
        this.id = id;
    }
    /**
     * Устанавливает имя основателя брэнда.
     * @param name имя основателя брэнда.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Устанавливает фамилию основателя брэнда.
     * @param nameLast фамилию основателя брэнда.
     */
    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }
    /**
     * Переопределяет метод toString().
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("Founder[id: %d, nameLast: %s, name: %s]", this.id, this.nameLast, this.name);
    }
}