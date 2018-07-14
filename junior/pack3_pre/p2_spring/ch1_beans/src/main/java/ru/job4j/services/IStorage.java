package ru.job4j.services;

import ru.job4j.models.User;
/**
 * IStorage объявляет интерфейс Хранилище.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-14
 * @since 2018-07-14
 */
public interface IStorage {
    /**
     * Добавляет пользователя в хранилище.
     * @param user пользователь.
     */
    void add(User user);
}