package ru.job4j.iterator;

import java.util.Iterator;
/**
 * Класс IterOfIterToIter реализует функционал конвертирования итератора итераторов в итератор.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-22
 */
class IterOfIterToIter implements Iterator {
    /**
     * Текущий итератор.
     */
    private Iterator<Iterator> curIter;
    /**
     * Указатель.
     */
    private Iterator<Iterator<Integer>> pointer;
    /**
     * Пустой конструктор.
     */
    IterOfIterToIter() {
    }
    /**
     * Конструктор.
     * @param iterOfIters итератор итераторов.
     */
    private IterOfIterToIter(Iterable<Iterator<Integer>> iterOfIters) {
        this.pointer = iterOfIters.iterator();
    }
    /**
     * Конвертирует итератор итераторов в итератор.
     * @param iterOfIters итератор итераторов.
     * @return итератор.
     */
    public Iterator<Integer> convert(Iterator<Iterator<Integer>> iterOfIters) {
        return new IterOfIterToIter((Iterable) iterOfIters);
    }
    /**
     * Получает следующий элемент.
     * @return следующий элемент или null.
     */
    private Integer getNext() {
        Integer result = null;
        while (this.pointer.hasNext()) {
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
        return (this.curIter != null && this.cutIter.hasNext());
    }
    /**
     * Возвращает значение следующего элемента.
     * @return значение следующего элемента.
     */
    public Integer next() {
        return this.curIter.next();
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
