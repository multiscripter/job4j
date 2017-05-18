package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс TwoDemArrIter реализует итератор для двухмерного массива int[][].
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-18
 */
class TwoDemArrIter implements Iterator {
    /**
     * Двухмерный массив.
     */
    private int[][] arr;
    /**
     * Текущий индекс массива.
     */
    private int index;
    /**
     * Конструктор.
     * @param arr двухмерный массив.
     */
    TwoDemArrIter(int[][] arr) {
        this.arr = arr;
        this.index = 0;
    }
    /**
     * Проверяет существование следующего элемента.
     * @return true если следующий элемент существует, иначе false.
     */
    public boolean hasNext() {
        return this.index < this.arr.length * this.arr[0].length ? true : false;
    }
    /**
     * Возвращает значение следующего элемента массива.
     * @return значение следующего элемента массива.
     */
    public Object next() {
        int x = this.index / this.arr[0].length;
        int y = this.index++ - x * this.arr[0].length;
        try {
            return new Integer(this.arr[x][y]);
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