package ru.job4j.generic;

/**
 * Класс SimpleList<T> реализует генерик контейнер списка.
 *
 * @param <T> тип контейнера.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-24
 */
public class SimpleList<T> {
    /**
     * Текущий индекс.
     */
    private int index;
    /**
     * Массив элементов.
     */
    private Object[] objects;
    /**
     * Размер массива элементов.
     */
    private int size;
    /**
     * Конструкор.
     * @param size размер контейнера.
     */
    SimpleList(int size) {
        this.size = size > 0 ? size : 10;
        this.objects = new Object[this.size];
        this.index = 0;
    }
    /**
     * Добавляет элемент в массив.
     * @param value добавляемый элемент.
     */
    public void add(T value) {
        if (this.index == this.objects.length) {
            this.addSize();
        }
        this.objects[this.index++] = value;
    }
    /**
     * Увиличивает ёмкость массива элементов.
     */
    private void addSize() {
        Object[] objects = new Object[this.size * 2];
        System.arraycopy(this.objects, 0, objects, 0, this.size);
        this.objects = objects;
        this.size *= 2;
    }
    /**
     * Получает текущее количество элементов в контейнере.
     * @return размер.
     */
    public int count() {
        return this.index;
    }
    /**
     * Удаляет элемент по индексу.
     * @param index индекс элемента.
     * @return удалённый элемент.
     */
    public T delete(int index) {
        Object deleted = this.objects[index];
        this.shift(index);
        this.index--;
        return (T) deleted;
    }
    /**
     * Получает элемент по индексу.
     * @param index индекс элемента.
     * @return элемент.
     */
    public T get(int index) {
        return (T) this.objects[index];
    }
    /**
     * Получает индекс контейнера.
     * @return индекс контейнера.
     */
    public int index() {
        return this.index;
    }
    /**
     * Сдвигает элементы влево на 1, начиная с индекса.
     * @param index индекс элемента.
     */
    private void shift(int index) {
        for (int size = this.index - 1; index < size;) {
            this.objects[index] = this.objects[++index];
        }
        this.size--;
    }
    /**
     * Получает текущий размер контейнера.
     * @return размер.
     */
    public int size() {
        return this.size;
    }
    /**
     * Получает массив контейнера.
     * @return массив контейнера.
     */
    public T[] toArray() {
        return (T[]) this.objects;
    }
    /**
     * Обновляет элемент в массиве.
     * @param index индекс элемента.
     * @param value обновлённый элемент.
     */
    public void update(int index, T value) {
        this.objects[index] = value;
    }
}