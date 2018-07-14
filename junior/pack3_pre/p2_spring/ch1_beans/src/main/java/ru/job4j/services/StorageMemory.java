package ru.job4j.services;

//import org.springframework.stereotype.Component;
import ru.job4j.models.User;
/**
 * Класс StorageMemory реализует сущность Хранилище в памяти.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-14
 * @since 2018-07-14
 */
//@Component
public class StorageMemory implements IStorage {
    /**
     * Добавляет пользователя в хранилище.
     * @param user пользователь.
     */
    public void add(User user) {
        System.err.println(String.format("%s added to memory.", user.toString()));
    }
}
