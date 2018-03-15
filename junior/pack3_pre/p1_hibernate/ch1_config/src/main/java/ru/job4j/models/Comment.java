package ru.job4j.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.SimpleTimeZone;
/**
 * Класс Comment реализует сущность Комментарий.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-03-09
 * @since 2018-03-09
 */
public class Comment {
    /**
     * Текст комментария.
     */
    private String comment;
    /**
     * Дата создания.
     */
    private final GregorianCalendar created;
    /**
     * Идентификатор комментария.
     */
    private int id;
    /**
     * Идентификатор заявки.
     */
    private int itemId;
    /**
     * Идентификатор автора комментария.
     */
    private int userId;
    /**
     * Конструктор без параметров.
     */
    public Comment() {
        this.comment = "";
        SimpleTimeZone tz = new SimpleTimeZone(0, "GMT");
        tz.setID("GMT+00:00");
        this.created = new GregorianCalendar(tz);
        this.created.setTimeInMillis(0L);
        this.created.set(Calendar.MILLISECOND, 0);
    }
    /**
     * Конструктор.
     * @param id идентификатор комментария.
     * @param itemId идентификатор заявки.
     * @param userId идентификатор автора комментария.
     * @param comment текст комментария.
     * @param created дата создания комментария.
     */
    public Comment(final int id, final int itemId, final int userId, String comment, long created) {
        this.comment = comment;
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
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
        Comment cmt = (Comment) obj;
        return !(this.id != cmt.id || this.itemId != cmt.itemId || this.userId != cmt.userId || (this.comment == null ? cmt.comment != null : !this.comment.equals(cmt.comment)) || (this.created == null ? this.created != null : !this.created.equals(cmt.created)));
    }
    /**
     * Получет текст комментария.
     * @return текст комментария.
     */
    public String getComment() {
        return this.comment;
    }
    /**
     * Получет дату создания комментария.
     * @return дата создания комментария.
     */
    public Date getCreated() {
        return this.created.getTime();
    }
    /**
     * Получет идентификатор комментария.
     * @return идентификатор комментария.
     */
    public int getId() {
        return this.id;
    }
    /**
     * Получет идентификатор заявки.
     * @return идентификатор заявки.
     */
    public int getItemId() {
        return this.itemId;
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
        return Objects.hash(this.id, this.itemId, this.userId, this.comment, this.created);
    }
    /**
     * Проверяет объект на пустоту.
     * @return true если объект пустой, иначе false.
     */
    public boolean isEmpty() {
        return this.id == 0 && this.itemId == 0 && this.userId == 0 && this.comment.length() == 0;
    }
    /**
     * Устанавливает текст комментария.
     * @param comment текст комментария.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * Устанавливает дату комментария.
     * @param created дата комментария.
     */
    public void setCreated(Date created) {
        this.created.setTime(created);
    }
    /**
     * Устанавливает идентификатор комментария.
     * @param id идентификатор комментария.
     */
    public void setId(int id) {
    	this.id = id;
    }
    /**
     * Устанавливает идентификатор автора заявки.
     * @param itemId идентификатор автора заявки.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
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
        sb.append("comment[");
        sb.append("id: ");
        sb.append(this.getId());
        sb.append(", item id: ");
        sb.append(this.getItemId());
        sb.append(", user id: ");
        sb.append(this.getUserId());
        sb.append(", comment: ");
        sb.append(this.getComment());
        sb.append(", created: ");
        sb.append(this.getCreated());
        sb.append("]");
        return sb.toString();
    }
}