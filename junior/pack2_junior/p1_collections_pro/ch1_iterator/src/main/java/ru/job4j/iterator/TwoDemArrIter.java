package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс TwoDemArrIter реализует итератор для двухмерного массива int[][].
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
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
     * Длина двухмерного массива.
     */
    private int length = 0;
    /**
     * Конструктор.
     * @param arr двухмерный массив.
     */
    TwoDemArrIter(int[][] arr) {
        this.arr = arr;
        this.index = 0;
        for (int a = 0; a < this.arr.length; a++) {
            this.length += this.arr[a].length;
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
    public Object next() {
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
