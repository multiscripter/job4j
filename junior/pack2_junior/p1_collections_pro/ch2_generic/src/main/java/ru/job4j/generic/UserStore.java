package ru.job4j.generic;
/**
 * Класс UserStore реализует сущность Хранилище пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-26
 */
class UserStore extends Store<User> {
    /**
     * Конструктор без параметров.
     */
    UserStore() {
        super();
    }
    /**
     * Конструктор.
     * @param size размер хранилища.
     */
    UserStore(int size) {
        super(size);
    }
}