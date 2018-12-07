package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Класс IterOfIterToIter реализует функционал конвертирования итератора итераторов в итератор.
 * @param <T> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2017-05-22
 */
class IterOfIterToIter<T> implements Iterator<T> {
    /**
     * Текущий итератор.
     */
    private Iterator<T> curIter;
    /**
     * Указатель.
     */
    private Iterator<Iterator<T>> pointer;
    /**
     * Пустой конструктор.
     */
    IterOfIterToIter() {
    }
    /**
     * Закрытый конструктор.
     * @param iterOfIters итератор итераторов.
     */
    private IterOfIterToIter(Iterator<Iterator<T>> iterOfIters) {
        this.pointer = iterOfIters;
    }
    /**
     * Конвертирует итератор итераторов в итератор.
     * @param iterOfIters итератор итераторов.
     * @return итератор.
     */
    public Iterator<T> convert(Iterator<Iterator<T>> iterOfIters) {
        return new IterOfIterToIter<>(iterOfIters);
    }
    /**
     * Получает следующий элемент.
     * @return следующий элемент или null.
     */
    private Iterator<T> getNext() {
        Iterator<T> result = null;
        if (this.pointer.hasNext()) {
            this.curIter = this.pointer.next();
            if (this.curIter.hasNext()) {
                result = this.curIter;
            }
        }
        return result;
    }
    /**
     * Проверяет существование следующего элемента.
     * @return true если следующий элемент существует, иначе false.
     */
    public boolean hasNext() {
        if (this.curIter == null || !this.curIter.hasNext()) {
            this.curIter = this.getNext();
        }
        return this.curIter != null && this.curIter.hasNext();
    }
    /**
     * Возвращает значение следующего элемента.
     * @return значение следующего элемента.
     */
    public T next() {
        if (this.curIter == null || !this.curIter.hasNext()) {
            this.curIter = this.getNext();
        }
        try {
            return this.curIter.next();
        } catch (Exception ex) {
            throw new NoSuchElementException();
        }
    }
    /**
     * Удаляет элемент массива.
     */
    public void remove() {
        if (this.curIter != null) {
            this.curIter.remove();
        }
    }
}
