package ru.job4j.control;

/**
 * Интерфейс ICreature реализует абстракцию Существо.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-05
 */
public interface ICreature extends IEntity {
    /**
     * Возвращает позицию.
     * @param dir направление движения.
     * @return true если передвижение выполнено успешно. Иначе false.
     */
    boolean move(final Direction dir);
}