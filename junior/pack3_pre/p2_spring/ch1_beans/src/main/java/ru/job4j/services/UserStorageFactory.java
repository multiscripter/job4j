package ru.job4j.services;

/**
 * Класс UserStorageFactory реализует сущность Фабрика хранилищ пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-14
 * @since 2018-07-14
 */
public class UserStorageFactory {
    /**
     * Получает экземпляр хранилища.
     * @return экземпляр хранилища.
     */
    public UserStorage getUserStorageFile() {
        return new UserStorage(new StorageFile());
    }
}
