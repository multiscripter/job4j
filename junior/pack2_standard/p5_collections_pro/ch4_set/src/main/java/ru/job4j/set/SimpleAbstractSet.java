package ru.job4j.set;

import java.util.Iterator;
/**
 * Класс SimpleAbstractSet реализует абструктную сущность Множество.
 *
 * @param <E> обобщённый тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-01
 */
abstract class SimpleAbstractSet<E> extends SimpleAdstractCollection<E> implements ISimpleSet<E> {
    /**
     * Сравнивает текущую коллекцию с указанной.
     * @param o объект сравнения.
     * @return true если объекты равны. Иначе false.
     */
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o) {
            result = true;
        }
        return result;
    }
    /**
     * Вычисляет и возвращает суммарный хэш-код всех объектов множества.
     * @return суммарный хэш-код всех объектов множества.
     */
    public int hashCode() {
        int hash = 0;
        Iterator<E> iter = iterator();
        while (iter.hasNext()) {
            E obj = iter.next();
            if (obj != null) {
                hash += obj.hashCode();
            }
        }
        return hash;
    }
}