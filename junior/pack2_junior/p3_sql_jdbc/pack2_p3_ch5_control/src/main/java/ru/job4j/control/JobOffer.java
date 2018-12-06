package ru.job4j.control;

import java.util.Objects;
/**
 * Класс JobOffer реализует сущность Объявление о вакансии.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-27
 */
class JobOffer {
    /**
     * Автор объявления.
     */
    private final String author;
    /**
     * Дата публикации объявления.
     */
    private final long date;
    /**
     * Текст объявления.
     */
    private final String text;
    /**
     * Заголовок темы объявления.
     */
    private final String title;
    /**
     * URL темы объявления.
     */
    private final String url;
    /**
     * Конструктор.
     * @param author автор объявления.
     * @param date дата публикации объявления.
     * @param text текст объявления.
     * @param title заголовок темы объявления.
     * @param url темы объявления.
     */
    JobOffer(String author, long date, String text, String title, String url) {
        this.author = author;
        this.date = date;
        this.text = text;
        this.title = title;
        this.url = url;
    }
    /**
     * Сравнивает объекты.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты равны. Иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        JobOffer jo = (JobOffer) obj;
        if (!this.author.equals(jo.getAuthor()) || this.date != jo.getDate() || !this.text.equals(jo.getText()) || !this.title.equals(jo.getTitle()) || !this.url.equals(jo.getUrl())) {
            return false;
        }
        return true;
    }
    /**
     * Получает автора объявления.
     * @return автор объявления.
     */
    public String getAuthor() {
        return this.author;
    }
    /**
     * Получает дату публикации объявления.
     * @return дата публикации объявления.
     */
    public long getDate() {
        return this.date;
    }
    /**
     * Получает текст объявления.
     * @return текст объявления.
     */
    public String getText() {
        return this.text;
    }
    /**
     * Получает заголовок темы объявления.
     * @return заголовок темы объявления.
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * Получает URL темы объявления.
     * @return URL темы объявления.
     */
    public String getUrl() {
        return this.url;
    }
    /**
     * Возвращает хэш-код.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.author, this.date, this.title, this.url, this.text);
    }
    /**
     * Возвращает строковое представление объявления.
     * @return строковое представление объявления.
     */
    @Override
    public String toString() {
        return String.format("JobOffer{author: %s, date: %d, title: %s, url: %s}", this.author, this.date, this.title, this.url);
    }
}