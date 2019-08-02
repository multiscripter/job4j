package ru.job4j.services;

import javax.persistence.metamodel.EntityType;
import java.util.Objects;

/**
 * Класс Entry реализует сущность Вхождение.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-27
 * @since 2019-05-27
 */
public class Entry {
    /**
     * Имя атрибута.
     */
    private String attrName;
    /**
     * Тип связанной сущности.
     */
    private EntityType<?> child;
    /**
     * Тип основной сущности.
     */
    private EntityType<?> parent;
    /**
     * Конструктор.
     * @param parent тип основной сущности.
     * @param child тип связанной сущности.
     * @param attrName имя атрибута.
     */
    public Entry(EntityType<?> parent, EntityType<?> child, String attrName) {
        this.attrName = attrName;
        this.child = child;
        this.parent = parent;
    }
    /**
     * Получает имя атрибута.
     * @return имя атрибута.
     */
    public String getAttrName() {
        return this.attrName;
    }
    /**
     * Получает тип связанной сущности.
     * @return тип связанной сущности.
     */
    public EntityType<?> getChild() {
        return this.child;
    }
    /**
     * Получает тип сущности.
     * @return тип сущности.
     */
    public EntityType<?> getParent() {
        return this.parent;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумма объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.attrName, this.child, this.parent);
    }
    /**
     * Устанавливает имя атрибута.
     * @param attrName имя атрибута.
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    /**
     * Устанавливает тип связанной сущности.
     * @param child тип связанной сущности.
     */
    public void setChild(EntityType<?> child) {
        this.child = child;
    }
    /**
     * Устанавливает тип сущности.
     * @param parent тип сущности.
     */
    public void setParent(EntityType<?> parent) {
        this.parent = parent;
    }
    /**
     * Возвращает строковое представление.
     * @return строковое представление.
     */
    public String toString() {
        return String.format("%s{%s %s}", this.parent.getJavaType().getName(), this.child.getJavaType().getName(), this.attrName);
    }
}