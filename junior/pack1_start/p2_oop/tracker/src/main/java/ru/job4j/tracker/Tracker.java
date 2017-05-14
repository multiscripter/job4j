package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class Tracker реализует сущность Трэкер заявок.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 6
 * @since 2017-04-18
 */
public class Tracker {
    /**
     * Массив заявок.
     */
    private ArrayList<Item> items;
    /**
     * Идентификатор заявки.
     */
    private int id;
    /**
     * Ёмкость массива заявок.
     */
    private int capacity;
    /**
     * Конструктор без параметров.
     */
    public Tracker() {
        this.id = 1;
        this.items = new ArrayList<>();
    }
    /**
     * Добавляет заявку.
     * @param item заявка.
     * @return добавленная заявка.
     */
    public Item add(Item item) {
        this.items.add(item);
        return item;
    }
    /**
     * Обновляет заявку.
     * @param uItem заявка.
     */
    public void update(Item uItem) {
        int index = -1;
        for (Item item : this.items) {
            if (item.getId().equals(uItem.getId())) {
                index = this.items.indexOf(item);
                break;
            }
        }
        if (index != -1) {
            this.items.set(index, uItem);
        }
    }
    /**
     * Ищет заявку по идентификатору.
     * @param id идентификатор заявки.
     * @return найденный или пустой объект заявки.
     */
    public Item findById(String id) {
        Item uItem = new Item();
        Iterator<Item> iter = this.items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            if (id.equals(item.getId())) {
                uItem = item;
                break;
            }
        }
        return uItem;
    }
    /**
     * Ищет заявку по имени.
     * @param name имя заявки.
     * @return массив найденных заявок или пустой массив.
     */
    public Item[] findByName(String name) {
        ArrayList<Item> found = new ArrayList<>();
        Iterator<Item> iter = this.items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            if (name.equals(item.getName())) {
                found.add(item);
            }
        }
        return found.toArray(new Item[found.size()]);
    }
    /**
     * Удаляет заявку по идентификатору.
     * @param id идентификатор заявки.
     * @return true если заявка удалена, иначе false.
     */
    public boolean delete(String id) {
        boolean deleted = false;
        Iterator<Item> iter = this.items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            if (id.equals(item.getId())) {
                iter.remove();
                deleted = true;
                break;
            }
        }
        return deleted;
    }
    /**
     * Получает все заявки.
     * @return массив заявок.
     */
    public Item[] getAll() {
        return this.items.toArray(new Item[this.items.size()]);
    }
    /**
     * Получает количество заявок.
     * @return количество заявок.
     */
    public int getQuantity() {
        return this.items.size();
    }
    /**
     * Генерирует идентификатор заявки.
     * @return идентификатор.
     */
    public String generateId() {
        return String.valueOf(this.id++);
    }
}
