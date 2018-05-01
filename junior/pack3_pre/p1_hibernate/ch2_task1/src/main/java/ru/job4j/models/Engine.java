package ru.job4j.models;

import java.util.Objects;
/**
 * Класс Engine реализует сущность Двигатель.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-01
 * @since 2018-04-26
 */
public class Engine implements IModel {
    /**
     * Идентификатор двигателя.
     */
    private int id;
    /**
     * Название двигателя.
     */
    private String name;
    /**
     * Конструктор без параметров.
     */
    public Engine() {
    }
    /**
     * Конструктор.
     * @param id идентификатор двигателя.
     * @param name название двигателя.
     */
    public Engine(int id, String name) {
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
        Engine engine = (Engine) o;
        return this.id == engine.getId() && this.name.equals(engine.getName());
    }
    /**
     * Получет идентификатор двигателя.
     * @return нидентификатор двигателя.
     */
    public int getId() {
        return this.id;
    }
    /**
     * Получет название двигателя.
     * @return название двигателя.
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
     * Устанавливает идентификатор двигателя.
     * @param id идентификатор двигателя.
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * Устанавливает название двигателя.
     * @param name название двигателя.
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
        return String.format("Engine[id: %d, name: %s]", this.id, this.name);
    }
}