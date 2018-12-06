package ru.job4j.control;

/**
 * Интерфейс IEntity реализует абстракцию Сущность.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-05
 */
public interface IEntity {
    /**
     * Возвращает имя сущности.
     * @return имя сущности.
     */
    String getName();
    /**
     * Возвращает позицию.
     * @return объект координат позициии на поле.
     */
    Position getPosition();
    /**
     * Возвращает короткое имя сущности.
     * @return короткое имя сущности.
     */
    String getSname();
}