package ru.job4j.tracker;

import java.util.Arrays;

/**
 * Class Tracker реализует сущность Трэкер заявок.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-18
 */
public class Tracker {
    /**
     * Массив заявок.
     */
    private Item[] items;
    /**
     * Идентификатор заявки.
     */
    private int id;
    /**
     * Ёмкость массив заявок.
     */
    private int capacity;
    /**
     * Указатель на первый пустой элемент массива.
     */
    private int pointer;
    /**
     * Конструктор без параметров.
     */
    public Tracker() {
        this.id = 1;
        this.capacity = 10;
        this.pointer = 0;
        this.items = new Item[this.capacity];
    }
    /**
     * Добавляет заявку.
     * @param item заявка.
     * @return добавленная заявка.
     */
    public Item add(Item item) {
        if (this.pointer == this.items.length) {
            this.items = this.increaseCapacity(this.items);
        }
        this.items[this.pointer++] = item;
        return item;
    }
    /**
     * Обновляет заявку.
     * @param uItem заявка.
     */
    public void update(Item uItem) {
        for (int a = 0; a < this.pointer; a++) {
            if (this.items[a].getId().equals(uItem.getId())) {
                items[a] = uItem;
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
        for (int a = 0; a < this.pointer; a++) {
            if (id.equals(items[a].getId())) {
                uItem = items[a];
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
        Item[] found = new Item[this.capacity];
        int index = 0;
        for (int a = 0; a < this.pointer; a++) {
            if (name.equals(items[a].getName())) {
                if (index == found.length) {
                    found = this.increaseCapacity(found);
                }
                found[index++] = this.items[a];
            }
        }
        return Arrays.copyOf(found, index);
    }
    /**
     * Удаляет заявку по идентификатору.
     * @param id идентификатор заявки.
     */
    public void delete(String id) {
        for (int a = 0; a < this.pointer; a++) {
            if (id.equals(this.items[a].getId())) {
                System.arraycopy((Object) this.items, a + 1, (Object) this.items, a, items.length - a - 1);
                break;
            }
        }
        this.pointer--;
    }
    /**
     * Получает все заявки.
     * @return массив заявок.
     */
    public Item[] getAll() {
        return Arrays.copyOf(this.items, this.pointer);
    }
    /**
     * Получает количество заявок.
     * @return количество заявок.
     */
    public int getLength() {
        return this.items.length;
    }
    /**
     * Увеличивает ёмкость массива.
     * @param items массив, ёмкость которого нужно увеличить.
     * @return увеличеснный массив.
     */
    private Item[] increaseCapacity(Item[] items) {
        return Arrays.copyOf(items, items.length + this.capacity);
    }
    /**
     * Генерирует идентификатор заявки.
     * @return идентификатор.
     */
    public String generateId() {
        return String.valueOf(this.id++);
    }
}
