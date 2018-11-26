package ru.job4j.methref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
 * @version 2018-09-11
 * @since 2018-09-11
 */
public class UserConvert {
    /**
     * Класс User реализует сущность Пользователь.
     */
    public static class User {
        /**
         * Имя пользоваетля.
         */
        private final String name;
        /**
         * Конструктор.
         * @param name имя пользоваетля.
         */
        public User(String name) {
            this.name = name;
        }
        /**
         * Сравнивает объекты.
         * @param obj целевой объект, с которым сравнивается текущий объект.
         * @return true если объекты равны. Иначе false.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            User user = (User) obj;
            return this.name.equals(user.getName());
        }
        /**
         * Получает имя.
         * @return имя.
         */
        public String getName() {
            return this.name;
        }
        /**
         * Возвращает хэш-код.
         * @return хэш-код.
         */
        @Override
        public int hashCode() {
            return Objects.hash(this.name);
        }
        /**
         * Возвращает строковое представление объекта.
         * @return строковое представление объекта.
         */
        @Override
        public String toString() {
            return String.format("User{name: %s}", name);
        }
    }
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
    /**
     * Главный метод. Точка входа в приложение.
     * @param args массив параметров запуска.
     */
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Petr", "Nick", "Ban");
        UserConvert users = new UserConvert();
        List<User> data = users.factory(names, User::new);
        data.forEach(System.out::println);
    }
}
