package ru.job4j.methref;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
/**
 * Класс UserConvert демострирует применение ссылок на методы.
 * Синтаксис:
 * Для статических методов:
 * имя_класса::имя_мутода
 * Для нестатических методов:
 * имя_переменной::имя_метода
 * @see https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-17
 * @since 2018-09-11
 */
public class UserConvert {
    /**
     * Создаёт и возвращает список объектов пользователей, над которыми произведена операция.
     * @param names список имён пользователей.
     * @param op операция.
     * @return список пользователей.
     */
    public List<User> factory(List<String> names, Function<String, User> op) {
        List<User> users = new ArrayList<>();
        names.forEach(
            n -> users.add(op.apply(n))
        );
        return users;
    }
}
