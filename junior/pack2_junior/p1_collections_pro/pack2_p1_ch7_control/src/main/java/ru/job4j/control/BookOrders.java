package ru.job4j.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
/**
 * Класс BookOrders реализует сущность Книжные заказы.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-06-29
 */
class BookOrders {
    /**
     * XML-парсер.
     */
    private XMLStreamReader parser;
    /**
     * Дерево заказов.
     */
    private Books books;
    /**
     * Конструктор.
     */
    BookOrders() {
        this.books = new Books();
    }
    /**
     * Загружает XML.
     * @param path имя xml-файла.
     * @throws FileNotFoundException исключение "Файл не найден".
     * @throws XMLStreamException исключение "XML-потока".
     */
    public void loadXML(String path) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream fis = new FileInputStream(path);
        this.parser = factory.createXMLStreamReader(fis);
    }
    /**
     * Парсит XML.
     * @throws XMLStreamException исключение "XML-потока".
     */
    public void parse() throws XMLStreamException {
        String name = "";
        int event;
        Book book;
        String orderId;
        while (this.parser.hasNext()) {
            event = this.parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                name = this.parser.getLocalName();
                if ("AddOrder".equals(name) || "DeleteOrder".equals(name)) {
                    String bookName = this.parser.getAttributeValue(null, "book");
                    book = this.books.get(bookName);
                    if (null == book) {
                        book = new Book(bookName);
                        this.books.add(bookName, book);
                    }
                    orderId = this.parser.getAttributeValue(null, "orderId");
                    if ("AddOrder".equals(name)) {
                        Double d = Double.parseDouble(this.parser.getAttributeValue(null, "price"));
                        book.add(orderId, new Order(orderId, this.parser.getAttributeValue(null, "operation"), d, Integer.parseInt(this.parser.getAttributeValue(null, "volume"))));
                    } else if ("DeleteOrder".equals(name)) {
                        book.remove(orderId);
                    }
                }
            }
        }
    }
    /**
     * Печатает список заказов из книг заказов.
     */
    public void print() {
        for (Book book : this.books) {
            System.out.println(String.format("Book: %s", book.getName()));
            System.out.println(String.format("%-14s   %-14s", "BID", "ASK"));
            System.out.println(String.format("%-14s   %-14s", "Volume@Price", "Volume@Price"));
            System.out.println("-------------------------------");
            System.out.println(book.gather());
        }
    }
    /**
     * Возвращает количество книг заказов.
     * @return количество книг заказов.
     */
    public int size() {
        return this.books.size();
    }
}

