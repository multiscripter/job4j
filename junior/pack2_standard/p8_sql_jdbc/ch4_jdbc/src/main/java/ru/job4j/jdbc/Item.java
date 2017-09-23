package ru.job4j.jdbc;

import java.util.ArrayList;
/**
 * Class Item реализует сущность Заявка.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
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
     * Массив комментариев заявки.
     */
    private ArrayList<String> comments;
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
        this.comments = new ArrayList<>();
    }
    /**
     * Получет комментарии заявки.
     * @return комментарии заявки.
     */
    public String[] getComments() {
        return this.comments.toArray(new String[this.comments.size()]);
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
        if (this.id != item.id || this.name != item.name || this.desc != item.desc || this.created != item.created) {
            return false;
        }
        return true;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумму.
     */
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.desc == null) ? 0 : this.desc.hashCode());
        result = prime * result + String.valueOf(this.created).hashCode();
        return result;
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
        StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(this.getId());
        sb.append(", user name: ");
        sb.append(this.getName());
        sb.append(", description: ");
        sb.append(this.getDesc());
        sb.append(", created: ");
        sb.append(this.getCreated());
        return sb.toString();
    }
}
