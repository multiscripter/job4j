package ru.job4j.search;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Класс PriorityQueue реализует сущность Очередь с приоритетом.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-11-29
 * @since 2018-11-29
 */
public class PriorityQueue {
    /**
     * Очередь.
     */
    private final LinkedList<Task> tasks = new LinkedList<>();
    /**
     * Добавляет задачу в очередь.
     * @param task добавляемая задача.
     */
    public void put(Task task) {
        if (this.tasks.size() == 0) {
            this.tasks.add(task);
        } else {
            List<Task> list = this.tasks.stream().filter(x -> x.getPriority() > task.getPriority()).limit(1).collect(Collectors.toList());
            if (list.size() == 0) {
                this.tasks.add(task);
            } else {
                this.tasks.add(this.tasks.indexOf(list.get(0)), task);
            }
        }
    }
    /**
     * Получает задачу с максимальным приоритетом.
     * @return задача с максимальным приоритетом.
     */
    public Task take() {
        return this.tasks.poll();
    }
}
