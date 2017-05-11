package ru.job4j.list2map;

import java.util.HashMap;
import java.util.List;
/**
 * Класс UserConvert реализует функционал конвертации List в HashMap.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-11
 */
class UserConvert {
    /**
     * Конвертирует список пользователей в отображение и возвращает его.
     * @param list список пользователей.
     * @return отображение с пользователями.
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap hm = new HashMap();
        for (User user : list) {
            hm.put(user.getId(), user);
        }
        return hm;
    }
}