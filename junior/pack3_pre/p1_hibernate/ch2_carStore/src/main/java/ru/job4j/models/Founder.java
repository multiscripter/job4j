package ru.job4j.models;

import java.util.Objects;
/**
 * Класс Founder реализует сущность Основатель брэнда.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-14
 * @since 2018-05-14
 */
public class Founder implements IModel {
    /**
     * Идентификатор основателя брэнда.
     */
    private int id;
    /**
     * Имя основателя брэнда.
     */
    private String name;
    /**
     * Фамилия основателя брэнда.
     */
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
    public Founder(int id, String nameLast, String name) {
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
        return this.id == founder.getId() && this.name.equals(founder.getName()) && this.nameLast.equals(founder.getNameLast());
    }
    /**
     * Получет идентификатор кузова.
     * @return нидентификатор кузова.
     */
    public int getId() {
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
    public void setId(final int id) {
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