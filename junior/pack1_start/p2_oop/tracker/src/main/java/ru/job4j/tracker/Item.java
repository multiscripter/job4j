package ru.job4j.tracker;

/**
 * Class Item реализует сущность Заявка.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
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
    private String[] comments;
    /**
     * Конструктор без параметров.
     */
    public Item() {
    }
    /**
     * Конструктор.
     * @param id идентификатор заявки.
     * @param name имя заявки.
     * @param desc описание заявки.
     */
    public Item(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.created = System.currentTimeMillis();
        this.comments = new String[10];
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
     * Устанавливает имя заявки.
     * @param name имя.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Получет описание заявки.
     * @return описание заявки.
     */
    public String getDesc() {
        return this.desc;
    }
    /**
     * Устанавливает описание заявки.
     * @param desc описание заявки.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * Получет время заявки.
     * @return время заявки.
     */
    public long getCreated() {
        return this.created;
    }
    /**
     * Получет комментарии заявки.
     * @return комментарии заявки.
     */
    public String[] getComments() {
        return this.comments;
    }
    /**
     * Проверяет объект на пустоту.
     * @return true если объект пустой, иначе false.
     */
    public boolean isEmpty() {
        return this.id == null && this.name == null && this.desc == null && this.created == 0L;
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
