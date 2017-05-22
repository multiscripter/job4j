package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс PrimeNumArrIter реализует итератор простых чисел.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-22
 */
class PrimeNumArrIter implements Iterator {
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
    PrimeNumArrIter(int[] arr) throws TooShortArrayException {
        if (arr.length < 1) {
            throw new TooShortArrayException();
        }
        this.arr = arr;
        this.index = 0;
    }
    /**
     * Проверяет является ли число простым.
     * @param num проверяемое число
     * @return true если число простое, иначе false.
     */
    private boolean isPrime(int num) {
        boolean result = false;
        if (num == 1 || num == 2) {
            result = true;
        } else if (num > 2) {
            if (num % 2 == 0) {
                result = false;
            } else {
                result = true;
                for (int b = 3; b < num; b += 2) {
                    if (num % b == 0) {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }
    /**
     * Проверяет существование следующего элемента.
     * @return true если следующий элемент существует, иначе false.
     */
    public boolean hasNext() {
        boolean result = false;
        for (int a = this.index; a < this.arr.length; a++) {
            if (this.isPrime(this.arr[a])) {
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
            while (!this.isPrime(this.arr[this.index])) {
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
