package ru.job4j.search;

/**
 * Класс Task реализует сущность Задача.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-29
 * @since 2018-11-29
 */
public class Task {
    /**
     * Текст задачи.
     */
    private final String desc;
    /**
     * Приоритет задачи.
     */
    private final int priority;
    /**
     * Конструктор.
     * @param desc     текст задачи.
     * @param priority приоритет задачи.
     */
    public Task(String desc, int priority) {
        this.desc = desc;
        this.priority = priority;
    }
    /**
     * Получает текст задачи.
     * @return текст задачи.
     */
    public String getDesc() {
        return this.desc;
    }
    /**
     * Получает приоритет.
     * @return приоритет.
     */
    public int getPriority() {
        return this.priority;
    }
}
