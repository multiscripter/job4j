package ru.job4j.nonblocking;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Objects;
/**
 * Класс Task реализует сущность "Задача".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-04
 */
class Task {
    /**
     * Счётчик объектов.
     */
    private static int counter = 1;
    /**
     * Описание задачи.
     */
    private String description;
    /**
     * Идентификатор задачи.
     */
    private int id;
    /**
     * Имя задачи.
     */
    private String name;
    /**
     * Версия.
     */
    private int version;
    /**
     * Список ревизий.
     */
    private ArrayList<String> revisions;
    /**
     * Объект блокировки.
     */
    private Object lock;
    /**
     * Конструктор.
     * @param name имя задачи.
     * @param description описание задачи.
     */
    Task(String name, String description) {
        this.lock = this;
        this.id = counter++;
        this.name = name;
        this.description = description;
        this.version = 1;
        this.revisions = new ArrayList<>();
        this.revisions.add(this.toString());
    }
    /**
     * Копирующий конструктор.
     * @param task объект задачи.
     */
    Task(Task task) {
        this.lock = this;
        this.id = task.id;
        this.name = task.name;
        this.description = task.description;
        this.version = task.version;
        this.revisions = new ArrayList<>(task.revisions);
    }
    /**
     * Переопределяет метод equals().
     * @return true если объекты равны. Иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        synchronized (this.lock) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            Task task = (Task) obj;
            if (!this.name.equals(task.getName()) || !this.description.equals(task.getDescription()) || this.version != task.getVersion()) {
                return false;
            }
            return true;
        }
    }
    /**
     * Возвращает хэш-код объекта задачи.
     * @return хэш-код объекта задачи.
     */
    @Override
    public int hashCode() {
        synchronized (this.lock) {
            return Objects.hash(this.name, this.description, this.version);
        }
    }
    /**
     * Получает описание задачи.
     * @return описание задачи.
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Получает идентификатор задачи.
     * @return идентификатор задачи.
     */
    public int getId() {
        return this.id;
    }
    /**
     * Получает имя задачи.
     * @return имя задачи.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Устанавливает описание задачи.
     * @param description описание задачи.
     */
    public void setDescription(String description) {
        synchronized (this.lock) {
            if (!this.description.equals(description)) {
                this.description = description;
                int version = this.version++;
                if (version == this.revisions.size()) {
                    this.revisions.add(this.toString());
                } else {
                    this.revisions.set(version, this.toString());
                }
            }
        }
    }
    /**
     * Получает список ревизий задачи.
     * @return список ревизий задачи.
     */
    public String[] getRevisions() {
        synchronized (this.lock) {
            this.revisions.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            return this.revisions.toArray(new String[this.revisions.size()]);
        }
    }
    /**
     * Получает версию задачи.
     * @return версию задачи.
     */
    public int getVersion() {
        synchronized (this.lock) {
            return this.version;
        }
    }
    /**
     * Устанавливает имя задачи.
     * @param name имя задачи.
     */
    public void setName(String name) {
        synchronized (this.lock) {
            if (!this.name.equals(name)) {
                this.name = name;
            }
        }
    }
    /**
     * Возвращает строковое представление задачи.
     * @return строковое представление задачи.
     */
    @Override
    public String toString() {
        synchronized (this.lock) {
            return String.format("Task{description: %s}", this.description);
        }
    }
}
