package ru.job4j.nonblocking;

import java.util.concurrent.ConcurrentHashMap;
/**
 * Класс NonBlockingCache реализует сущность "Неблокирующий кеш".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-04
 */
class NonBlockingCache {
    /**
     * Кэш.
     */
    private ConcurrentHashMap<Integer, Task> cache;
    /**
     * Объект блокировки.
     */
    private Object lock;
    /**
     * Конструктор.
     */
    NonBlockingCache() {
        this.lock = this;
        this.cache = new ConcurrentHashMap<>();
    }
    /**
     * Добавляет значение в кэш.
     * @param key ключ.
     * @param value значение.
     * @return предыдущее значение, ассоциированное с указанным ключём. Иначе null.
     */
    public Task add(int key, Task value) {
        return this.cache.put(key, value);
    }
    /**
     * Удаляет значение из кэша.
     * @param key ключ.
     * @return значение, ассоциированное с указанным ключём. Иначе null.
     */
    public Task delete(int key) {
        return this.cache.remove(key);
    }
    /**
     * Удаляет значение из кэша.
     * @param key ключ.
     * @param value значение.
     * @return значение, ассоциированное с указанным ключём. Иначе null.
     */
    public Task delete(int key, Task value) {
        return this.cache.remove(key);
    }
    /**
     * Получает значение из кэша. Использование копирующего конструктора new Task().
     * @param key ключ.
     * @return значение, ассоциированное с указанным ключём. Иначе null.
     */
    public Task get(int key) {
        synchronized (this.lock) {
            return new Task(this.cache.get(key));
        }
    }
    /**
     * Обновляет значение в кэше.
     * @param key ключ.
     * @param value значение.
     * @return предыдущее значение, ассоциированное с указанным ключём. Иначе null.
     */
    public Task update(int key, Task value) {
        return this.cache.computeIfPresent(key, (k, oldValue) -> {
            int oldV = oldValue.getVersion();
            if (oldV + 1 != value.getVersion()) {
                throw new OplimisticException(oldV, value.getVersion());
            }
            return value;
        });
    }
}
/**
 * Класс OplimisticException реализует исключение "Несовпадение версий".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-04
 */
class OplimisticException extends RuntimeException {
    /**
     * Конструктор.
     * @param oldV старая версия.
     * @param newV новая версия.
     */
    OplimisticException(int oldV, int newV) {
        super(String.format("Version mismatch. Old: %d, new: %d", oldV, newV));
    }
}
