package ru.job4j.generic;

import java.util.Objects;
/**
 * Класс Base реализует обобщённый абстрактный класс.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-24
 */
abstract class Base implements Comparable<Base> {
    /**
     * Идентификатор объекта.
     */
    private String id;
    /**
     * Имя объекта.
     */
    private String name;
    /**
     * Получает идентификатор.
     * @return идентификатор.
     */
    public String getId() {
        return this.id;
    }
    /**
     * Получает имя.
     * @return имя.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Устанавливает идентификатор.
     * @param id идентификатор объекта.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Устанавливает имя объекта.
     * @param name имя объекта.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Сравнивает два объекта пользователя.
     * @param base объект сравнения.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(Base base) {
        return this.hashCode() - base.hashCode();
    }
    /**
     * Возвращает хэш-код объекта пользователя.
     * @return хэш-код объекта пользователя.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
    /**
     * Сравнивает объекты.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые, иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Base base = (Base) obj;
        if (!this.getName().equals(base.getName())) {
            return false;
        }
        return true;
    }
}