package ru.job4j.control;

import java.util.TreeMap;
import java.util.Iterator;
/**
 * Класс Books реализует сущность Перечень книг с заказами.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-07-02
 */
 class Books implements Iterable<Book> {
    /**
     * Карта с перечнем книг.
     */
    private TreeMap<String, Book> books;
    /**
     * Конструктор.
     */
    Books() {
        this.books = new TreeMap<>();
    }
    /**
     * Добавляет книгу с заказами в перечень книг.
     * @param name название книги с заказами.
     * @param book объект книги с заказами.
     */
    public void add(String name, Book book) {
        this.books.put(name, book);
    }
    /**
     * Получает книгу с заказами по названию.
     * @param name название книги с заказами.
     * @return объект книги с заказами.
     */
    public Book get(String name) {
        return this.books.get(name);
    }
    /**
     * Возвращает итератор по книгам с заказами.
     * @return итератор по книгам с заказами.
     */
    public Iterator<Book> iterator() {
        return this.books.values().iterator();
    }
    /**
     * Возвращает количество книг с заказами.
     * @return количество книг с заказами.
     */
    public int size() {
        return this.books.size();
    }
 }
