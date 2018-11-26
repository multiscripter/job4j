package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
/**
 * Класс Directory реализует сущность Справочник.
 *
 * @param <K> тип ключа.
 * @param <V> тип значения.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-06-15
 */
class Directory<K, V> {
    /**
     * Массив пар "Ключ-значение".
     */
    private Pair<K, V>[] table;
    /**
     * Количество элементов в cправочнике.
     */
    private int count = 0;
    /**
     * Конструктор без параметров.
     */
    Directory() {
        this.table = new Pair[10];
    }
    /**
     * Конструктор.
     * @param capacity ёмкость cправочника.
     */
    Directory(int capacity) {
        this.table = new Pair[capacity];
    }
    /**
     * Удаляет значение по ключу из справочника.
     * @param key ключ.
     * @return true в случае успешного удаления. Иначе false.
     */
    public boolean delete(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        boolean result = false;
        int index = this.index(key);
        if (this.table[index] != null) {
            this.table[index] = null;
            result = true;
            this.count--;
        }
        return result;
    }
    /**
     * Получает значение по ключу из справочника.
     * @param key ключ.
     * @return значение.
     */
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return this.table[this.index(key)].getValue();
    }
    /**
     * Вычисляет индекс для ключа.
     * @param key ключ.
     * @return индекс для ключа.
     */
    private int index(K key) {
        int hash = this.hash(key);
        return (hash & 0x7FFFFFFF) % this.table.length;
    }
    /**
     * Вставляет ключ и значение в Справочник.
     * @param key ключ.
     * @param value значение.
     * @return true в случае успешной вставки. Иначе false.
     */
    public boolean insert(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        boolean result = false;
        int index = this.index(key);
        if (this.table[index] == null) {
            this.table[index] = new Pair(key, value);
            result = true;
            this.count++;
        }
        return result;
    }
    /**
     * Получает объект итератора.
     * @return объект итератора.
     */
    public Iterator iterator() {
        return new SimpleIterator();
    }
    /**
     * Возвращает хэш-код ключа.
     * @param key ключ.
     * @return хэш-код ключа.
     */
    private int hash(K key) {
        return Objects.hash(key);
    }
    /**
     * Возвращает количество элементов в справочнике.
     * @return количество элементов в справочнике.
     */
    public int size() {
        return this.count;
    }
    /**
     * Класс Pair реализует сущность Пара "Ключ-значение".
     *
     * @param <K> тип ключа.
     * @param <V> тип значения.
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-06-15
     */
    class Pair<K, V> {
        /**
         * Ключ.
         */
        private final K key;
        /**
         * Значение.
         */
        private final V value;
        /**
         * Конструктор.
         * @param key ключ.
         * @param value значение.
         */
        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        /**
         * Получает ключ.
         * @return ключ.
         */
        public K getKey() {
            return this.key;
        }
        /**
         * Получает значение.
         * @return значение.
         */
        public V getValue() {
            return this.value;
        }
    }
    /**
     * Класс SimpleIterator реализует сущность Итератор.
     *
     * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-06-15
     */
    private class SimpleIterator implements Iterator {
        /**
         * Текущий индекс итератора.
         */
        private int index = 0;
        /**
         * Получает индекс следующего элемента.
         * @return индекс следующего элемента.
         */
        private int getNextIndex() {
            int i = this.index + 1;
            while (i < Directory.this.table.length) {
                if (Directory.this.table[i] != null) {
                    break;
                }
                i++;
            }
            return i;
        }
        /**
         * Проверяет существование следующего элемента.
         * @return true если следующий элемент существует. Иначе false.
         */
        public boolean hasNext() {
            return this.getNextIndex() < Directory.this.size() ? true : false;
        }
        /**
         * Возвращает значение следующего элемента.
         * @return значение следующего элемента.
         */
        public V next() {
            int i = this.getNextIndex();
            if (i == Directory.this.size()) {
                throw new NoSuchElementException();
            }
            V result = Directory.this.table[this.index].getValue();
            this.index = i;
            return result;
        }
        /**
         * Удаляет текущий элемент.
         */
        public void remove() {
            Directory.this.delete(Directory.this.table[this.index].getKey());
        }
    }
}
