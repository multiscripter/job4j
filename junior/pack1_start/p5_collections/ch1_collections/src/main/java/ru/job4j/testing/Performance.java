package ru.job4j.testing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;
import javax.xml.bind.DatatypeConverter;

/**
 * Класс Performance реализует функционал тестирования производительности операций над коллекцией: добавление и удаление первых n элементов.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-10
 */
class Performance {
    /**
     * Класс StringGenerator реализует функционал генерирования случайных строк.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-05-11
     */
    class StringGenerator {
        /**
         * Объект хэшера.
         */
        private MessageDigest md;
        /**
         * Списочный массив сгенерированных строк.
         */
        private ArrayList<String> strs;
        /**
         * Длина списочного массива сгенерированных строк.
         */
        private int amount;
        /**
         * Объект рандомизатора.
         */
        private Random rnd;
        /**
         * Конструктор.
         * @param amount количество генерируемых строк.
         */
        StringGenerator(int amount) {
            try {
                this.md = MessageDigest.getInstance("MD5");
                this.amount = amount;
                this.strs = new ArrayList<>(amount);
                this.rnd = new Random(System.currentTimeMillis());
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
        /**
         * Заполняет массив строками.
         */
        public void fill() {
            for (int a = this.amount; a > 0; a--) {
                int num = this.rnd.nextInt(Integer.MAX_VALUE - 1) + 1;
                md.update(String.valueOf(num).getBytes());
                byte[] digest = md.digest();
                String str = DatatypeConverter.printHexBinary(digest);
                this.strs.add(str);
            }
        }
        /**
         * Возвращает итератор.
         * @return итератор.
         */
        public Iterator getIterator() {
            return this.strs.iterator();
        }
    }
    /**
     * Добавляет элемент в коллекцию определённое количество раз.
     * @param collection коллекция, в которую производится добавление.
     * @param sg объект генератора строк.
     * @param amount количество добавляемых элементов.
     * @return время, затраченное на вставку.
     */
    public long add(Collection<String> collection, StringGenerator sg, int amount) {
        Iterator iter = sg.getIterator();
        long start = System.nanoTime();
        for (; amount > 0; amount--) {
            if (!iter.hasNext()) {
                break;
            }
            collection.add(iter.next().toString());
        }
        return System.nanoTime() - start;
    }
    /**
     * Удаляет из коллекции определённое количество элементов.
     * @param collection коллекция, из которой удаляются эдементы.
     * @param amount количество удаляемых эдементов.
     * @return время, затраченное на удаление.
     */
    public long delete(Collection<String> collection, int amount) {
        Iterator iter = collection.iterator();
        long start = System.nanoTime();
        for (; amount > 0; amount--) {
            if (!iter.hasNext()) {
                break;
            }
            iter.next();
            iter.remove();
        }
        return System.nanoTime() - start;
    }
    /**
     * Точка входа в программу. Принимает 1 параметр: число строк от 0 по 100000).
     * @param args массив параметров запуска.
     */
    public static void main(String[] args) {
        int amount;
        try {
            amount = Integer.valueOf(args[0]);
            amount = amount < 0 || amount > 1000000 ? 0 : amount;
        } catch (NumberFormatException ex) {
            amount = 0;
        }
        Performance p = new Performance();
        StringGenerator sg = p.new StringGenerator(amount);
        sg.fill();
        long timeAdd, timeDel;
        ArrayList<String> al = new ArrayList<>();
        timeAdd = p.add(al, sg, amount);
        timeDel = p.delete(al, amount);
        System.out.println(String.format("ArrayList.  add: %9d, del: %9d nanoseconds.", timeAdd, timeDel));
        LinkedList<String> ll = new LinkedList<>();
        timeAdd = p.add(ll, sg, amount);
        timeDel = p.delete(ll, amount);
        System.out.println(String.format("LinkedList. add: %9d, del: %9d nanoseconds.", timeAdd, timeDel));
        TreeSet<String> ts = new TreeSet<>();
        timeAdd = p.add(ts, sg, amount);
        timeDel = p.delete(ts, amount);
        System.out.println(String.format("TreeSet.    add: %9d, del: %9d nanoseconds.", timeAdd, timeDel));
    }
}