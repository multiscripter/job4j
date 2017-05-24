package ru.job4j.generic;
/**
 * Класс Store реализует сущность Хранилище.
 *
 * @param <T> тип объекта.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-24
 */
class Store<T extends Base> {
    /**
     * Хранилище объектов.
     */
    private SimpleList<T> store;
    /**
     * Конструктор без параметров.
     */
    Store() {
        this.store = new SimpleList<>();
    }
    /**
     * Конструктор.
     * @param size размер хранилища.
     */
    Store(int size) {
        this.store = new SimpleList<>(size);
    }
    /**
     * Добавляет элемент в хранилище.
     * @param value добавляемый элемент.
     */
    public void add(T value) {
        this.store.add(value);
    }
    /**
     * Получает текущее количество элементов в хранилище.
     * @return текущее количество элементов в хранилище.
     */
    public int count() {
        return this.store.count();
    }
    /**
     * Удаляет элемент по индексу из хранилища.
     * @param index индекс элемента.
     * @return удалённый элемент.
     */
    public T delete(String index) {
        return this.store.delete(Integer.parseInt(index));
    }
    /**
     * Получает элемент по индексу из хранилища.
     * @param index индекс элемента.
     * @return элемент.
     */
    public T get(String index) {
        return this.store.get(Integer.parseInt(index));
    }
    /**
     * Получает индекс контейнера.
     * @return индекс контейнера.
     */
    public int index() {
        return this.store.index();
    }
    /**
     * Получает текущий размер контейнера.
     * @return размер.
     */
    public int size() {
        return this.store.size();
    }
    /**
     * Получает массив элементов хранилища.
     * @return массив элементов хранилища.
     */
    public Object[] toArray() {
        return this.store.toArray();
    }
    /**
     * Обновляет элемент в массиве.
     * @param index индекс элемента.
     * @param value обновлённый элемент.
     */
    public void update(String index, T value) {
        this.store.update(Integer.parseInt(index), value);
    }
}