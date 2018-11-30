package ru.job4j.phonebook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Класс PhoneDictionary реализует сущность Телефонный справочник.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-30
 * @since 2018-11-30
 */
public class PhoneDictionary {
    /**
     * Список человек.
     */
    private final List<Person> persons = new ArrayList<>();
    /**
     * Добавляет человека в справочник.
     * @param person человека, добавляемый в справочник.
     * @return true в случае успешного добавления. Иначе false.
     */
    public boolean add(final Person person) {
        return this.persons.add(person);
    }
    /**
     * Вернуть список всех пользователей, который содержат key в любых полях.
     * @param key Ключ поиска.
     * @return Список подощедщих пользователей.
     */
    public List<Person> find(String key) {
        return this.persons.stream().filter(x -> x.getAddress().contains(key) || x.getName().contains(key) || x.getPhone().contains(key) || x.getSurname().contains(key)).collect(Collectors.toList());
    }
}
