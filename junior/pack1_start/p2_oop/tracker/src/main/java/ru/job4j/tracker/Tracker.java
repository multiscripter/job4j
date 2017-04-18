package ru.job4j.tracker;

import java.util.Arrays;

/**
 * Class Tracker реализует сущность Трэкер заявок.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-18
 */
public class Tracker {
    /**
     * Массив заявок.
     */
    private Item[] items;
    /**
     * Конструктор без параметров.
     */
    public Tracker() {
        this.items = new Item[0];
    }
    /**
     * Добавляет заявку.
     * @param item заявка.
     * @return добавленная заявка.
     */
    public Item add(Item item) {
        this.items = Arrays.copyOf(this.items, this.items.length + 1);
        this.items[this.items.length - 1] = item;
        return item;
    }
    /**
     * Обновляет заявку.
     * @param uItem заявка.
     */
    public void update(Item uItem) {
        for (Item item : this.items) {
            if (item.getId() == uItem.getId()) {
                item = uItem;
                break;
            }
        }
    }
    /**
     * Ищет заявку по идентификатору.
     * @param id идентификатор заявки.
     * @return найденный или пустой объект заявки.
     */
    public Item findById(String id) {
        Item uItem = new Item();
        for (Item item : this.items) {
            if (item.getId() == id) {
                uItem = item;
                break;
            }
        }
        return uItem;
    }
    /**
     * Удаляет заявку по идентификатору.
     * @param id идентификатор заявки.
     */
    public void delete(String id) {
        Item[] items = new Item[this.items.length - 1];
        for (int a = 0; a < items.length; a++) {
            if (this.items[a].getId() != id) {
                items[a] = this.items[a];
            }
        }
        this.items = items;
    }
    /**
     * Получает все заявки.
     * @return массив заявок.
     */
    public Item[] getAll() {
        return this.items;
    }
    /**
     * Получает количество заявок.
     * @return количество заявок.
     */
    public int getLength() {
        return this.items.length;
    }
}