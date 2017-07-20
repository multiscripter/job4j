package ru.job4j.jmm;

/**
 * Класс Jmm.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-20
 */
class Jmm {
    /**
     * Некоторое числовое поле.
     */
    private int val;
    /**
     * Конструктор.
     * @param val число.
     */
    Jmm(int val) {
        this.val = val;
    }
    /**
     * Возвращает число.
     * @return число.
     */
    public int getVal() {
        return this.val;
    }
    /**
     * Устанавливает число.
     * @param val число.
     */
    public void setVal(int val) {
        this.val = val;
    }
    /**
     * Возвращает строковое представление объекта.
     * @return строковое представление объекта.
     */
    public String toString() {
        return String.format("{val: %d}", this.val);
    }
}
