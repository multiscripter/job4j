package ru.job4j.services;

import ru.job4j.models.User;
/**
 * Класс StorageFile реализует сущность Хранилище в файле.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-14
 * @since 2018-07-14
 */
public class StorageFile implements IStorage {
    /**
     * Добавляет пользователя в хранилище.
     * @param user пользователь.
     */
    public void add(User user) {
        System.err.println(String.format("%s added to file.", user.toString()));
    }
}
