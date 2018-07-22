package ru.job4j.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.SimpleTimeZone;
/**
 * Class Item реализует сущность Заявка.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-21
 * @since 2017-04-18
 */
public class Item {
    /**
     * Дата создания.
     */
    private final GregorianCalendar created;
    /**
     * Описание заявки.
     */
    private String desc;
    /**
     * Идентификатор заявки.
     */
    private int id;
    /**
     * Имя заявки.
     */
    private String name;
    /**
     * Идентификатор автора заявки.
     */
    private int userId;
    /**
     * Конструктор без параметров.
     */
    public Item() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "GMT");
        tz.setID("GMT+00:00");
        this.created = new GregorianCalendar(tz);
        this.created.setTimeInMillis(0L);
        this.created.set(Calendar.MILLISECOND, 0);
    }
    /**
     * Конструктор.
     * @param id идентификатор.
     * @param userId идентификатор автора заявки.
     * @param name имя заявки.
     * @param desc описание заявки.
     * @param created дата создания.
     */
    public Item(final int id, final int userId, String name, String desc, long created) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.desc = desc;
        SimpleTimeZone tz = new SimpleTimeZone(0, "GMT");
        tz.setID("GMT+00:00");
        this.created = new GregorianCalendar(tz);
        this.created.set(Calendar.MILLISECOND, 0);
        if (created != 0) {
            this.created.setTimeInMillis(created);
        }
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
        return !(this.id != item.getId() || this.userId != item.getUserId() || (this.name == null ? item.getName() != null : !this.name.equals(item.getName())) || (this.desc == null ? item.getDesc() != null : !this.desc.equals(item.getDesc())) || (this.created == null ? item.getCreated() != null : !this.created.getTime().equals(item.getCreated())));
    }
    /**
     * Получет дата заявки.
     * @return дата заявки.
     */
    public Date getCreated() {
        return this.created.getTime();
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
    public int getId() {
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
     * Получет идентификатор автора комментария.
     * @return идентификатор автора комментария.
     */
    public int getUserId() {
        return this.userId;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумму.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.userId, this.name, this.desc, this.created);
    }
    /**
     * Проверяет объект на пустоту.
     * @return true если объект пустой, иначе false.
     */
    public boolean isEmpty() {
        return this.id == 0 && this.userId == 0 && this.name == null && this.desc == null;
    }
    /**
     * Устанавливает дата заявки.
     * @param created дата заявки.
     */
    public void setCreated(Date created) {
        this.created.setTime(created);
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
    public void setId(int id) {
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
     * Устанавливает идентификатор автора комментария.
     * @param userId идентификатор автора комментария.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * Переопределяет метод toString().
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("item[");
        sb.append("id: ");
        sb.append(this.id);
        sb.append(", user id: ");
        sb.append(this.userId);
        sb.append(", name: ");
        sb.append(this.name);
        sb.append(", description: ");
        sb.append(this.desc);
        sb.append(", created: ");
        sb.append(this.getCreated());
        sb.append("]");
        return sb.toString();
    }
}