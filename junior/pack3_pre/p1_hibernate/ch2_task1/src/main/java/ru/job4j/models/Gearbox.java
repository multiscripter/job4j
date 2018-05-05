package ru.job4j.models;

import java.util.Objects;
/**
 * Класс Gearbox реализует сущность Коробка передач.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-05-01
 * @since 2018-04-26
 */
public class Gearbox implements IModel {
    /**
     * Идентификатор коробки передач.
     */
    private int id;
    /**
     * Название коробки передач.
     */
    private String name;
    /**
     * Конструктор без параметров.
     */
    public Gearbox() {
    }
    /**
     * Конструктор.
     * @param id идентификатор коробки передач.
     * @param name название коробки передач.
     */
    public Gearbox(int id, String name) {
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
        Gearbox gearbox = (Gearbox) o;
        return this.id == gearbox.getId() && this.name.equals(gearbox.getName());
    }
    /**
     * Получет идентификатор коробки передач.
     * @return нидентификатор коробки передач.
     */
    public int getId() {
        return this.id;
    }
    /**
     * Получет название коробки передач.
     * @return название коробки передач.
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
     * Устанавливает идентификатор коробки передач.
     * @param id идентификатор коробки передач.
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * Устанавливает название коробки передач.
     * @param name название коробки передач.
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
        return String.format("Gearbox[id: %d, name: %s]", this.id, this.name);
    }
}