package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * Класс Tracker реализует сущность Трэкер заявок.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-10
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
        for (int a = 0, size = this.items.size(); a < size; a++) {
            if (this.items.get(a).getId().equals(uItem.getId())) {
                this.items.set(a, uItem);
                break;
            }
        }
    }
    /**
     * Ищет заявку по идентификатору.
     * Простая реализация.
     * @param id идентификатор заявки.
     * @return найденный или пустой объект заявки.
     *
    public Item findById(String id) {
        Item uItem = new Item();
        for (Item item : this.items) {
            if (id.equals(item.getId())) {
                uItem = item;
                break;
            }
        }
        return uItem;
    }*/
    /**
     * Ищет заявку по идентификатору.
     * Реализация с использованием лямбда.
     * @param id идентификатор заявки.
     * @return найденный или пустой объект заявки.
     *
    public Item findById(String id) {
        final Item[] uItem = {new Item()};
        Predicate<String> p = id::equals;
        this.items.forEach(x -> {
            if (p.test(x.getId())) {
                uItem[0] = x;
            }
        });
        return uItem[0];
    }*/
    /**
     * Ищет заявку по идентификатору.
     * Реализация с использованием Stream API.
     * @param id идентификатор заявки.
     * @return найденный или пустой объект заявки.
     */
    public Item findById(String id) {
        Predicate<String> p = id::equals;
        List<Item> found = this.items.stream().filter(x -> p.test(x.getId())).collect(Collectors.toList());
        return found.size() == 1 ? found.get(0) : new Item();
    }
    /**
     * Ищет заявку по имени.
     * Простая реализация.
     * @param name имя заявки.
     * @return массив найденных заявок или пустой массив.
     *
    public Item[] findByName(String name) {
        ArrayList<Item> found = new ArrayList<>();
        for (Item item : this.items) {
            if (name.equals(item.getName())) {
                found.add(item);
            }
        }
        return found.toArray(new Item[found.size()]);
    }*/
    /**
     * Ищет заявку по имени.
     * Реализация с использованием лямбда.
     * @param name имя заявки.
     * @return массив найденных заявок или пустой массив.
     *
    public Item[] findByName(String name) {
        ArrayList<Item> found = new ArrayList<>();
        Predicate<String> p = name::equals;
        this.items.forEach(x -> {
            if (p.test(x.getName())) {
                found.add(x);
            }
        });
        return found.toArray(new Item[found.size()]);
    }*/
    /**
     * Ищет заявку по имени.
     * Реализация с использованием Stream API.
     * @param name имя заявки.
     * @return массив найденных заявок или пустой массив.
     */
    public Item[] findByName(String name) {
        Predicate<String> p = name::equals;
        return this.items.stream().filter(x -> p.test(x.getName())).collect(Collectors.toList()).toArray(new Item[0]);
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
