package ru.job4j.services;

import ru.job4j.models.User;
/**
 * Класс StorageJDBC реализует сущность Хранилище JDBC.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-14
 * @since 2018-07-14
 */
public class StorageJDBC implements IStorage {
    /**
     * Закрытый конструктор.
     */
    private StorageJDBC() {
    }
    /**
     * Получает экземпляр хранилища.
     * @return экземпляр хранилища.
     */
    public static UserStorage getInstance() {
        return new UserStorage(new StorageJDBC());
    }
    /**
     * Добавляет пользователя в хранилище.
     * @param user пользователь.
     */
    public void add(User user) {
        System.err.println(String.format("%s added to DBMS.", user.toString()));
    }
}
