package ru.job4j.addresses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Класс Profiles реализует сущность Анкеты.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-11
 * @since 2019-03-11
 */
public class Profiles {
    /**
     * Возвращает список адресов клиентов.
     * @param profiles список клиентов.
     * @return список адресов клиентов.
     */
    public List<Address> collect(List<Profile> profiles) {
        List<Address> list = new ArrayList<>();
        profiles.stream().map(a -> list.add(a.getAddress())).collect(Collectors.toList());
        return list;
    }
}
