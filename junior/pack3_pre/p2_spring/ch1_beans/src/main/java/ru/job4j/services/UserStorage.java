package ru.job4j.services;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import ru.job4j.models.User;
/**
 * Класс UserStorage реализует сущность Хранилище пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-14
 * @since 2018-07-14
 */
//@Component
public class UserStorage {
    /**
     * Хранилище.
     */
    private final IStorage storage;
    /**
     * Конструктор.
     * @param storage хранилище.
     */
    //@Autowired
    public UserStorage(final IStorage storage) {
        this.storage = storage;
    }
    /**
     * Добавляет пользователя в хранилище.
     * @param user пользователь.
     */
    public void add(User user) {
        this.storage.add(user);
    }
}