package ru.job4j.testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Класс Performance реализует функционал тестирования производительности операций над коллекцией: добавление и удаление первых n элементов.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-10
 */
class Performance {
    /**
     * Добавляет элемент в коллекцию определённое количество раз.
     * @param collection коллекция, в которую производится добавление.
     * @param line добавляемый элемент.
     * @param amount количество добавляемых элементов.
     * @return время, затраченное на вставку.
     */
    public long add(Collection<String> collection, String line, int amount) {
        long start = System.nanoTime();
        for (; amount > 0; amount--) {
            collection.add(line);
        }
        return System.nanoTime() - start;
    }
    /**
     * Удаляет из коллекции определённое количество элементов.
     * @param collection коллекция, из которой удаляются эдементы.
     * @param line удаляемый элемент.
     * @param amount количество удаляемых эдементов.
     * @return время, затраченное на удаление.
     */
    public long delete(Collection<String> collection, String line, int amount) {
        long start = System.nanoTime();
        for (; amount > 0; amount--) {
            collection.remove(line);
        }
        return System.nanoTime() - start;
    }
    /**
     * Точка входа в программу.
     * @param args массив аргументов с параметрами запуска.
     */
    public static void main(String[] args) {
        Performance p = new Performance();
        String str = "CtFzJhnPCSiLlGeClAnBGQHwNJmQEjYT";
        long timeAdd, timeDel;
        ArrayList<String> al = new ArrayList<>();
        timeAdd = p.add(al, str, 100);
        timeDel = p.delete(al, str, 100);
        System.out.println(String.format("ArrayList.  add: %6d, del: %6d nanoseconds.", timeAdd, timeDel));
        LinkedList<String> ll = new LinkedList<>();
        timeAdd = p.add(ll, str, 100);
        timeDel = p.delete(ll, str, 100);
        System.out.println(String.format("LinkedList. add: %6d, del: %6d nanoseconds.", timeAdd, timeDel));
        TreeSet<String> ts = new TreeSet<>();
        timeAdd = p.add(ts, str, 100);
        timeDel = p.delete(ts, str, 100);
        System.out.println(String.format("TreeSet.    add: %6d, del: %6d nanoseconds.", timeAdd, timeDel));
    }
}