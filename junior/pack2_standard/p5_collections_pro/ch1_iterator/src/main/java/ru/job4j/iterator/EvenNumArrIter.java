package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс EvenNumArrIter реализует итератор чётных чисел.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-22
 */
class EvenNumArrIter implements Iterator {
    /**
     * Массив чисел.
     */
    private int[] arr;
    /**
     * Текущий индекс массива.
     */
    private int index;
    /**
     * Конструктор.
     * @param arr массив чисел.
     * @throws TooShortArrayException исключение "Слишком короткий массив".
     */
    EvenNumArrIter(int[] arr) throws TooShortArrayException {
        if (arr.length < 1) {
            throw new TooShortArrayException();
        }
        this.arr = arr;
        this.index = 0;
    }
    /**
     * Проверяет существование следующего элемента.
     * @return true если следующий элемент существует, иначе false.
     */
    public boolean hasNext() {
        boolean result = false;
        for (int a = this.index; a < this.arr.length; a++) {
            if (this.arr[a] % 2 == 0) {
                result = true;
                break;
            }
        }
        return result;
    }
    /**
     * Возвращает значение следующего элемента массива.
     * @return значение следующего элемента массива.
     */
    public Object next() {
        try {
            while (this.arr[this.index] % 2 != 0) {
                this.index++;
            }
            return new Integer(this.arr[this.index++]);
        } catch (Exception ex) {
            throw new NoSuchElementException();
        }
    }
    /**
     * Удаляет элемент массива.
     * @throws UnsupportedOperationException исключение "Операция не поддерживается".
     */
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}