package ru.job4j.tracker;

import java.util.Objects;
/**
 * Класс Item реализует сущность Заявка.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-19
 * @since 2017-04-18
 */
public class Item {
    /**
     * Идентификатор заявки.
     */
    private String id;
    /**
     * Имя заявки.
     */
    private String name;
    /**
     * Описание заявки.
     */
    private String desc;
    /**
     * Время заявки в формате Unix-timestamp.
     */
    private long created;
    /**
     * Конструктор без параметров.
     */
    public Item() {
    }
    /**
     * Конструктор.
     * @param name имя заявки.
     * @param desc описание заявки.
     */
    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.created = System.currentTimeMillis();
    }
    /**
     * Получет время заявки.
     * @return время заявки.
     */
    public long getCreated() {
        return this.created;
    }
    /**
     * Получет описание заявки.
     * @return описание заявки.
     */
    public String getDesc() {
        return this.desc;
    }
    /**
     * Получет идентификатор заявки.
     * @return нидентификатор заявки.
     */
    public String getId() {
        return this.id;
    }
    /**
     * Получет имя заявки.
     * @return имя заявки.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Переопределяет метод equals().
     * @return true если объект пустой, иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Item item = (Item) obj;
        return this.id.equals(item.getId()) && this.name.equals(item.getName()) && this.desc.equals(item.getDesc()) && this.created == item.created;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумму.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.desc, this.created);
    }
    /**
     * Проверяет объект на пустоту.
     * @return true если объект пустой, иначе false.
     */
    public boolean isEmpty() {
        return this.id == null && this.name == null && this.desc == null && this.created == 0L;
    }
    /**
     * Устанавливает время заявки.
     * @param created время заявки.
     */
    public void setCreated(long created) {
    	this.created = created;
    }
    /**
     * Устанавливает описание заявки.
     * @param desc описание заявки.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * Устанавливает идентификатор заявки.
     * @param id идентификатор заявки.
     */
    public void setId(String id) {
    	this.id = id;
    }
    /**
     * Устанавливает имя заявки.
     * @param name имя.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Переопределяет метод toString().
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("id: %s, user name: %s, description: %s, created: %d", this.id, this.name, this.desc, this.created);
    }
}
