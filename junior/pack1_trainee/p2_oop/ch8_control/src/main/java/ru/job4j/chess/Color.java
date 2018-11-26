package ru.job4j.chess;

/**
 * Перечисление Color реализует константы "Цвет".
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-03
 */
enum Color {
    /**
     * Чёрный.
     */
    BLACK("Black"),
    /**
     * Белый.
     */
    WHITE("White");
    /**
     * Значение перечисления.
     */
    private String value;
    /**
     * Конструктор.
     * @param value значение перечисления.
     */
    Color(String value) {
        this.value = value;
    }
    /**
     * Конструктор.
     * @return значение перечисления.
     */
    public String getValue() {
        return this.value;
    }
}
