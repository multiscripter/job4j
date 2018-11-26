package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс EvenIndexArrIter реализует итератор чётных чисел.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-22
 */
class EvenIndexArrIter implements Iterator {
    /**
     * Массив чисел.
     */
    private int[] arr;
    /**
     * Текущий индекс массива.
     */
    private int index;
    /**
     * Длина массива.
     */
    private int length;
    /**
     * Название вызванного метода.
     */
    private String state;
    /**
     * Конструктор.
     * @param arr массив чисел.
     * @throws TooShortArrayException исключение "Слишком короткий массив".
     */
    EvenIndexArrIter(int[] arr) throws TooShortArrayException {
        if (arr.length < 1) {
            throw new TooShortArrayException();
        }
        this.arr = arr;
        this.index = this.arr[0] % 2 == 0 ? -2 : -1;
        this.length = this.arr.length;
        this.state = "";
    }
    /**
     * Проверяет существование следующего элемента.
     * @return true если следующий элемент существует, иначе false.
     */
    public boolean hasNext() {
        this.state = "hasNext";
        return this.index + 2 < this.length;
    }
    /**
     * Возвращает значение следующего элемента массива.
     * @return значение следующего элемента массива.
     */
    public Object next() {
        this.state = "next";
        this.index += 2;
        try {
            return new Integer(this.arr[this.index]);
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