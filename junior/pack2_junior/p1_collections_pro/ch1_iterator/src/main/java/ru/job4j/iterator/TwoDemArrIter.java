package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс TwoDemArrIter реализует итератор для двухмерного массива int[][].
 * @param <T> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-05-18
 */
class TwoDemArrIter<T> implements Iterator<T> {
    /**
     * Двухмерный массив.
     */
    private T[][] arr;
    /**
     * Текущий индекс массива.
     */
    private int index;
    /**
     * Длина двухмерного массива.
     */
    private int length = 0;
    /**
     * Конструктор.
     * @param arr двухмерный массив.
     */
    TwoDemArrIter(T[][] arr) {
        this.arr = arr;
        this.index = 0;
        for (T[] anArr : this.arr) {
            this.length += anArr.length;
        }
    }
    /**
     * Проверяет существование следующего элемента.
     * @return true если следующий элемент существует, иначе false.
     */
    public boolean hasNext() {
        return this.index < this.length;
    }
    /**
     * Возвращает значение следующего элемента массива.
     * @return значение следующего элемента массива.
     */
    public T next() {
        int x = 0, y = 0, cur = 0;
        for (int a = 0; a < this.arr.length; a++, x++) {
            cur += this.arr[a].length;
            if (cur > this.index) {
                y = this.arr[a].length - (cur - this.index);
                break;
            }
        }
        this.index++;
        try {
            return this.arr[x][y];
        } catch (Exception ex) {
            throw new NoSuchElementException();
        }
    }
    /**
     * Удаляет элемента массива.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
