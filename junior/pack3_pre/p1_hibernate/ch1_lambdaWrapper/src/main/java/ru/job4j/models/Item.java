package ru.job4j.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
/**
 * Класс Item реализует сущность Элемент TODO-листа.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-12
 * @since 2018-03-20
 */
public class Item {
    /**
     * Время создания элемента.
     */
    private final GregorianCalendar created;
    /**
     * Описание элемента.
     */
    private String descr;
    /**
     * Состояние элемента (выполнен/невыполнен).
     */
    private boolean done;
    /**
     * Идентификатор элемента.
     */
    private int id;
    /**
     * Заголовок элемента.
     */
    private String item;
    /**
     * Конструктор без параметров.
     */
    public Item() {
        this.created = new GregorianCalendar();
        this.created.setTimeInMillis(0L);
        this.created.set(Calendar.MILLISECOND, 0);
    }
    /**
     * Конструктор.
     * @param id идентификатор элемента.
     * @param item заголовок элемента.
     * @param descr описание элемента.
     * @param created дата создания.
     * @param done состояние элемента.
     */
    public Item(final int id, String item, String descr, final long created, final boolean done) {
        this.id = id;
        this.item = item;
        this.descr = descr;
        this.done = done;
        this.created = new GregorianCalendar();
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
        Item o = (Item) obj;
        return !(this.id != o.getId() || (this.item == null ? o.getItem() != null : !this.item.equals(o.getItem())) || (this.descr == null ? o.getDescr() != null : !this.descr.equals(o.getDescr())) || (this.created != null && !this.getCreated().equals(o.getCreated())) || this.done != o.getDone());
    }
    /**
     * Получет дата заявки.
     * @return дата заявки.
     */
    public Date getCreated() {
        return this.created.getTime();
    }
    /**
	 * Получает строковое представление даты создания.
	 * @return строковое представление даты создания.
	 */
	public String getCreatedStr() {
        return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", this.created);
	}
    /**
     * Получает описание элемента.
     * @return описание элемента.
     */
    public String getDescr() {
        return this.descr;
    }
    /**
     * Получает состояние элемента.
     * @return состояние элемента.
     */
    public boolean getDone() {
        return this.done;
    }
    /**
     * Получает идентификатор элемента.
     * @return идентификатор элемента.
     */
    public int getId() {
        return this.id;
    }
    /**
     * Получает заголовок элемента.
     * @return заголовок элемента.
     */
    public String getItem() {
        return this.item;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумму.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.item, this.descr, this.created, this.done);
    }
    /**
     * Устанавливает дата заявки.
     * @param created дата заявки.
     */
    public void setCreated(final Date created) {
        this.created.setTimeInMillis((long) Math.floor(created.getTime() / 1000) * 1000);
    }
    /**
     * Устанавливает описание элемента.
     * @param descr описание элемента.
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }
    /**
     * Устанавливает состояние элемента.
     * @param done состояние элемента.
     */
    public void setDone(final boolean done) {
        this.done = done;
    }
    /**
     * Устанавливает идентификатор элемента.
     * @param id идентификатор элемента.
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * Устанавливает заголовок элемента.
     * @param item заголовок элемента.
     */
    public void setItem(String item) {
        this.item = item;
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
        sb.append(", item: ");
        sb.append(this.item);
        sb.append(", description: ");
        sb.append(this.descr);
        sb.append(", created: ");
        sb.append(this.getCreatedStr());
        sb.append(", done: ");
        sb.append(this.done);
        sb.append("]");
        return sb.toString();
    }
}